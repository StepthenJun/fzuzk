package zk.domain.VO.ArrangeKs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("课程表")
public class ZyTable  {
    @ApiModelProperty("1.专业名称")
    private String zy_mc;
    @ApiModelProperty("2.专业代码")
    private String zy_dm;
    @ApiModelProperty("3.专业院校")
    private String zy_yx;
    @ApiModelProperty("4.时间")
    private List<Date> date;

}
