package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.YxMapper.KcMessageMapper;
import zk.dao.YxMapper.YxMessageMapper;
import zk.dao.YxMapper.ZyMessageMapper;
import zk.dao.YxMapper.ZyfromYxMapper;
import zk.domain.DTO.YxMessage.KcMessage;
import zk.domain.DTO.YxMessage.YxMessage;
import zk.domain.DTO.YxMessage.ZyMessage;
import zk.domain.DTO.YxMessage.ZyfromYx;
import zk.domain.VO.YxMessage.ZyxqMessge;
import zk.service.Yx2ZyService;

import java.util.List;

@Service
public class Yx2zyServiceImpl implements Yx2ZyService {
    @Autowired
    private ZyMessageMapper zyMessageMapper;
    @Autowired
    private KcMessageMapper kcMessageMapper;
    @Autowired
    private YxMessageMapper yxMessageMapper;
    @Autowired
    private ZyfromYxMapper zyfromYxMapper;

    public ZyxqMessge getallmessage(String zy_dm){
        QueryWrapper<ZyMessage> zyqw = new QueryWrapper<>();
        QueryWrapper<KcMessage> kcqw = new QueryWrapper<>();
        QueryWrapper<YxMessage> yxqw = new QueryWrapper<>();
        ZyxqMessge zyxqMessge = new ZyxqMessge();

        zyqw.eq("zy_dm",zy_dm);
        List<ZyMessage> zyMessages = zyMessageMapper.selectList(zyqw);
        if (zyMessages.size() != 0){
            zyxqMessge.setZyMessage(zyMessages.get(0));
        }

        kcqw.eq("zy_dm",zy_dm);
        List<KcMessage> kcMessages = kcMessageMapper.selectList(kcqw);
        if (kcMessages != null){
            System.out.println(kcMessages);
            zyxqMessge.setKcMessages(kcMessages);
        }

        yxqw.eq("zy_dm",zy_dm);
        List<YxMessage> yxMessages = yxMessageMapper.selectList(yxqw);
        YxMessage yxMessage = yxMessages.get(0);
        System.out.println(yxMessage);
        if (yxMessage != null){
            System.out.println(yxMessage);
            zyxqMessge.setYxMessage(yxMessage);
        }
        return zyxqMessge;
    }

    @Override
    public String updatezybz(String zybz,String zy_dm) {

        return null;
    }

    @Override
    public List<ZyfromYx> checkby(ZyfromYx condition) {
        QueryWrapper<ZyfromYx> qw = new QueryWrapper<>(condition);
        return zyfromYxMapper.selectList(qw);
    }
}
