package zk.domain.VO.ZyMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KAZMessge {
    private YxMessage yxMessage;
    private ZyMessage zyMessage;
    private List<KcMessage> kcMessages;
}
