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
@TableName("zyxq_message_new")
@ApiModel("专业设置点击详情里的课程表属性")
public class ZykcMessage {
//    这里要设置序号吗？
    @ApiModelProperty("当次开考")
    private String dckk;
    @ApiModelProperty("序号")
    private Integer xh;
    @ApiModelProperty("课程代码")
    private String kc_dm;
    @ApiModelProperty("考试备注")
    private String bz;
    @ApiModelProperty("学分")
    private String kc_xf;
    @ApiModelProperty("课程类型")
    private String kctype;
    @ApiModelProperty("课程属性")
    private String kcsx;
    @ApiModelProperty("衔接属性")
    private String xjks_fs;
    @ApiModelProperty("衔接课程分类")
    private String xjkcfl;
    @ApiModelProperty("课程名称")
    private String kc_mc;
    @ApiModelProperty("专业代码")
    private String zy_dm;
}
