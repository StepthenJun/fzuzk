package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.dao.GkSjnewMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.GkSjnew;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.service.GknewService;

import java.util.List;

@Service
public class GknewServiceImpl extends ServiceImpl<GkSjnewMapper, GkSjnew> implements GknewService {
    @Autowired
    private GkSjnewMapper gkSjnewMapper;

    @Override
    public void arrangeGk(List<GkSj> oldgkList) {
        QueryWrapper<GkSjnew> qw = new QueryWrapper<>();
        List<GkSjnew> newgkSjList = gkSjnewMapper.selectList(qw);
//        将国考对应的时间（代码1，2，3..）添加到考试表里的时间，先把国考的确定了
        for (int i = 0; i < newgkSjList.size(); i++) {
            String kc_mc = newgkSjList.get(i).getKc_mc();
            for (int j = 0; j < oldgkList.size(); j++) {
                 GkSj gk= oldgkList.get(j);
                UpdateWrapper<GkSjnew> uw = new UpdateWrapper<>();
                if (gk.getKc_mc().compareTo(kc_mc) == 0){
                    uw.eq("kc_mc",kc_mc);
                    String ksSj = gk.getKs_sj();
                    if (ksSj.equals("4月15日上午9:00-11:30")){
                        String newkssj = "4月13日上午9:00-11:30";
                        uw.set("ks_sj",newkssj);
                        gkSjnewMapper.update(null,uw);
                    }if (ksSj.equals("4月15日下午14:30-17:00")){
                        String newkssj = "4月13日下午14:30-17:00";
                        uw.set("ks_sj",newkssj);
                        gkSjnewMapper.update(null,uw);
                    }if (ksSj.equals("4月16日上午9:00-11:30")){
                        String newkssj = "4月14日上午9:00-11:30";
                        uw.set("ks_sj",newkssj);
                        gkSjnewMapper.update(null,uw);
                    }if (ksSj.equals("4月16日下午14:30-17:00")){
                        String newkssj = "4月14日下午14:30-17:00";
                        uw.set("ks_sj",newkssj);
                        gkSjnewMapper.update(null,uw);
                    }if (ksSj.equals("10月28日上午9:00-11:30")){
                        System.out.println("28");
                        String newkssj = "10月26日上午9:00-11:30";
                        GkSjnew gkSjnew = new GkSjnew(gk.getKc_dm(),newgkSjList.get(i).getKc_mc(),newkssj);
                        gkSjnewMapper.insert(gkSjnew);
                        break;
                    }if (ksSj.equals("10月28日下午14:30-17:00")){
                        String newkssj = "10月26日下午14:30-17:00";
                        GkSjnew gkSjnew = new GkSjnew(gk.getKc_dm(),newgkSjList.get(i).getKc_mc(),newkssj);
                        gkSjnewMapper.insert(gkSjnew);
                        break;
                    }if (ksSj.equals("10月29日上午9:00-11:30")){
                        String newkssj = "10月27日上午9:00-11:30";
                        GkSjnew gkSjnew = new GkSjnew(gk.getKc_dm(),newgkSjList.get(i).getKc_mc(),newkssj);
                        gkSjnewMapper.insert(gkSjnew);
                        break;
                    }if (ksSj.equals("10月29日下午14:30-17:00")){
                        String newkssj = "10月27日下午14:30-17:00";
                        GkSjnew gkSjnew = new GkSjnew(gk.getKc_dm(),newgkSjList.get(i).getKc_mc(),newkssj);
                        gkSjnewMapper.insert(gkSjnew);
                        break;
                    }
                }
            }
        }
    }

    public void insert(GkSjnew newgk,String sj){
        if (newgk.getKs_sj() != null){
            GkSjnew gkSjnew = new GkSjnew(newgk.getKc_dm(),newgk.getKc_mc(),sj);
            gkSjnewMapper.insert(gkSjnew);
        }
    }
}
