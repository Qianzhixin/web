package team.ifp.cbirc.dao;

import lombok.Data;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import team.ifp.cbirc._enum.RegulationState;
import team.ifp.cbirc.po.ExternalRegulation;

import java.util.Optional;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Data
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExternalRegulationDaoTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ExternalRegulationDao externalRegulationDao;

    @Test
    @Order(1)
    public void testGet() throws Exception{
        Optional<ExternalRegulation> byId = externalRegulationDao.findById(1);
        if(!byId.isPresent()) throw new Exception();

        ExternalRegulation externalRegulation = byId.get();

        Assertions.assertEquals(1,externalRegulation.getId());
        Assertions.assertEquals("测试法规1",externalRegulation.getTitle());
        Assertions.assertEquals(RegulationState.UNPUBLISHED,externalRegulation.getState());

    }

}
