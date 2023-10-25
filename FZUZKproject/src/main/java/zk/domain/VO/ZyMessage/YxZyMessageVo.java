package zk.domain.VO.ZyMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyfromYx;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YxZyMessageVo {
    private String zy_yx;
    private List<ZyfromYx> zyMessageList;
}
