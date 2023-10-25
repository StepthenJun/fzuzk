package zk.service;

import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.VO.ZyMessage.YxZyMessageVo;

import java.util.List;

public interface ZyMessageService {
    List<ZyMessage> getZyData(String zy_mc);
    List<String> getZyType();
    List<KcMessage> getKcData(String zy_mc);

    List<YxMessage> getYxMessage(String zy_mc);
    String insertzyMessage();
    String updateZyMessage(String zy_dm,String zy_mc,String zy_yx);
    List<ZyYxMessage> checkZymessage();
    void deleteAll();
    Integer updategkkc(String kc_dm,int sj);
    YxZyMessageVo getzyfromyx(String zy_yx);
}
