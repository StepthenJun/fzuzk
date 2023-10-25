package zk.domain.DTO.ZyYxMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("zyxq_message")
public class ZykcMessage {
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
    private String xjsx;
    @ApiModelProperty("衔接课程分类")
    private String xjkcfl;
    @ApiModelProperty("课程名称")
    private String kc_mc;
}
