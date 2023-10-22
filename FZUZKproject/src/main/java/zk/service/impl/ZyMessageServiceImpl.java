package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.zymessage.KcMessageMapper;
import zk.dao.TblKsMapper;
import zk.dao.zymessage.YxMessageMapper;
import zk.dao.zymessage.ZyMessageMapper;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.service.ZyMessageService;

import java.util.*;

@Service
public class ZyMessageServiceImpl implements ZyMessageService {
    @Autowired
    private ZyMessageMapper zyMessageMapper;
    @Autowired
    private TblKsMapper tblKsMapper;
    @Autowired
    private KcMessageMapper kcMessageMapper;
    @Autowired
    private YxMessageMapper yxMessageMapper;

//    获取专业信息（根据专业名称题头那部分）
    public List<ZyMessage> getZyData(String zy_dm){
        QueryWrapper<ZyMessage> qw = new QueryWrapper<>();
        qw.select().eq("zy_dm",zy_dm);
        List<ZyMessage> zyMessages = zyMessageMapper.selectList(qw);
        return zyMessages;
    }
//   获取专业相关的课程
    public List<KcMessage> getKcData(String zy_dm){
        QueryWrapper<KcMessage> qw = new QueryWrapper<>();
        qw.select().eq("zy_dm",zy_dm);
        List<KcMessage> kcMessages = kcMessageMapper.selectList(qw);
        UpdateWrapper<KcMessage> uw = new UpdateWrapper<>();
        for (int i = 0; i < kcMessages.size(); i++) {
//            或许就不用更新，每次都刷新一次序号就好了
            /*uw.eq("kc_dm",kcMessages.get(i).getKc_dm()).set("id",i+1);*/
            kcMessages.get(i).setId(i + 1);
        }
        return kcMessages;
    }
//   获取专业类型的
    public List<String> getZyType(){
        QueryWrapper<ZyMessage> qw = new QueryWrapper<>();
        qw.select("zy_yx");
        List<ZyMessage> zyTypes = zyMessageMapper.selectList(qw);
        HashSet<String> set = new HashSet<String>(zyTypes.size());
        List<String> result = new ArrayList<String>(zyTypes.size());
        for (int i = 0; i < zyTypes.size(); i++) {
            String zy_yx = zyTypes.get(i).getZy_yx();
            if (set.add(zy_yx)){
                result.add(zy_yx);
            }
        }
        return result;
    }
//    最下面备注部分的
    public List<YxMessage> getYxMessage(String zy_dm){
        QueryWrapper<YxMessage> qw = new QueryWrapper<>();
        qw.select().eq("zy_dm",zy_dm);
        List<YxMessage> yxMessages = yxMessageMapper.selectList(qw);
        return yxMessages;
    }
}
