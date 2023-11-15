package zk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import zk.domain.Login.User;
import zk.domain.Result;

import javax.servlet.http.HttpSession;

public interface UserService {
    Result login(String username, String password, String verifyCode, HttpSession session);
}
