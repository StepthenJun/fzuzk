package zk.domain.Login;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("usermessage")
@ApiModel("登录类")
public class Login {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("登录密码")
    private String password;
}
