package zk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import zk.domain.Login.User;

public interface UserService {
    String loginUser(User user);
    String generateJwtToken(User user);
    User getOne(QueryWrapper queryWrapper);

    String loginnotoken(User user);
}
