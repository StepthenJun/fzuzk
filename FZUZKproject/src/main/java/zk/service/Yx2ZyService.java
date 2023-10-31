package zk.service;

import zk.domain.DTO.YxMessage.ZyfromYx;
import zk.domain.VO.YxMessage.ZyxqMessge;

import java.util.List;

public interface Yx2ZyService {
    ZyxqMessge getallmessage(String zy_dm);
    String updatezybz(String zybzm,String zy_dm);

    List<ZyfromYx> checkby(ZyfromYx zyfromYx);
}
