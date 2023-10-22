package zk.domain.VO.ArrangeKs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("时间类")
public class Date {
    @ApiModelProperty("时间（星期）")
    private String sj;
    @ApiModelProperty("早上的课程表")
    private List<Morning> morningList;
    @ApiModelProperty("下午的课程表")
    private List<Afternoon> afternoonList;
}
