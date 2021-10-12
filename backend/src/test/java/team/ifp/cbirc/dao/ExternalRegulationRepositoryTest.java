package team.ifp.cbirc.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.dao.externalRegulation.ExternalRegulationRepository;
import team.ifp.cbirc.po.ExternalRegulation;
import team.ifp.cbirc.pojo.SearchRegulationPOJO;
import team.ifp.cbirc.vo.SearchRegulationVO;

import java.util.List;
import java.util.Optional;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExternalRegulationRepositoryTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ExternalRegulationRepository externalRegulationRepository;

    @Test
    @Order(1)
    public void testGet() throws Exception {
        Optional<ExternalRegulation> byId = externalRegulationRepository.findById(1);
        if(!byId.isPresent()) throw new Exception();

        ExternalRegulation externalRegulation = byId.get();

        assertTestData1(externalRegulation);
    }

    @Test
    @Order(2)
    public void testSearch() throws Exception {
        SearchRegulationVO searchRegulationVO = new SearchRegulationVO();
        searchRegulationVO.setTitle("测试法规1");
        searchRegulationVO.setEffectivenessLevel(1);
        searchRegulationVO.setPublishingDepartment("部门1");
        searchRegulationVO.setBegin(0);
        searchRegulationVO.setLen(0);

        SearchRegulationPOJO searchRegulationPOJO = new SearchRegulationPOJO(searchRegulationVO);

        List<ExternalRegulation> regulationList = externalRegulationRepository.search(searchRegulationPOJO);

        assertTestData1(regulationList.get(0));
    }

    /**
     * 测试是否是测试数据1
     */
    private void assertTestData1(ExternalRegulation externalRegulation) {
        Assertions.assertEquals(1,externalRegulation.getId());
        Assertions.assertEquals("测试法规1",externalRegulation.getTitle());
        Assertions.assertEquals("",externalRegulation.getNumber());
        Assertions.assertEquals("测试类别1",externalRegulation.getType());
        Assertions.assertEquals("部门1",externalRegulation.getPublishingDepartment());
        Assertions.assertEquals(RegulationState.UNPUBLISHED,externalRegulation.getState());
    }

}
