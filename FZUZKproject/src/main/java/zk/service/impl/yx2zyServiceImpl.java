package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.zymessage.KcMessageMapper;
import zk.dao.zymessage.YxMessageMapper;
import zk.dao.zymessage.ZyMessageMapper;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.domain.VO.ZyMessage.KAZMessge;
import zk.service.Yx2ZyService;

import java.util.List;

@Service
public class yx2zyServiceImpl implements Yx2ZyService {
    @Autowired
    private ZyMessageMapper zyMessageMapper;
    @Autowired
    private KcMessageMapper kcMessageMapper;
    @Autowired
    private YxMessageMapper yxMessageMapper;

    public KAZMessge getallmessage(String zy_dm){
        QueryWrapper<ZyMessage> zyqw = new QueryWrapper<>();
        QueryWrapper<KcMessage> kcqw = new QueryWrapper<>();
        QueryWrapper<YxMessage> yxqw = new QueryWrapper<>();
        KAZMessge kazMessge = new KAZMessge();

        zyqw.eq("zy_dm",zy_dm);
        List<ZyMessage> zyMessages = zyMessageMapper.selectList(zyqw);
        if (zyMessages.size() != 0){
            kazMessge.setZyMessage(zyMessages.get(0));
        }

        kcqw.eq("zy_dm",zy_dm);
        List<KcMessage> kcMessages = kcMessageMapper.selectList(kcqw);
        if (kcMessages != null){
            System.out.println(kcMessages);
            kazMessge.setKcMessages(kcMessages);
        }

        yxqw.eq("zy_dm",zy_dm);
        YxMessage yxMessage = yxMessageMapper.selectOne(yxqw);
        System.out.println(yxMessage);
        if (yxMessage != null){
            System.out.println(yxMessage);
            kazMessge.setYxMessage(yxMessage);
        }
        return kazMessge;
    }
}
