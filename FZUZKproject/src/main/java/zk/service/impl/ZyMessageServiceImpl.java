package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.GkSjMapper;
import zk.dao.YxMapper.ZyfromYxMapper;
import zk.dao.ZyYxmessage.ZyYxMapper;
import zk.dao.zymessage.KcMessageMapper;
import zk.dao.TblKsMapper;
import zk.dao.zymessage.YxMessageMapper;
import zk.dao.zymessage.ZyMessageMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.domain.DTO.ZyMessage.ZyfromYx;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.VO.ArrangeKs.ZyTable;
import zk.domain.VO.ZyMessage.YxZyMessageVo;
import zk.service.BZyService;
import zk.service.ZyMessageService;
import zk.service.ZyYxService;

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
    @Autowired
    private BZyService bZyService;
    @Autowired
    private ZyYxMapper zyYxMapper;
    @Autowired
    private GkSjMapper gkSjMapper;
    @Autowired
    private ZyfromYxMapper zyfromYxMapper;

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

    public String insertzyMessage(){
        List<ZyTable> zyTable = bZyService.getZyTable();
        for (int i = 0; i < zyTable.size(); i++) {
            ZyYxMessage zyYxMessage = new ZyYxMessage();
            ZyTable table = zyTable.get(i);
            zyYxMessage.setZy_dm(table.getZy_dm());
            zyYxMessage.setZy_mc(table.getZy_mc());
            zyYxMessage.setZy_yx(table.getZy_yx());
            zyYxMapper.insert(zyYxMessage);
        }
        return "1";
    }
    //    修改专业院校和名称（并且要对应到编排表里的内容）
    public String updateZyMessage(String zy_dm,String zy_mc,String zy_yx){
        List<ZyTable> zyTable = bZyService.getZyTable();
        ZyYxMessage zyYxMessage = new ZyYxMessage();
        zyYxMessage.setZy_yx(zy_yx);
        zyYxMessage.setZy_mc(zy_mc);
        zyYxMessage.setZy_dm(zy_dm);
        UpdateWrapper<ZyYxMessage> uw = new UpdateWrapper<>();
        uw.eq("zy_dm",zy_dm);
        zyYxMapper.update(zyYxMessage,uw);
        for (int i = 0; i < zyTable.size(); i++) {
                UpdateWrapper<TblKs> uw1 = new UpdateWrapper<>();
                uw1.eq("zy_dm",zy_dm).set("zy_yx",zy_yx);
                uw.eq("zy_dm",zy_dm).set("zy_yx",zy_yx);
                zyYxMapper.update(null,uw);
                tblKsMapper.update(null,uw1);
        }
        return "1";
    }

//    查看专业
    public List<ZyYxMessage> checkZymessage(){
        List<ZyYxMessage> zyYxMessages = zyYxMapper.selectList(null);
        return zyYxMessages;
    }

    public void deleteAll(){
        zyYxMapper.delete(null);
    }

    public Integer updategkkc(String kc_dm,int sj){
        UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
        UpdateWrapper<GkSj> uw1 = new UpdateWrapper<>();
        String ks_sj = "";
            if (sj == 1){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","4月13日上午9:00-11:30");
            }if (sj == 2){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","4月13日下午14:30-17:00");
            }if (sj == 3){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","4月14日上午9:00-11:30");
            }if (sj == 4){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","4月14日下午14:30-17:00");
            }
            if (sj == 5){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","10月26日上午9:00-11:30");
            }if (sj == 6){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","10月26日下午14:30-17:00");
            }if (sj == 7){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","10月27日上午9:00-11:30");
            }if (sj == 8){
                uw1.eq("kc_dm",kc_dm).set("ks_sj","10月27日下午14:30-17:00");

        }
        int update = gkSjMapper.update(null, uw1);
        return update;
    }

    public YxZyMessageVo getzyfromyx(String zy_yx){
        QueryWrapper<ZyfromYx> qw = new QueryWrapper<>();
        qw.eq("zy_yx",zy_yx);
        List<ZyfromYx> zyMessages = zyfromYxMapper.selectList(qw);
        Map<String, Integer> zyMap = new HashMap<>();
        for (ZyfromYx zyfromYx : zyMessages) {
            String zy_mc = zyfromYx.getZy_mc();
            if (zyMap.containsKey(zy_mc)) {
                zyMap.put(zy_mc, zyMap.get(zy_mc) + 1);
            } else {
                zyMap.put(zy_mc, 1);
            }
        }
        List<ZyfromYx> zyfromYxList = new ArrayList<>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(zyMap.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            String zy_mc = entryList.get(i).getKey();
            QueryWrapper<ZyfromYx> qw1 = new QueryWrapper<>();
            qw1.eq("zy_yx",zy_yx).eq("zy_mc",zy_mc).last("LIMIT 1");
            ZyfromYx zyfromYx = zyfromYxMapper.selectOne(qw1);
            zyfromYxList.add(zyfromYx);
        }
        YxZyMessageVo yxZyMessageVo = new YxZyMessageVo(zy_yx,zyfromYxList);
        return yxZyMessageVo;
    }
}