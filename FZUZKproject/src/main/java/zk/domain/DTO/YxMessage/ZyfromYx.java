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
@TableName("zyxq_message")
@ApiModel("专业主考学校的查询条件也是显示的对象")
public class ZyfromYx {
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("专业名称")
    private String zy_mc;
    @ApiModelProperty("专业学分")
    private String zy_xf;
    @ApiModelProperty("层次")
    private String cc;
    @ApiModelProperty("考试方式")
    private String ks_fs;
    @ApiModelProperty("专业审批")
    private String zysp;
    @ApiModelProperty("证书停发")
    private String zstf;
    @ApiModelProperty("专业停考")
    private String zytk;
    @ApiModelProperty("是否停止招生")
    private String sftzzs;
}
