package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import zk.dao.GkSjMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.dao.TblKsMapper;
import zk.domain.VO.ArrangeKs.*;
import zk.domain.VO.ArrangeKs.Date;
import zk.service.BZyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 专业表 服务实现类
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
@Service
public class BZyServiceImpl extends ServiceImpl<TblKsMapper, TblKs> implements BZyService {
    @Autowired
    private TblKsMapper bzymapper;
    @Autowired
    private GkSjMapper gkSjMapper;
    @Autowired
    private TblKsMapper tblKsMapper;

    //excel还是json?
    @Override
    public List<TblKs> orderlist() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select("zy_dm", "kc_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Map<String, Integer> zyMap = new HashMap<>();
        for (TblKs tbl : tblKs) {
            String zy_dm = tbl.getZy_dm();
            if (zyMap.containsKey(zy_dm)) {
                zyMap.put(zy_dm, zyMap.get(zy_dm) + 1);
            } else {
                zyMap.put(zy_dm, 1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(zyMap.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
//            加强匹配（专业名称和专业代码双重匹配）
            String zy_dm = entry.getKey();
//            获取在循环相关专业的课程
            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.eq("zy_dm", zy_dm).eq("bz", "国考").eq("ks_fs", "笔试");
            List<TblKs> gK = tblKsMapper.selectList(qw1);
            QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
            qw2.eq("zy_dm", zy_dm).eq("xjks_fs", "专业核心").eq("bz", "省考").eq("ks_fs", "笔试");
            List<TblKs> zyHx = tblKsMapper.selectList(qw2);
            int[] counts = arrangeHx(gK, zyHx);
//            对list里的对象随机取出，再对其进行时间的赋值
            //创建一个单独专业的list
            List<TblKs> randomSelectionList = new ArrayList<>();
            Random random = new Random();
            QueryWrapper<TblKs> qw3 = new QueryWrapper<>();
            qw3.select()
                    .eq("zy_dm", zy_dm)
                    .and(qww -> qww
                            .eq("xjks_fs", "公共基础")
                            .or()
                            .isNull("xjks_fs")
                    )
                    .eq("bz", "省考").eq("ks_fs", "笔试");
            List<TblKs> zyList = tblKsMapper.selectList(qw3);
//           取一个放到一个新的list中再对其随机进行取出再放到一个新的list中作为打乱（只要对randomlist进行抽取就好了）
            while (!zyList.isEmpty()) {
                int randomIndex = random.nextInt(zyList.size());
                TblKs kc = zyList.get(randomIndex);
                randomSelectionList.add(kc);
                zyList.remove(randomIndex);
            }
            int min = 0;
//           可以对相对应专业的课程进行排序了
            for (int j = 0; j < randomSelectionList.size(); j++) {
                UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
                int sj = 0;
                for (int k = 0; k < counts.length; k++) {

                    min = Arrays.stream(counts).min().getAsInt();

                    if (counts[k] <= min) {
                        sj = k + 1;
                        counts[k]++;
                        break;
                    }
                }
                randomSelectionList.get(j).setKs_sj(sj);
                uw.eq("kc_dm", randomSelectionList.get(j).getKc_dm())
                        .set("ks_sj", sj);
                tblKsMapper.update(null, uw);
            }
        }
        return list();
    }

    //按排上半年的考试
    @Override
    public int[] arrangeGk(List<TblKs> ksList) {
        QueryWrapper<GkSj> qw = new QueryWrapper<>();
        List<GkSj> gkSjList = gkSjMapper.selectList(qw);
        int[] counts = {0, 0, 0, 0};
//        将国考对应的时间（代码1，2，3..）添加到考试表里的时间，先把国考的确定了
        for (int i = 0; i < ksList.size(); i++) {
            String kc_dm = ksList.get(i).getKc_dm();
            for (int j = 0; j < gkSjList.size(); j++) {
                GkSj gk = gkSjList.get(j);
                UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
                if (gk.getKc_dm().compareTo(kc_dm) == 0) {
                    uw.eq("kc_dm", kc_dm);
//                    如果时间为这个就赋值时间代码1（依次类推）
//                    这里我感觉要改成if一次性判断
                    String ksSj = gk.getKs_sj();
                    if (ksSj.equals("4月13日上午9:00-11:30")) {
                        ksList.get(i).setKs_sj(1);
                        uw.set("ks_sj", 1);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("4月13日下午14:30-17:00")) {
                        ksList.get(i).setKs_sj(2);
                        uw.set("ks_sj", 2);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("4月14日上午9:00-11:30")) {
                        ksList.get(i).setKs_sj(3);
                        uw.set("ks_sj", 3);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("4月14日下午14:30-17:00")) {
                        ksList.get(i).setKs_sj(4);
                        uw.set("ks_sj", 4);
                        bzymapper.update(null, uw);
                        break;
                    } else {
                        ksList.get(i).setKs_sj(null);
                        uw.set("ks_sj", null);
                        bzymapper.update(null, uw);
                        break;
                    }
                }
            }
        }
//        这段是将国考已经占有的时间塞进计数器里
        for (int i = 0; i < ksList.size(); i++) {
            if (ksList.get(i).getKs_sj() != null) {
                int add = ksList.get(i).getKs_sj() - 1;
                for (int j = 0; j < counts.length; j++) {
                    if (add == j) {
                        counts[j]++;
                    }
                }
            }
        }
        return counts;
    }

    //    抽取核心考试的时间段
    @Override
    public int[] arrangeHx(List<TblKs> gK, List<TblKs> zyHx) {
        int[] arrangedGk = arrangeGk(gK);
//        这里也要做到随机
//        操，这里忘记随机了md难怪
//        要获取安排完国考的计数器里的最小值
        int min = 0;
        for (int i = 0; i < zyHx.size(); i++) {
            UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
            uw.eq("kc_dm", zyHx.get(i).getKc_dm());
            int sj = 0;
            for (int j = 0; j < arrangedGk.length; j++) {

                min = Arrays.stream(arrangedGk).min().getAsInt();

                if (arrangedGk[j] <= min) {
                    sj = j + 1;
                    arrangedGk[j]++;
                    break;
                }
            }
            zyHx.get(i).setKs_sj(sj);
            uw.eq("kc_dm", zyHx.get(i).getKc_dm()).set("ks_sj", sj);
            bzymapper.update(null, uw);
        }
        return arrangedGk;
    }


    //安排下半年的考试
    @Override
    public List<TblKs> orderlistlater() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select("zy_dm", "kc_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Map<String, Integer> zyMap = new HashMap<>();
        for (TblKs tbl : tblKs) {
            String zy_dm = tbl.getZy_dm();
            if (zyMap.containsKey(zy_dm)) {
                zyMap.put(zy_dm, zyMap.get(zy_dm) + 1);
            } else {
                zyMap.put(zy_dm, 1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(zyMap.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
//            加强匹配（专业名称和专业代码双重匹配）
            String zy_dm = entry.getKey();
//            获取在循环相关专业的课程
            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.eq("zy_dm", zy_dm).eq("bz", "国考").eq("ks_fs", "笔试");
            List<TblKs> gK = tblKsMapper.selectList(qw1);
            QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
            qw2.eq("xjks_fs", "专业核心").eq("bz", "省考").eq("zy_dm", zy_dm).eq("ks_fs", "笔试");
            List<TblKs> zyHx = tblKsMapper.selectList(qw2);
            int[] counts = arrangeHxLater(gK, zyHx);

//            对list里的对象随机取出，再对其进行时间的赋值
            //创建一个单独专业的list
            List<TblKs> randomSelectionList = new ArrayList<>();
            Random random = new Random();
            QueryWrapper<TblKs> qw3 = new QueryWrapper<>();
            qw3.select()
                    .eq("zy_dm", zy_dm)
                    .and(qww -> qww
                            .eq("xjks_fs", "公共基础")
                            .or()
                            .isNull("xjks_fs")
                    )
                    .eq("bz", "省考").eq("ks_fs", "笔试");
            List<TblKs> zyList = tblKsMapper.selectList(qw3);
//           取一个放到一个新的list中再对其随机进行取出再放到一个新的list中作为打乱（只要对randomlist进行抽取就好了）
            while (!zyList.isEmpty()) {
                int randomIndex = random.nextInt(zyList.size());
                TblKs kc = zyList.get(randomIndex);
                randomSelectionList.add(kc);
                zyList.remove(randomIndex);
            }
            int min = 0;
//           可以对相对应专业的课程进行排序了
            for (int j = 0; j < randomSelectionList.size(); j++) {
                UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
                int sj = 0;
                for (int k = 0; k < counts.length; k++) {

                    min = Arrays.stream(counts).min().getAsInt();

                    if (counts[k] <= min) {
                        sj = k + 1 + 4;
                        counts[k]++;
                        break;
                    }
                }
                randomSelectionList.get(j).setKs_sjlater(sj);
                uw.eq("kc_dm", randomSelectionList.get(j).getKc_dm())
                        .set("ks_sjlater", sj);
                tblKsMapper.update(null, uw);
            }
        }
        return list();
    }

    @Override
    public int[] arrangeGkLater(List<TblKs> ksList) {
        QueryWrapper<GkSj> qw = new QueryWrapper<>();
        List<GkSj> gkSjList = gkSjMapper.selectList(qw);
        int[] counts = {0, 0, 0, 0};

//        将国考对应的时间（代码1，2，3..）添加到考试表里的时间，先把国考的确定了
        for (int i = 0; i < ksList.size(); i++) {
            String kc_dm = ksList.get(i).getKc_dm();
            for (int j = 0; j < gkSjList.size(); j++) {
                GkSj gk = gkSjList.get(j);
                UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
                if (gk.getKc_dm().compareTo(kc_dm) == 0) {
                    uw.eq("kc_dm", kc_dm);
//                    如果时间为这个就赋值时间代码1（依次类推）
                    String ksSj = gk.getKs_sj();
                    if (ksSj.equals("10月26日上午9:00-11:30")) {
                        ksList.get(i).setKs_sjlater(5);
                        uw.set("ks_sjlater", 5);
                        System.out.println(kc_dm);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("10月26日下午14:30-17:00")) {
                        ksList.get(i).setKs_sjlater(6);
                        uw.set("ks_sjlater", 6);
                        bzymapper.update(null, uw);
                        System.out.println(kc_dm);
                        break;
                    }
                    if (ksSj.equals("10月27日上午9:00-11:30")) {
                        ksList.get(i).setKs_sjlater(7);
                        uw.set("ks_sjlater", 7);
                        bzymapper.update(null, uw);
                        System.out.println(kc_dm);
                        break;
                    }
                    if (ksSj.equals("10月27日下午14:30-17:00")) {
                        ksList.get(i).setKs_sjlater(8);
                        uw.set("ks_sjlater", 8);
                        bzymapper.update(null, uw);
                        break;
                    }/*else{
                    ksList.get(i).setKs_sjlater(null);
                    uw.set("ks_sjlater",null);
                    bzymapper.update(null,uw);
                    break;
                }*/
                }
            }
        }
//        这段是将国考已经占有的时间塞进计数器里
        for (int i = 0; i < ksList.size(); i++) {
            if (ksList.get(i).getKs_sjlater() != null) {
                int add = ksList.get(i).getKs_sjlater() - 1;
                for (int j = 0; j < counts.length; j++) {
                    if (add == j) {
                        counts[j]++;
                    }
                }
            }
        }
        return counts;
    }

    @Override
    public int[] arrangeHxLater(List<TblKs> gK, List<TblKs> zyHx) {
        int[] arrangedGk = arrangeGkLater(gK);
//        System.out.println(hxs);
//        这里也要做到随机
//        要获取安排完国考的计数器里的最小值
        int min = 0;
        for (int i = 0; i < zyHx.size(); i++) {
            UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
            uw.eq("kc_dm", zyHx.get(i).getKc_dm());
            int sj = 0;
            for (int j = 0; j < arrangedGk.length; j++) {

                min = Arrays.stream(arrangedGk).min().getAsInt();

                if (arrangedGk[j] <= min) {
                    sj = j + 1 + 4;
                    arrangedGk[j]++;
                    break;
                }
            }
            zyHx.get(i).setKs_sjlater(sj);
            uw.eq("kc_dm", zyHx.get(i).getKc_dm()).set("ks_sjlater", sj);
            bzymapper.update(null, uw);
        }
        return arrangedGk;
    }

    // 得到最终的展现表
    @Override
    public List<ZyTable> getZyTable() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select();
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        List<ZyTable> zyTable = new ArrayList<>();
        Map<String, Integer> zyMap = new HashMap<>();
        for (TblKs tbl : tblKs) {
            String zy_dm = tbl.getZy_dm();
            if (zyMap.containsKey(zy_dm)) {
                zyMap.put(zy_dm, zyMap.get(zy_dm) + 1);
            } else {
                zyMap.put(zy_dm, 1);
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(zyMap.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String, Integer> entry = entryList.get(i);
            String zy_dm = entry.getKey();
            int zy_kcCount = entry.getValue();

            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.select().eq("zy_dm", zy_dm);
            List<TblKs> ksTable = bzymapper.selectList(qw1);

            ZyTable zytbl = new ZyTable();
            zytbl.setZy_dm(zy_dm);
            zytbl.setZy_mc(ksTable.get(0).getZy_mc());
            zytbl.setZy_yx(ksTable.get(0).getZy_yx());
            List<Date> dateList = new ArrayList<>();

            int count = 0;
            for (int l = 1; l <= 4; l++) {
                Date date = new Date();
                date.setSj(getKsDate(l));
                List<Morning> morningList = new ArrayList<>();
                List<Afternoon> afternoonList = new ArrayList<>();
                for (int j = 0; j < ksTable.size(); j++) {
                    TblKs ks = ksTable.get(j);
                    if (ks.getKs_sj() != null) {
                        int kssj = ks.getKs_sj();
                        if (l > 2) {
                            if (ks.getKs_sjlater() != null) {
                                kssj = ks.getKs_sjlater();
                            }
                        }
                        if (kssj == l + count || kssj == l * 2) {
                            if (kssj % 2 == 1) {
                                Morning morning = new Morning(ks.getKc_dm(), ks.getKc_mc());
                                morningList.add(morning);
                                date.setMorningList(morningList);
                            }
                            if (kssj % 2 == 0) {
                                Afternoon afternoon = new Afternoon(ks.getKc_dm(), ks.getKc_mc());
                                afternoonList.add(afternoon);
                                date.setAfternoonList(afternoonList);
                            }
                        }
                    }
                }
                if (count < l) {
                    count++;
                }
                dateList.add(date);
            }
            zytbl.setDate(dateList);
            zyTable.add(zytbl);
        }
        return zyTable;
    }

    @Override
    public void setf_gk() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select();
        List<TblKs> tblKs = bzymapper.selectList(qw);
        for (int i = 0; i < tblKs.size(); i++) {
            if (tblKs.get(i).getSf_gk() == null) {
                UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
                uw.eq("kc_dm", tblKs.get(i).getKc_dm())
                        .set("sf_gk", "否");
                bzymapper.update(null, uw);
            }
        }
    }

    @Override
    public String importgksj(GkSj gkSj) {
        gkSjMapper.update(gkSj,null);
        return "导入成功";
    }

    //获取日期
    public String getKsDate(Integer ksSjDm) {
        if (ksSjDm == 1) {
            return "4月13日(星期六)";
        }
        if (ksSjDm == 2) {
            return "4月14日(星期日)";
        }
        if (ksSjDm == 3) {
            return "10月26日(星期六)";
        }
        if (ksSjDm == 4) {
            return "10月27日(星期日)";
        } else return "";
    }


}
