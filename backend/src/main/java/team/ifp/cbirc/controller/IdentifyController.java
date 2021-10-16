package team.ifp.cbirc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "账号接口", description = "基本的登录注册登出")
@RestController
@RequestMapping("/identify")
public class IdentifyController {

    @Autowired
    IdentifyService identifyService;

    @Operation(summary = "登录", description = "账号登录")
    @Parameters({
            @Parameter(name = "userVO", description = "用户值对象", required = true),
    })
    @PostMapping("/login")
    @OperLog()
    ResponseEntity<ResponseVO> login(@RequestBody UserVO userVO){
        return identifyService.login(userVO);
    }

    @Operation(summary = "注册", description = "账号注册")
    @Parameters({
            @Parameter(name = "userVO", description = "用户值对象", required = true),
    })
    @PostMapping("/register")
    @OperLog()
    ResponseEntity<ResponseVO> register(@RequestBody UserVO userVO){
        return identifyService.register(userVO);
    }


    @Operation(summary = "登出", description = "账号登出")
    @GetMapping("/logout")
    @OperLog()
    ResponseEntity<ResponseVO> logout(){
        return identifyService.logout();
    }

}
