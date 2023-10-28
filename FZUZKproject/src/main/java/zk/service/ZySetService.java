package zk.service;

import zk.domain.DTO.ZyMessage.ZyYxMessage;

import java.util.List;

public interface ZySetService {
    List<ZyYxMessage> getzyyxmessage(ZyYxMessage zyYxMessage);

    String Delete(String zy_dm);

}
