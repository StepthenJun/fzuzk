package zk.service;

import zk.domain.VO.YxMessage.ZyxqMessge;

public interface Yx2ZyService {
    ZyxqMessge getallmessage(String zy_dm);
    String updatezybz(String zybzm,String zy_dm);
}
