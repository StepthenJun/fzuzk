package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zk.dao.ZyYxmessage.ZyYxMapper;
import zk.dao.ZyYxmessage.ZykcMessageMapper;
import zk.dao.ZyYxmessage.ZyxqMapper;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.DTO.ZyYxMessage.ZykcMessage;
import zk.domain.DTO.ZyYxMessage.Zyxq;
import zk.domain.VO.ZyYxMessage.ZyxqVO;
import zk.service.ZyxqService;

import java.lang.reflect.Field;
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

    public String updateZyxq(ZyxqVO zyxqVO){
        /*Zyxq zyxq = zyxqVO.getZyxq();
        UpdateWrapper<Zyxq> zyxquw = new UpdateWrapper<>();
        zyxquw.eq("zy_dm",zyxq.getZy_dm());
        List<ZykcMessage> zykcMessageList = zyxqVO.getZykcMessageList();
        zyxqMapper.update(zyxq,zyxquw);
        for (int i = 0; i < zykcMessageList.size(); i++) {
            ZykcMessage zykcMessage = zykcMessageList.get(i);
            UpdateWrapper<ZykcMessage> zykcuw = new UpdateWrapper<>();
            zykcuw.eq("kc_dm",zykcMessage.getKc_dm());
            zykcMessageMapper.update(zykcMessage,zykcuw);
        }*/

        Field[] fields = ZyxqVO.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(zyxqVO);
                if (value != null && !StringUtils.isEmpty(value.toString())) {
                    Zyxq zyxq = zyxqVO.getZyxq();
                    UpdateWrapper<Zyxq> zyxquw = new UpdateWrapper<>();
                    zyxquw.eq("zy_dm",zyxq.getZy_dm());
                    List<ZykcMessage> zykcMessageList = zyxqVO.getZykcMessageList();
                    zyxqMapper.update(zyxq,zyxquw);
                    for (int i = 0; i < zykcMessageList.size(); i++) {
                        ZykcMessage zykcMessage = zykcMessageList.get(i);
                        UpdateWrapper<ZykcMessage> zykcuw = new UpdateWrapper<>();
                        zykcuw.eq("kc_dm",zykcMessage.getKc_dm());
                        zykcMessageMapper.update(zykcMessage,zykcuw);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "成功";
    }
}
