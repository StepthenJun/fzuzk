package zk.domain.DTO.ArrangeZy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("zyxq_message")
@ApiModel("编排表课程属性")
public class TblKs {
    @ApiModelProperty("专业代码")
    private String zy_dm;
    @ApiModelProperty("专业名称")
    private String zy_mc;
    @ApiModelProperty("专业院校")
    private String zy_yx;
    @ApiModelProperty("上半年考试时间")
    private Integer ks_sj;
    @ApiModelProperty("课程名称")
    private String kc_mc;
    @ApiModelProperty("课程代码")
    private String kc_dm;
    @ApiModelProperty("是否国考")
    private String sf_gk;
    @ApiModelProperty("衔接考试方式")
    private String xjks_fs;
    @ApiModelProperty("下班年考试时间")
    private Integer ks_sjlater;
    @ApiModelProperty("考试方式")
    private String ks_fs;
    @ApiModelProperty("判断是否国考")
    private String bz;
    @ApiModelProperty("层次")
    private String cc;
    @ApiModelProperty("是否停止招生")
    private String sftzzs;
    @ApiModelProperty("委托开考")
    private String wtkk;
}
