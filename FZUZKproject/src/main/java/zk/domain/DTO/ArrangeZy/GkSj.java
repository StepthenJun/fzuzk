package zk.domain.DTO.ArrangeZy;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gk_sjtest")
@Api("国考时间")
public class GkSj {
    @ApiModelProperty("课程代码")
    private String kc_dm;
    @ApiModelProperty("课程名称")
    private String kc_mc;
    @ApiModelProperty("考试时间")
    private String ks_sj;
}
