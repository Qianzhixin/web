package team.ifp.cbirc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.ifp.cbirc.bl.IdentifyService;
import team.ifp.cbirc.util.aop.OperLog;
import team.ifp.cbirc.vo.ResponseVO;
import team.ifp.cbirc.vo.UserVO;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */

@RestController
@RequestMapping("/identify")
public class IdentifyController {

    @Autowired
    IdentifyService identifyService;

    @PostMapping("/login")
    @OperLog()
    ResponseEntity<ResponseVO> login(@RequestBody UserVO userVO){
        return identifyService.login(userVO);
    }

    @PostMapping("/register")
    @OperLog()
    ResponseEntity<ResponseVO> register(@RequestBody UserVO userVO){
        return identifyService.register(userVO);
    }

    @GetMapping("/logout")
    @OperLog()
    ResponseEntity<ResponseVO> logout(){
        return identifyService.logout();
    }

}
