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
import team.ifp.cbirc.bl.ExternalRegulationService;
import team.ifp.cbirc.dao.UserRepository;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.userdata.UserSession;
import team.ifp.cbirc.util.FileUtil;
import team.ifp.cbirc.vo.*;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
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

    @Autowired
    private UserRepository userRepository;

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
            ResponseVO.buildBadRequest("所有查询条件都为空");
        }
        if(vo.getBegin()==null && vo.getLen()==null) {
            //设置为查询所有
            vo.setBegin(0);
            vo.setLen(0);
        }
        else if(vo.getBegin()==null) {
            ResponseVO.buildBadRequest("只给出了查询长度,没有给出查询起点");
        }
        else if(vo.getLen()==null) {
            ResponseVO.buildBadRequest("只给出了查询起点,没有给出查询长度");
        }
        else if(vo.getBegin()<0 || vo.getLen()<=0) {
            ResponseVO.buildBadRequest("无意义查询(查询起点: " + vo.getBegin() + ",查询长度: " + vo.getLen() + ")");
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
            ResponseVO.buildNotFound("需要下载的法规不存在");
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

        //存入数据并捕获数据库异常
        boolean ifDataAccessException = false;
        try {
            externalRegulationRepository.save(externalRegulation);
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
            ifDataAccessException = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //产生异常删除文件 并抛出异常
        if(ifDataAccessException) {
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
        if(editRegulationVO==null || editRegulationVO.getId()==null) {
            ResponseVO.buildBadRequest("没有给出所要修改法规的id");
        }

        boolean isAllNull = editRegulationVO.isAllNull();
        if(file==null && isAllNull) {
            ResponseVO.buildBadRequest("没有做出任何修改");
        }

        //对每条记录生成一个字符串对象作为锁
        String lock = REGULATION_LOCK_PREFIX + editRegulationVO.getId();
        synchronized (lock.intern()) {

            //获取原记录
            Optional<ExternalRegulation> byId = externalRegulationRepository.findById(editRegulationVO.getId());
            if(!byId.isPresent()) {
                ResponseVO.buildNotFound("索要修改记录不存在");
            }
            ExternalRegulation er = byId.get();

            //保存新上传的文件
            File savedFile = null;
            String oldFilePath = er.getTextPath();
            if(file != null) {
                savedFile = saveFileSafely(file);
                er.setTextPath(savedFile.getAbsolutePath());
            }

            //更新数据库信息
            if(!isAllNull) {
                er = editRegulationVO.fillEditProperty(er);
                //存入数据并捕获数据库异常
                boolean ifDataAccessException = false;
                try {
                    externalRegulationRepository.save(er);
                } catch (DataAccessException dataAccessException) {
                    dataAccessException.printStackTrace();
                    ifDataAccessException = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //产生异常删除文件 并抛出异常
                if(ifDataAccessException) {
                    if(savedFile!=null && !savedFile.delete()) {
                        System.err.println("编辑法规失败,法规对应文件已保存但删除失败(path:" + savedFile.getAbsolutePath() + ")");
                    }
                    ResponseVO.buildInternetServerError("服务器异常");
                }

                //删除旧文件
                if(file != null) {
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


}
