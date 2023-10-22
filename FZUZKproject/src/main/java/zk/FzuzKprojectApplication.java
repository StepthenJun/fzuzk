package zk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("zk.dao")
public class FzuzKprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FzuzKprojectApplication.class, args);
    }

}
