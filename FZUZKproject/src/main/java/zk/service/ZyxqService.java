package zk.service;

import zk.domain.VO.ZyYxMessage.ZyxqVO;

public interface ZyxqService {
    ZyxqVO checkZyxq(String zy_dm);
    String updateZyxq(ZyxqVO zyxqVO);
}
