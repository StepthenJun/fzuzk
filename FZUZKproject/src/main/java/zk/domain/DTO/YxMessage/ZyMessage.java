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
@ApiModel("主考学校设置里点专业详情的最上面一层")
public class ZyMessage {
    @ApiModelProperty("专业名称")
    private String zy_mc;
    @ApiModelProperty("专业代码")
    private String zy_dm;
    @ApiModelProperty("层次")
    private String cc;
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("备考条件")
    private String bktj;
}
