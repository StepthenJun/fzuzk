package zk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zk.domain.Result;
import zk.service.LoginService;

@RestController
@RequestMapping("/FZUZK/login")
@Api("登入的接口")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation("登入")
    @PostMapping
    public Result<String> login(String username,String password){
        String login = loginService.login(username, password);
        return Result.success(login);
    }
}
