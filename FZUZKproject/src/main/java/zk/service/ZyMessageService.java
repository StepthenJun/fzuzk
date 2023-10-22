package zk.service;

import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;

import java.util.List;

public interface ZyMessageService {
    List<ZyMessage> getZyData(String zy_mc);
    List<String> getZyType();
    List<KcMessage> getKcData(String zy_mc);

    List<YxMessage> getYxMessage(String zy_mc);
}
