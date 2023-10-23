package zk.domain.DTO.ZyYxMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("关于专业的表")
@TableName("zkyx_message")
public class ZyYxMessage {
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("专业名称")
    private String zy_mc;
    private String byxf;
    private String xlcc;
    private String zysp;
    private String zstf;
    private String zytk;
    private String sftzzs;
    private String kkzt;
    @ApiModelProperty("专业代码")
    private String zy_dm;
}
