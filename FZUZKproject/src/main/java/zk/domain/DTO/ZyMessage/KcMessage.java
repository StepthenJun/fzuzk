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
@TableName("zyxq_message")
@ApiModel("主考学校设置里点专业详情里的课程表")
public class KcMessage {
    @ApiModelProperty("课程类型")
    private String kctype;
    @ApiModelProperty("课程代码")
    private String kc_dm;
    @ApiModelProperty("课程学分")
    private String kc_xf;
    @ApiModelProperty("面向社会")
    private String mxsh;
    @ApiModelProperty("体制改革")
    private String tzgg;
    @ApiModelProperty("衔接试点")
    private String xjsd;
    @ApiModelProperty("旧计划课程代码")
    private String jjhkc_dm;
    @ApiModelProperty("旧计划课程名称")
    private String jjhkc_mc;
    @ApiModelProperty("看是不是国考")
    private String bz;
    @ApiModelProperty("出版社")
    private String cbs;
}
