package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zk.dao.UserMapper;
import zk.domain.Login.User;
import zk.service.UserService;

import java.util.Date;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    private String jwtSecret;
    private long jwtExpirationMs;

    public String loginUser(User user) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername());
        User foundUser = userMapper.selectOne(qw);
        System.out.println(generateJwtToken(foundUser));
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return generateJwtToken(foundUser);
        }
        return null;
    }

    public String generateJwtToken(User user) {
        Date now = new Date();
        jwtExpirationMs = 86400000;
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);
        jwtSecret = "qwertyuiop";
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public User getOne(QueryWrapper queryWrapper) {
        User user = userMapper.selectOne(queryWrapper);
        return user;
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
