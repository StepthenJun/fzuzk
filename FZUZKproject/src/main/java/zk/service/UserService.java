package zk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import zk.domain.Login.User;

public interface UserService {
    String loginnotoken(User user);
    User login(User user);
}
