package zk.domain.VO.ZyYxMessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.ZyMessage.ZykcMessage;
import zk.domain.DTO.ZyMessage.Zyxq;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("专业设置详情里的显示类")
public class ZyxqVO {
    @ApiModelProperty("专业详情")
    private Zyxq zyxq;
    @ApiModelProperty("课程的表")
    private List<ZykcMessage> zykcMessageList;
}
