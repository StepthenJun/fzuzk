package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.Zymessage.ZykcMessageMapper;
import zk.dao.Zymessage.ZyxqMapper;
import zk.domain.DTO.ZyMessage.ZykcMessage;
import zk.domain.DTO.ZyMessage.Zyxq;
import zk.domain.VO.ZyYxMessage.ZyxqVO;
import zk.service.ZyxqService;

import java.util.List;

@Service
public class ZyxqServiceImpl implements ZyxqService {
    @Autowired
    private ZykcMessageMapper zykcMessageMapper;
    @Autowired
    private ZyxqMapper zyxqMapper;

    public ZyxqVO checkZyxq(String zy_dm){
        QueryWrapper<ZykcMessage>  zykcqw= new QueryWrapper<>();
        zykcqw.select().eq("zy_dm",zy_dm);
        List<ZykcMessage> zykcMessages = zykcMessageMapper.selectList(zykcqw);
        QueryWrapper<Zyxq> zyxqqw = new QueryWrapper<>();
        zyxqqw.select().eq("zy_dm",zy_dm);
        List<Zyxq> zyxqs = zyxqMapper.selectList(zyxqqw);
        if (zyxqs != null){
            Zyxq zyxq = zyxqs.get(0);
            ZyxqVO zyxqVO = new ZyxqVO(zyxq,zykcMessages);
            return zyxqVO;
        }
        return null;
    }

    public String updateZyxq(ZyxqVO condition){
        Zyxq zyxq = condition.getZyxq();
        UpdateWrapper<Zyxq> zyxquw = new UpdateWrapper<>();
        zyxquw.eq("zy_dm",zyxq.getZy_dm());
        List<ZykcMessage> zykcMessageList = condition.getZykcMessageList();
        zyxqMapper.update(zyxq,zyxquw);
        for (int i = 0; i < zykcMessageList.size(); i++) {
            ZykcMessage zykcMessage = zykcMessageList.get(i);
            UpdateWrapper<ZykcMessage> zykcuw = new UpdateWrapper<>();
            zykcuw.eq("kc_dm",zykcMessage.getKc_dm());
            zykcMessageMapper.update(zykcMessage,zykcuw);
        }
        return "成功";
    }

    public String insertZyxq(ZyxqVO condition){
        if (condition != null){
        Zyxq zyxq = condition.getZyxq();
        QueryWrapper<Zyxq> qw = new QueryWrapper<>();
        qw.select(zyxq.getZy_dm());
        List<Zyxq> zyxqs = zyxqMapper.selectList(qw);
            String zyDm = zyxq.getZy_dm();
            zyxqMapper.insert(zyxq);
            List<ZykcMessage> zykcMessageList = condition.getZykcMessageList();
            if (zykcMessageList == null){
                return "你没有输入课程";
            }else {
                for (int i = 0; i < zykcMessageList.size(); i++) {
                    ZykcMessage zykcMessage = zykcMessageList.get(i);
                    zykcMessage.setZy_dm(zyDm);
                    UpdateWrapper<ZykcMessage> uw = new UpdateWrapper<>();
                    uw.eq("zy_dm",zyDm);
                    zykcMessageMapper.update(zykcMessage,uw);
                    if (i < zykcMessageList.size() - 1){
                    zyxqMapper.insert(zyxq);
                }
            }

            /*List<ZykcMessage> zykcMessageList = condition.getZykcMessageList();
            String zyDm = zyxq.getZy_dm();
            UpdateWrapper<Zyxq> uw = new UpdateWrapper<>();
            for (int i = 0; i < zykcMessageList.size(); i++) {
                ZykcMessage zykcMessage = zykcMessageList.get(i);
                zykcMessage.setZy_dm(zyDm);
                zykcMessageMapper.insert(zykcMessage);
                uw.eq("zy_dm",zyDm);
                zyxqMapper.update(zyxq,uw);
            }*/
        }
        return "成功";
        }else return "添加不能为空";
    }
}
