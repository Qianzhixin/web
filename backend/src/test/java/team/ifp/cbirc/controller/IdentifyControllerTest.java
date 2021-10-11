package team.ifp.cbirc.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.ifp.cbirc.MockMvcManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IdentifyControllerTest {

    @Autowired
    private WebApplicationContext wac;

    MockMvc mockMvc;

    MockHttpSession session;

    @BeforeEach
    public void beforeEach() throws Exception{
        //初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @Test
    @Order(1)
    public void testLogin() throws Exception {
        JSONObject requestJsonObj = new JSONObject();
        requestJsonObj.put("username","toor");
        requestJsonObj.put("password","111111");
        String requestBody;

        requestBody = requestJsonObj.toJSONString();
        mockMvc.perform(post("/identify/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        requestJsonObj.put("username","root");
        requestBody = requestJsonObj.toJSONString();
        mockMvc.perform(post("/identify/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        mockMvc.perform(post("/identify/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @Test
    @Order(2)
    public void testLogout() throws Exception {
        mockMvc.perform(get("/identify/logout")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        MockMvcManager.login(mockMvc,session);

        mockMvc.perform(get("/identify/logout")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(3)
    public void testRegister() throws Exception {
        JSONObject requestJsonObj = new JSONObject();
        requestJsonObj.put("username","root");
        requestJsonObj.put("password","123456");
        String requestBody;

        requestBody = requestJsonObj.toJSONString();
        mockMvc.perform(post("/identify/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }
}
