package zk.service;

import zk.domain.DTO.YxMessage.KcMessage;
import zk.domain.DTO.YxMessage.YxMessage;
import zk.domain.DTO.YxMessage.ZyMessage;
import zk.domain.DTO.ZyMessage.ZyYxMessage;
import zk.domain.VO.YxMessage.YxZyMessageVo;

import java.util.List;

public interface ZyMessageService {
//    List<ZyMessage> getZyData(String zy_mc);
//    List<String> getZyType();
//    List<KcMessage> getKcData(String zy_mc);
List<String> getZyType();
//    List<YxMessage> getYxMessage(String zy_mc);
    String insertzyMessage();
    String updateZyMessage(String zy_dm,String zy_mc,String zy_yx);
    List<ZyYxMessage> checkZymessage();
//    void deleteAll();
    Integer updategkkc(String kc_dm,int sj);
    YxZyMessageVo getzyfromyx(String zy_yx);
}
