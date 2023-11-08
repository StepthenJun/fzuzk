package zk.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserMapper userMapper;

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
    public String login(@RequestParam("username") String username,
                        @RequestParam("md5_password") String md5pwd,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {

        // 验证验证码
        String captchaCode = session.getAttribute("verifyCode") + "";
        if (!verifyCode.equals(captchaCode)) {
            return "验证码错误";
        }

        // 对密码加盐操作
        String salt_md5pwd = md5pwd + "zzuli";
        String final_md5pwd = DigestUtils.md5DigestAsHex(salt_md5pwd.getBytes()); // 最终的 MD5 密码

        User user = new User(username, final_md5pwd);

        // 具体的业务逻辑
        if (userMapper.selectOne(new QueryWrapper<>(user)) != null) {
            session.setAttribute("User", username); // 记录 session
            return "success"; // 跳转登录成功页面
        } else {
            return "用户名或者密码错误！";
        }
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
    @PostMapping("/loginc")
    public String loginByHutool(@RequestParam("verifyCode") String verifyCode,HttpSession session) {
        String captchaCode = session.getAttribute("verifyCode") + "";
        if(verifyCode.equals(captchaCode)){
            return "success";
        }
        return "false";
    }
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username, //接收从index.html 传参过来 参数：username
                        @RequestParam("md5_password") String md5pwd //接收的标识是前端的id字段
                        ,HttpSession session
    ){
        String salt_md5pwd=md5pwd+"fzusosd"; //后端加盐操作

        String final_md5pwd= DigestUtils.md5DigestAsHex(salt_md5pwd.getBytes());// 最终的 MD5 密码
        User user = new User(username,md5pwd);
        System.out.println(final_md5pwd);
        //具体的业务
        if(userMapper.selectOne(new QueryWrapper<>())!=null){  //如果查询结果不为空则用户、密码正确
            session.setAttribute("loginUser",username); //记录 session
            return "success";//跳转登录成功页面
        }
        else {
            Result.error( "用户名或者密码错误！");
        }
        return "index";
    }
/*
    @PostMapping("/loginnotoken")
    public Result<String> loginnotoken(User user){
        String loginnotoken = userService.loginnotoken(user);
        return Result.success(loginnotoken);
    }*/
}
