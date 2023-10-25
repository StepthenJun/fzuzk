package zk.domain.VO.ZyYxMessage;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.ZyYxMessage.ZykcMessage;
import zk.domain.DTO.ZyYxMessage.Zyxq;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZyxqVO {
    private Zyxq zyxq;
    @ApiModelProperty("课程的表")
    private List<ZykcMessage> zykcMessageList;
}
