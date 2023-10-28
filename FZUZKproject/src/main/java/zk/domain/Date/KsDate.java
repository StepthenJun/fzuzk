package zk.domain.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("设置考试时间实体类")
public class KsDate {
    @ApiModelProperty("标识")
    private String sign;
    @ApiModelProperty("考试时间")
    private String sj;
}
