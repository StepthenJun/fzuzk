package zk.domain.DTO.ZyMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("专业设置展示的每一条（展示类也是实体类）")
@TableName("zkyx_message_new")
public class ZyYxMessage {
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("专业名称")
    private String zy_mc;
    @ApiModelProperty("毕业学分")
    private String byxf;
    @ApiModelProperty("学历层次")
    private String xlcc;
    @ApiModelProperty("专业审批")
    private String zysp;
    @ApiModelProperty("证书停发")
    private String zstf;
    @ApiModelProperty("专业停考")
    private String zytk;
    @ApiModelProperty("是否停止招生")
    private String sftzzs;
    @ApiModelProperty("开考状态")
    private String kkzt;
    @ApiModelProperty("专业代码")
    private String zy_dm;
}
