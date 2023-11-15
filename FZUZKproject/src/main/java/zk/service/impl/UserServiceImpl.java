package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zk.dao.UserMapper;
import zk.domain.Login.User;
import zk.domain.Result;
import zk.service.UserService;
import zk.util.Md5Util;

import javax.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(String username, String password, String verifyCode, HttpSession session) {

        String sessionCode = (String) session.getAttribute("verifyCode");
        if (sessionCode == null || !sessionCode.equalsIgnoreCase(verifyCode)) {
            return Result.error("验证码错误");
        }
        // 对密码进行 MD5 加密
        String encryptedPassword = Md5Util.encrypt(password);

        // 验证用户名和加密后的密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && user.getPassword().equals(encryptedPassword)) {
            // 成功登录逻辑
            return Result.success("登录成功");
        } else {
            return Result.error("登录失败");
        }
    }
}
