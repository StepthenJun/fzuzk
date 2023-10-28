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
@ApiModel("专业设置面板点击详情上面的属性（专业表）")
public class Zyxq {
    @ApiModelProperty("专业名称")
    private String zy_mc;
    @ApiModelProperty("专业代码")
    private String zy_dm;
    @ApiModelProperty("学分需求")
    private String zy_xf;
    @ApiModelProperty("主考院校")
    private String zy_yx;
    @ApiModelProperty("学历层次")
    private String cc;
    @ApiModelProperty("专业类型")
    private String zy_type;
    @ApiModelProperty("专业审批")
    private String zysp;
    @ApiModelProperty("审批时间")
    private String spsj;
    @ApiModelProperty("开考方式")
    private String kkfs;
    @ApiModelProperty("委托开考")
    private String wtkk;
    @ApiModelProperty("委托单位")
    private String wtdw;
    @ApiModelProperty("报考条件")
    private String bktj;
    @ApiModelProperty("证书停发")
    private String zstf;
    @ApiModelProperty("专业停考")
    private String zytk;
    @ApiModelProperty("是否停止招生")
    private String sftzzs;

}
