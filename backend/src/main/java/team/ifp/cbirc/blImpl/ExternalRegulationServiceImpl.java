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
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.vo.ExternalRegulationVO;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
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
            ResponseVO.buildBadRequest("所指定的法规不存在");
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

        if(inputStreamResource == null) ResponseVO.buildInternetServerError("服务器异常"); //文件不存在

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
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<ResponseVO> create(MultipartFile file, ExternalRegulationVO vo) {

        //存储上传文件
        String savePath = DATA_ROOT_PATH + File.separator + file.getName();
        File savedFile = new File(savePath);
        try {
            file.transferTo(savedFile);
        } catch (IOException e) {
            ResponseVO.buildInternetServerError("服务器错误");
        }

        //构建存储对象
        ExternalRegulation externalRegulation = new ExternalRegulation(vo);
        externalRegulation.setTextPath(savePath);

        //捕获数据库异常
        boolean ifDataAccessException = false;
        try {
            externalRegulationRepository.save(externalRegulation);
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
            //产生异常删除文件
            if(!savedFile.delete()) {
                System.err.println("新建法规保存失败,法规对应文件已保存但删除失败(path:" + savePath + ")");
            }
            ifDataAccessException = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(ifDataAccessException) ResponseVO.buildInternetServerError("服务器异常");

        return ResponseEntity.ok(ResponseVO.buildOK("创建成功"));
    }
}
