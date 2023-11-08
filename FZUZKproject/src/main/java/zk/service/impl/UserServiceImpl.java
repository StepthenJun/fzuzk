package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import zk.dao.UserMapper;
import zk.domain.Login.User;
import zk.service.UserService;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user){
            // 根据接收用户名密码查询数据库
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername()).eq("password",user.getPassword());
            User userDB = userMapper.selectOne(qw);
            if (userDB!=null){
                return userDB;
            }
            throw  new RuntimeException("登录失败 -.-");


    }
    @Override
    public String loginnotoken(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername()).eq("password",user.getPassword());
        User user1 = userMapper.selectOne(qw);
        if (user1 == null){
            return "账号密码错误";
        }
         return "登入成功";
    }


}
