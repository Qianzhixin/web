package team.ifp.cbirc.blImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.userdata.UserSession;
import team.ifp.cbirc.util.FileUtil;
import team.ifp.cbirc.vo.*;

import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author GuoXinyuan
 * @date 2021/10/10
 */
@Service
public class ExternalRegulationServiceImpl implements ExternalRegulationService {

    @Value("${myconfig.data-root-path}")
    private String DATA_ROOT_PATH;

    @Autowired
    private ExternalRegulationRepository externalRegulationRepository;

    /**
     * 存储文件锁
     */
    ReentrantLock saveFileLock = new ReentrantLock();

    /**
     * 创建和编辑某一法规记录时,同时涉及数据库和文件的操作
     * 为每一个法规记录生成一个字符串对象作为锁,对其操作时进行锁定
     * 此变量作为字符串锁对象前缀
     */
    private final String REGULATION_LOCK_PREFIX = "RLP";

    /**
     * 获取任意数量的法规
     *
     * @param begin
     * @param len
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> gain(Integer begin, Integer len) {
        List<ExternalRegulation> resultList;

        //不进行分页
        if(begin==null && len==null) {
            resultList = externalRegulationRepository.findAll();
        }
        //进行分页
        else {
            //判断搜索范围是否有意义 无意义抛出异常
            checkSearchRangeAndThrowException(begin, len);

            //进行查询
            assert begin!=null;
            resultList = externalRegulationRepository.find(begin, len);
        }

        return ResponseEntity.ok(ResponseVO.buildOK(resultList));
    }

    /**
     * 根据searchRegulationVO中的信息搜索满足条件的法规
     * 当条件为null则不参与搜索;
     * 如果全为null则返回空集;
     * 如果查询范围(begin,len) 都为 null 则查询全部;
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> search(SearchRegulationVO vo) {
        if(vo.isAllNull()) {
//            ResponseVO.buildBadRequest("所有查询条件都为空");

            // 无过滤条件，查询全部
            List<ExternalRegulation> entityList = externalRegulationRepository.findAll();
            List<ExternalRegulationVO> resultList = entityList.parallelStream().map(ExternalRegulationVO::new).collect(Collectors.toList());
            return ResponseEntity.ok(ResponseVO.buildOK(resultList));
        }
        if(vo.getBegin()==null && vo.getLen()==null) {
            vo.setBegin(0);
            vo.setLen(0);
        }
        else {
            //判断搜索范围是否有意义 无意义抛出异常
            checkSearchRangeAndThrowException(vo.getBegin(), vo.getLen());
        }

        SearchRegulationPOJO searchRegulationPOJO = new SearchRegulationPOJO(vo);

        //查询获取实体列表
        List<ExternalRegulation> entityList = externalRegulationRepository.search(searchRegulationPOJO);

        if(entityList == null) {
            ResponseVO.buildInternetServerError("服务器异常");
        }

        //将实体映射为VO
        List<ExternalRegulationVO> resultList = entityList.parallelStream().map(ExternalRegulationVO::new).collect(Collectors.toList());

        return ResponseEntity.ok(ResponseVO.buildOK(resultList));
    }

    /**
     * 根据id下载法规正文文件
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<InputStreamResource> downloadFile(int id) {

        Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id);

        if(!byId.isPresent()) {
            ResponseVO.buildNotFound("需要下载正文的法规不存在");
        }

        ExternalRegulation er = byId.get();

        //获取文件流
        File file = null;
        InputStreamResource inputStreamResource;
        try {
            file = new File(er.getTextPath());
            InputStream inputStream = new FileInputStream(file);
            inputStreamResource = new InputStreamResource(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            inputStreamResource = null;
        }

        if(inputStreamResource == null) ResponseVO.buildInternetServerError("文件丢失");

        // 创建http headers
        HttpHeaders headers;
        try {
            headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(),"UTF-8")));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            headers = null;
        }

        if(headers == null) {
            ResponseVO.buildInternetServerError("服务器异常");
        }

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }

    /**
     * 根据所给定的信息创建外规记录
     *
     * @param file
     * @param createRegulationVO
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> create(MultipartFile file, CreateRegulationVO createRegulationVO) {

        createRegulationVO.setType(createRegulationVO.getEffectivenessLevel() + "");

        //检查输入合法性并抛出异常 !如果不合法直接返回异常 400
        createRegulationVO.checkLegalityAndThrowException();

        //存储文件 !过程中失败直接返回异常 500
        File savedFile = saveFileSafely(file);

        //构建存储对象
        ExternalRegulation externalRegulation = new ExternalRegulation(createRegulationVO,UserSession.getUser(),savedFile.getAbsolutePath());

        //存入数据并捕获数据库异常 产生异常删除文件 并抛出异常
        if(tryCacheDataAccessException(()->externalRegulationRepository.save(externalRegulation))) {
            if(!savedFile.delete()) {
                System.err.println("新建法规保存失败,法规对应文件已保存但删除失败(path:" + savedFile.getAbsolutePath() + ")");
            }
            ResponseVO.buildInternetServerError("服务器异常");
        }

        return ResponseEntity.ok(ResponseVO.buildOK("创建成功"));
    }

    /**
     * 根据给定的文件与信息修改法规信息
     *
     * @param file                 法规正文文件
     *                             为 null 则不对正文做出修改;
     *                             否则删除原文件,替换为这次上传的文件；
     * @param editRegulationVO 法规信息修改对象
     *                             editRegulationVO==null,不对正文信息做出任何修改;
     *                             editRegulationVO 内部属性为 null,不对该属性做出修改
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> edit(MultipartFile file, EditRegulationVO editRegulationVO) {
        if (file == null) ResponseVO.buildInternetServerError("服务器异常");

        if(editRegulationVO==null || editRegulationVO.getId()==null) {
            ResponseVO.buildBadRequest("没有给出所要修改法规的id");
        }

        boolean isAllNull = editRegulationVO.isAllNull();
        if(file.isEmpty() && isAllNull) {
            ResponseVO.buildBadRequest("没有做出任何修改");
        }

        //对每条记录生成一个字符串对象作为锁
        String lock = REGULATION_LOCK_PREFIX + editRegulationVO.getId();
        synchronized (lock.intern()) {

            //获取原记录
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(editRegulationVO.getId());
            if(!byId.isPresent()) ResponseVO.buildNotFound("所要修改记录不存在");
            ExternalRegulation er = byId.get();

            //保存新上传的文件
            File savedFile = null;
            String oldFilePath = er.getTextPath();
            if(!file.isEmpty()) {
                savedFile = saveFileSafely(file);
                er.setTextPath(savedFile.getAbsolutePath());
            }

            //更新数据库信息
            if(!isAllNull) {
                er = editRegulationVO.fillEditProperty(er);

                //存入数据并捕获数据库异常 产生异常删除文件 并抛出异常
                ExternalRegulation finalEr = er;
                if(tryCacheDataAccessException(()->externalRegulationRepository.save(finalEr))) {
                    if(savedFile!=null && !savedFile.delete()) {
                        System.err.println("编辑法规失败,法规对应文件已保存但删除失败(path:" + savedFile.getAbsolutePath() + ")");
                    }
                    ResponseVO.buildInternetServerError("服务器异常");
                }

                //删除旧文件
                if(!file.isEmpty()) {
                    File oldFile = new File(oldFilePath);
                    if(oldFile.exists() && !oldFile.delete()) {
                        System.err.println("编辑法规成功,旧法规对应文件删除失败(path:" + oldFilePath + ")");
                    }
                }

            }
        }

        return ResponseEntity.ok(ResponseVO.buildOK("修改成功"));
    }

    /**
     * 根据id删除一条未发布法规
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> delete(int id) {
        //对每条记录生成一个字符串对象作为锁
        String lock = REGULATION_LOCK_PREFIX + id;
        synchronized (lock.intern()) {

            //获取原记录
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id);
            if(!byId.isPresent()) {
                ResponseVO.buildNotFound("所要删除的记录不存在");
            }
            ExternalRegulation er = byId.get();

            if(er.getState().equals(RegulationState.PUBLISHED)) {
                ResponseVO.buildBadRequest("不能删除已发布的法规");
            }

            //删除记录
            if(tryCacheDataAccessException(() -> {
                externalRegulationRepository.deleteById(id);
                return null;
            })) ResponseVO.buildInternetServerError("服务器异常");
            else {
                File oldFile = new File(er.getTextPath());
                if(oldFile.exists() && !oldFile.delete()) {
                    System.err.println("删除法规成功,旧法规对应文件删除失败(path:" + er.getTextPath() + ")");
                }
            }
        }
        return ResponseEntity.ok(ResponseVO.buildOK("删除成功"));
    }

    /**
     * 根据id发布一条法规
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> issue(int id) {
        String lock = REGULATION_LOCK_PREFIX + id;
        synchronized (lock.intern()) {
            //获取原记录
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id);
            if(!byId.isPresent()) {
                ResponseVO.buildNotFound("所要发布的法规不存在");
            }
            ExternalRegulation er = byId.get();

            if(er.getState().equals(RegulationState.PUBLISHED)) {
                ResponseVO.buildBadRequest("法规已经发布");
            }

            er.setState(RegulationState.PUBLISHED);

            //存储结果
            if(tryCacheDataAccessException(() -> externalRegulationRepository.save(er))) ResponseVO.buildInternetServerError("服务器错误");
        }

        return ResponseEntity.ok(ResponseVO.buildOK("发布成功"));
    }

    /**
     * 根据id废止一条法规
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> abolish(int id) {
        String lock = REGULATION_LOCK_PREFIX + id;
        synchronized (lock.intern()) {
            //获取原记录
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(id);
            if(!byId.isPresent()) {
                ResponseVO.buildNotFound("所要发布的法规不存在");
            }
            ExternalRegulation er = byId.get();

            if(er.getState().equals(RegulationState.UNPUBLISHED)) {
                ResponseVO.buildBadRequest("法规尚未发布");
            }

            er.setState(RegulationState.UNPUBLISHED);

            //存储结果
            if(tryCacheDataAccessException(() -> externalRegulationRepository.save(er))) ResponseVO.buildInternetServerError("服务器错误");
        }

        return ResponseEntity.ok(ResponseVO.buildOK("废止成功"));
    }

    /**
     * 进行统计
     *
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> doStatistics() {
        List<ExternalRegulation> resultList = externalRegulationRepository.findAll();

        StatisticsVO statisticsVO = new StatisticsVO();
        Map<String, Integer> map1 = statisticsVO.getCountPerDepartment();
        Map<String, Integer> map2 = statisticsVO.getCountPerType();
        Map<String, Integer> map3 = statisticsVO.getCountPerReleaseDate();
        Map<String, Integer> map4 = statisticsVO.getCountPerImplementationDate();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        resultList.parallelStream().forEach(er -> {
            String key1 = er.getPublishingDepartment();
            String key2 = er.getType();
            Date releaseDate = er.getReleaseDate();
            Date implementationDate = er.getImplementationDate();
            String key3 = dateFormat.format(releaseDate);
            String key4;
            if(implementationDate != null){
                key4 = dateFormat.format(er.getImplementationDate());
            }else{
                key4 = "";
            }

            map1.put(key1,map1.computeIfAbsent(key1,k -> 0)+1);
            map2.put(key2,map2.computeIfAbsent(key2,k -> 0)+1);
            map3.put(key3,map3.computeIfAbsent(key3,k -> 0)+1);
            map4.put(key4,map4.computeIfAbsent(key4,k -> 0)+1);
        });

        return ResponseEntity.ok(ResponseVO.buildOK(statisticsVO));
    }



    /**
     * -------------------------------------------------------------------------------------------
     */



