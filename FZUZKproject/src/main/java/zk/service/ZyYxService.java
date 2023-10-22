package zk.service;

import zk.domain.DTO.ZyYxMessage.ZyYxMessage;

import java.util.List;

public interface ZyYxService {
    List<ZyYxMessage> getzyyxmessage(ZyYxMessage zyYxMessage);

    int Delete(String zy_dm);
}
