package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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

    /*@Override
    public List<ZyfromYx> checkby(ZyfromYx condition) {
        QueryWrapper<ZyfromYx> qw = new QueryWrapper<>(condition);
        return zyfromYxMapper.selectList(qw);
    }*/

    @Override
    public List<ZyfromYx> checkby(ZyfromYx condition) {
        QueryWrapper<ZyfromYx> qw = new QueryWrapper<>();
        // 对每个字段进行模糊查询
        qw.like(StringUtils.isNotBlank(condition.getZy_dm()), "zy_dm", condition.getZy_dm())
                .like(StringUtils.isNotBlank(condition.getZy_yx()), "zy_yx", condition.getZy_yx())
                .like(StringUtils.isNotBlank(condition.getZy_mc()), "zy_mc", condition.getZy_mc())
                .like(StringUtils.isNotBlank(condition.getZy_xf()), "zy_xf", condition.getZy_xf())
                .like(StringUtils.isNotBlank(condition.getCc()), "cc", condition.getCc())
                .like(StringUtils.isNotBlank(condition.getKs_fs()), "ks_fs", condition.getKs_fs())
                .like(StringUtils.isNotBlank(condition.getZysp()), "zysp", condition.getZysp())
                .like(StringUtils.isNotBlank(condition.getZstf()), "zstf", condition.getZstf())
                .like(StringUtils.isNotBlank(condition.getZytk()), "zytk", condition.getZytk())
                .like(StringUtils.isNotBlank(condition.getSftzzs()), "sftzzs", condition.getSftzzs());

        return zyfromYxMapper.selectList(qw);
    }
}