    /**
     * 尝试执行数据库操作并捕获 databaseOperation
     * @param databaseOperation
     * @return
     */
    private boolean tryCacheDataAccessException(Supplier<ExternalRegulation> databaseOperation) {
        boolean ifDataAccessException = false;
        try {
            databaseOperation.get();
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
            ifDataAccessException = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ifDataAccessException;
    }

    /**
     * 安全地存储文件
     * 为文件在 DATA_ROOT_PATH 所指定的文件夹获取唯一的文件名(通过拼接UUID),并存储
     * @param file
     * @return 存储完毕的文件对象
     */
    private File saveFileSafely(MultipartFile file) {

        File savedFile;

        saveFileLock.lock();
        try {
            do {
                String savePath = DATA_ROOT_PATH + File.separator +
                        FileUtil.getFileNamePrefix(Objects.requireNonNull(file.getOriginalFilename())) + //去除后缀文件名
                        UUID.randomUUID().toString() + "." + //拼接uuid
                        FileUtil.getFileNameSuffix(file.getOriginalFilename()); //
                savedFile = new File(savePath);
            } while (savedFile.exists());
        } finally {
            saveFileLock.unlock();
        }

        //存储上传文件
        try {
            file.transferTo(savedFile);
        } catch (IOException e) {
            savedFile = null;
        }
        if(savedFile == null) ResponseVO.buildInternetServerError("服务器错误");

        return savedFile;
    }

    /**
     * 判断搜索范围是否有效并抛出异常
     * @param begin
     * @param len
     */
    private void checkSearchRangeAndThrowException(Integer begin,Integer len) {
        if(begin==null && len==null) {
            ResponseVO.buildInternetServerError("服务器异常");
        }
        else if(begin == null) {
            ResponseVO.buildBadRequest("只给出了查询长度,没有给出查询起点");
        }
        else if(len == null) {
            ResponseVO.buildBadRequest("只给出了查询起点,没有给出查询长度");
        }
        else if(begin < 0 || len <= 0) {
            ResponseVO.buildBadRequest("无意义查询(查询起点: " + begin + ",查询长度: " + len + ")");
        }
    }


}
