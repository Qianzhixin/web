package team.ifp.cbirc;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */
public class MockMvcManager {

    public static void login(MockMvc mockMvc, MockHttpSession session) throws Exception {
        JSONObject requestJsonObj = new JSONObject();
        requestJsonObj.put("username","root");
        requestJsonObj.put("password","111111");
        String requestBody = requestJsonObj.toJSONString();
        mockMvc.perform(post("/identify/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    public static void logout(MockMvc mockMvc, MockHttpSession session) throws Exception {
        mockMvc.perform(get("/identify/logout")
                .session(session))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}
