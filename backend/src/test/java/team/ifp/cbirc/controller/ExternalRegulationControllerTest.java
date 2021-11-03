package team.ifp.cbirc.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.ifp.cbirc.MockMvcManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExternalRegulationControllerTest {

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    MockHttpSession session;

    @BeforeEach
    public void beforeEach() throws Exception{

        System.out.println("before each");
        //初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();

        //进行登录
        MockMvcManager.login(mockMvc,session);
    }

    @AfterEach
    public void afterEach() throws Exception {
        System.out.println("after each");
        MockMvcManager.logout(mockMvc,session);
    }

    @Test
    @Order(1)
    public void testSearch() throws Exception {
        JSONObject requestJsonObj = new JSONObject();
        requestJsonObj.put("title","测试法规1");
        requestJsonObj.put("number","");
        requestJsonObj.put("publishingDepartment","部门1");
        requestJsonObj.put("effectivenessLevel",1);
//        requestJsonObj.put("releaseDate","2021-04-29 18:37:10");
        requestJsonObj.put("state","UNPUBLISHED");

        String requestBody = requestJsonObj.toJSONString();
        MvcResult mvcResult = mockMvc.perform(post("/externalRegulation/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        JSONObject responseJsonObj = JSONObject.parseObject(response.getContentAsString());
        JSONObject contentObj = responseJsonObj.getJSONArray("content").getObject(0,JSONObject.class);

        Assertions.assertEquals("测试法规1",contentObj.get("title"));
    }

    @Test
    @Order(2)
    public void a(){}

}
