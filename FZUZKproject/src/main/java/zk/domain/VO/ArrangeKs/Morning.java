package zk.domain.VO.ArrangeKs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("早上类")
public class Morning {
    @ApiModelProperty("课程代码")
    private String kc_dm;
    @ApiModelProperty("课程名称")
    private String kc_mc;
    @ApiModelProperty("层次")
    private String cc;
}
