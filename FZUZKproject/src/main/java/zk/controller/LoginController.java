package zk.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import zk.dao.UserMapper;
import zk.domain.Login.User;
import zk.domain.Result;
import zk.service.UserService;
import zk.util.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
@Api("登入的接口")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;


//    @GetMapping("/login")
//    public Map<String,Object> login(User user){
//        log.info("用户名：[{}]",user.getUsername());
//        log.info("密码：[{}]",user.getPassword());
//        Map<String,Object> map = new HashMap<>();
//        try {
//            User userDB = userService.login(user);
//            Map<String, String> payload = new HashMap<>();
//            payload.put("name",userDB.getUsername());
//            // 生成jwt令牌
//            String token = JWTUtils.getToken(payload);
//            map.put("state",true);
//            map.put("msg","认证成功！");
//            map.put("token",token);  // 响应token
//        } catch (Exception e) {
//            map.put("state",false);
//            map.put("msg",e.getMessage());
//        }
//        return map;
//    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestParam String username, @RequestParam String password, @RequestParam String verifyCode, HttpSession session) {
        Result result = userService.login(username, password, verifyCode, session);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/common/verify")
    public void Verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 5, 4);
        //图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write(response.getOutputStream());
        //获取验证码中的文字内容
        String verifyCode = captcha.getCode();
        request.getSession().setAttribute("verifyCode",verifyCode);
    }

}
