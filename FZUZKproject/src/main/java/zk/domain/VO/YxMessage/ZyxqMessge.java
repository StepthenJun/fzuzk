package zk.domain.VO.YxMessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.YxMessage.KcMessage;
import zk.domain.DTO.YxMessage.YxMessage;
import zk.domain.DTO.YxMessage.ZyMessage;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("专业主考学校里详情里的信息展示类")
public class ZyxqMessge {
    @ApiModelProperty("院校信息（下半部分）")
    private YxMessage yxMessage;
    @ApiModelProperty("专业信息（上班部分）")
    private ZyMessage zyMessage;
    @ApiModelProperty("课程列表")
    private List<KcMessage> kcMessages;
}
