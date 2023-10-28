package zk.domain.DTO.YxMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_message")
@ApiModel("主考学校设置里点专业详情里的院校表")
public class YxMessage {
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("专业代码")
    private String zy_dm;
    @ApiModelProperty("专业备注")
    private String zy_bz;
    @ApiModelProperty("体制改革院校")
    private String tzgg_yx;
    @ApiModelProperty("衔接试点院校")
    private String xjsd_yx;
    @ApiModelProperty("衔接二学历院校")
    private String xjexl_yx;
    @ApiModelProperty("开考专业备注")
    private String kkzy_bz;
    @ApiModelProperty("体制衔接院校")
    private String tzxs_yx;
}
