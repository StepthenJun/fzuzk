package zk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.LoginMapper;
import zk.domain.Login.Login;
import zk.service.LoginService;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public String login(String username,String password){
        List<Login> logins = loginMapper.selectList(null);
        String ifpass = "no";
        for (int i = 0; i < logins.size(); i++) {
            Login login = logins.get(i);
            if (login.getPassword().equals(password) && login.getUsername().equals(username)){
                ifpass = "pass";
                break;
            }
        }
        return ifpass;
    }
}
