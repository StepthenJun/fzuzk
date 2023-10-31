package zk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import zk.common.WebResultJson;
import zk.domain.Login.User;
import zk.domain.Result;
import zk.service.UserService;
import zk.util.TokenUtil;

@RestController
@RequestMapping()
@Api("登入的接口")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public WebResultJson login(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            return WebResultJson.fail("用户或者密码为空！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                .eq("password", user.getPassword());
        User userDb = userService.getOne(queryWrapper);
        if (userDb != null) {
            // 获取用户所有的权限信息
//            Set<String> menuUrlList = menuService.listUrlByUserId(userDb.getId());
//            userDb.setMenuUrlList(menuUrlList);
            // 登陆成功了
            String token = TokenUtil.generateToken(userDb);
            userDb.setToken(token);
//            loginLogService.saveLoginLog(userDb.getId());
            return WebResultJson.ok(userDb);
        } else {
            return WebResultJson.fail("用户名或密码错误！");
        }
    }

    @PostMapping("/loginnotoken")
    public Result<String> loginnotoken(User user){
        String loginnotoken = userService.loginnotoken(user);
        return Result.success(loginnotoken);
    }
}
