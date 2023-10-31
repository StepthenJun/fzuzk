package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import zk.common.KsDate;
import zk.dao.DateMapper.DateMapper;
import zk.dao.GkMapper.GkSjMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.dao.TestTblKsMapper.TblKsMapper;
import zk.domain.VO.ArrangeKs.*;
import zk.domain.VO.ArrangeKs.Date;
import zk.service.ArrangeKcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zk.util.KcSj;

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
public class ArrangeKcServiceImpl extends ServiceImpl<TblKsMapper, TblKs> implements ArrangeKcService {
    @Autowired
    private TblKsMapper bzymapper;
    @Autowired
    private GkSjMapper gkSjMapper;
    @Autowired
    private TblKsMapper tblKsMapper;
    @Autowired
    private DateMapper dateMapper;

    //excel还是json?
    @Override
    public List<TblKs> orderlist() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select();
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
            qw1.eq("zy_dm", zy_dm)
                    .and(qww -> qww
                            .eq("bz", "国考")
                            .or()
                            .eq("bz", "特殊")
                    )
                    .eq("ks_fs", "笔试");
            List<TblKs> gK = tblKsMapper.selectList(qw1);
            QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
            qw2.eq("zy_dm", zy_dm).eq("xjks_fs", "专业核心").eq("bz", "省考").eq("ks_fs", "笔试");
            List<TblKs> zyHx = tblKsMapper.selectList(qw2);
            int[] counts = arrangeHx(gK, zyHx);
/*            System.out.println("---------");
            System.out.println(zy_dm);
            System.out.println("安排前的计数器" + Arrays.toString(counts));*/
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
/*            System.out.println("安排完的计数器" + Arrays.toString(counts));*/
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
        qw.select();
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
            qw1.eq("zy_dm", zy_dm).and(qww -> qww
                    .eq("bz", "国考")
                    .or()
                    .eq("bz", "特殊")
            ).eq("ks_fs", "笔试");
            List<TblKs> gK = tblKsMapper.selectList(qw1);
            QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
            qw2.eq("xjks_fs", "专业核心").eq("bz", "省考").eq("zy_dm", zy_dm).eq("ks_fs", "笔试");
            List<TblKs> zyHx = tblKsMapper.selectList(qw2);
            int[] counts = arrangeHxLater(gK, zyHx);
/*            System.out.println("---------");
            System.out.println(zy_dm);
            System.out.println("安排前的计数器" + Arrays.toString(counts));*/
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
/*            System.out.println("安排完的计数器" + Arrays.toString(counts));*/
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
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("10月26日下午14:30-17:00")) {
                        ksList.get(i).setKs_sjlater(6);
                        uw.set("ks_sjlater", 6);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("10月27日上午9:00-11:30")) {
                        ksList.get(i).setKs_sjlater(7);
                        uw.set("ks_sjlater", 7);
                        bzymapper.update(null, uw);
                        break;
                    }
                    if (ksSj.equals("10月27日下午14:30-17:00")) {
                        ksList.get(i).setKs_sjlater(8);
                        uw.set("ks_sjlater", 8);
                        bzymapper.update(null, uw);
                        break;
                    }
                }
            }
        }
//        这段是将国考已经占有的时间塞进计数器里
        for (int i = 0; i < ksList.size(); i++) {
            if (ksList.get(i).getKs_sjlater() != null) {
                int add = ksList.get(i).getKs_sjlater() - 1;
                for (int j = 0; j < counts.length; j++) {
                    if (add == j + 4) {
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
/*    @Override
    public List<ArrangeTableVO> getZyTable() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select();
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        List<ArrangeTableVO> arrangeTableVO = new ArrayList<>();
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

            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.select().eq("zy_dm", zy_dm);
            List<TblKs> ksTable = bzymapper.selectList(qw1);

            ArrangeTableVO zytbl = new ArrangeTableVO();
            zytbl.setZy_dm(zy_dm);
            zytbl.setZy_mc(ksTable.get(0).getZy_mc());
            zytbl.setZy_yx(ksTable.get(0).getZy_yx());
            zytbl.setCc(ksTable.get(0).getCc());
            zytbl.setSftzzs(ksTable.get(0).getSftzzs());
            zytbl.setWtkk(ksTable.get(0).getWtkk());
            List<Date> dateList = new ArrayList<>();

            for (int day = 1; day <= 4; day++) {
                Date date = new Date();
                date.setSj(getKsDate(day));
                List<Morning> morningList = new ArrayList<>();
                List<Afternoon> afternoonList = new ArrayList<>();
                for (int j = 0; j < ksTable.size(); j++) {
                    TblKs tbl = ksTable.get(j);
                    Integer ksSj = tbl.getKs_sj();
                    Integer ksSjlater = tbl.getKs_sjlater();
                    if (ksSj == null || ksSjlater == null){
                        if (ksSj != null){
                        if (ksSj == 2 * day || ksSj == 2 * day - 1){
                            if (ksSj % 2 == 1){
                                Morning morning = new Morning(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                morningList.add(morning);
                                date.setMorningList(morningList);
                            }  if (ksSj % 2 == 0){
                                Afternoon afternoon = new Afternoon(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                afternoonList.add(afternoon);
                                date.setAfternoonList(afternoonList);
                            }
                        }
                        }if (ksSjlater != null){
                            if (ksSjlater == 2 * day || ksSjlater == 2 * day - 1){
                                if (ksSjlater % 2 == 1){
                                    Morning morning = new Morning(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    morningList.add(morning);
                                    date.setMorningList(morningList);
                                }  if (ksSjlater % 2 == 0){
                                    Afternoon afternoon = new Afternoon(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    afternoonList.add(afternoon);
                                    date.setAfternoonList(afternoonList);
                                }
                            }
                        }
                    }
                }
                dateList.add(date);
            }
            zytbl.setDate(dateList);
            arrangeTableVO.add(zytbl);
        }
        return arrangeTableVO;
    }*/

    @Override
    public List<ArrangeTableVO> getZyTable() {
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select();
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        List<ArrangeTableVO> arrangeTableVO = new ArrayList<>();
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

            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.select().eq("zy_dm", zy_dm);
            List<TblKs> ksTable = bzymapper.selectList(qw1);

            ArrangeTableVO zytbl = new ArrangeTableVO();
            zytbl.setZy_dm(zy_dm);
            zytbl.setZy_mc(ksTable.get(0).getZy_mc());
            zytbl.setZy_yx(ksTable.get(0).getZy_yx());
            zytbl.setCc(ksTable.get(0).getCc());
            zytbl.setSftzzs(ksTable.get(0).getSftzzs());
            zytbl.setWtkk(ksTable.get(0).getWtkk());
            List<Date> dateList = new ArrayList<>();

            for (int day = 1; day <= 4; day++) {
                Date date = new Date();
                date.setSj(getKsDate(day));
                List<Morning> morningList = new ArrayList<>();
                List<Afternoon> afternoonList = new ArrayList<>();
                for (int j = 0; j < ksTable.size(); j++) {
                    TblKs tbl = ksTable.get(j);
                    if (day <= 2){
                        if (tbl.getKs_sj() != null){
                            Integer ksSj = tbl.getKs_sj();
                            if (ksSj == 2 * day || ksSj == 2 * day - 1){
                                if (ksSj % 2 == 1){
                                    Morning morning = new Morning(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    morningList.add(morning);
                                    date.setMorningList(morningList);
                                }  if (ksSj % 2 == 0){
                                    Afternoon afternoon = new Afternoon(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    afternoonList.add(afternoon);
                                    date.setAfternoonList(afternoonList);
                                }
                            }
                        }
                    }
                    if (day > 2 ){
                        if (tbl.getKs_sjlater() != null){
                            Integer ksSjlater = tbl.getKs_sjlater();
                            if (ksSjlater == 2 * day || ksSjlater == 2 * day - 1){
                                if (ksSjlater % 2 == 1){
                                    Morning morning = new Morning(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    morningList.add(morning);
                                    date.setMorningList(morningList);
                                }  if (ksSjlater % 2 == 0){
                                    Afternoon afternoon = new Afternoon(tbl.getKc_dm(), tbl.getKc_mc(),tbl.getBz());
                                    afternoonList.add(afternoon);
                                    date.setAfternoonList(afternoonList);
                                }
                            }
                        }
                    }
                }
                dateList.add(date);
            }
            zytbl.setDate(dateList);
            arrangeTableVO.add(zytbl);
        }
        return arrangeTableVO;
    }
    @Override
    public String importgksj(List<GkSj> gkSjlist) {

        if (gkSjlist != null) {
            for (int i = 0; i < gkSjlist.size(); i++) {
                UpdateWrapper<GkSj> uw = new UpdateWrapper<>();
                GkSj gkSj = gkSjlist.get(i);
                if (gkSj.getKc_dm() != null && !gkSj.getKc_dm().isEmpty()){
                String kc_dm = gkSj.getKc_dm();
                String ksSj = gkSj.getKs_sj();
                String kcMc = gkSj.getKc_mc();
                uw.eq("kc_dm", kc_dm).set("ks_sj",ksSj);
                gkSjMapper.update(null, uw);
                }
                else if (gkSj.getKc_dm() == null && gkSj.getKc_dm().isEmpty()){
                    gkSjMapper.insert(gkSj);
                }
            }return "导入成功";
        }
        else return "没数据";
    }

    public String importegksj(List<String> kcdms,List<String> kcmcs,List<String> kssjs){
        List<GkSj> gkSjs = gkSjMapper.selectList(null);
        for (int i = 0; i < kcdms.size(); i++) {
            String kc_dm = kcdms.get(i);
            String kc_mc = kcmcs.get(i);
            String kssj = kssjs.get(i);
            for (int i1 = 0; i1 < gkSjs.size(); i1++) {
                GkSj gkSj = gkSjs.get(i1);
                if (gkSj.getKc_dm()  == kc_dm) {
                    UpdateWrapper<GkSj> uw = new UpdateWrapper<>();
                    uw.eq("kc_dm", kc_dm).set("kc_mc", kc_mc).set("ks_sj", kssj);
                    gkSjMapper.update(null, uw);
                }
                else {
                    UpdateWrapper<GkSj> uw = new UpdateWrapper<>();
                    GkSj gkSj1 = new GkSj(kc_dm,kc_mc,kssj);
                    gkSjMapper.insert(gkSj1);
                }
            }
        }
        return "s";
    }


    //获取日期
    public String getKsDate(Integer ksSjDm) {
        if (ksSjDm == 1) {
            return KcSj.getkssj("1").substring(0,KcSj.getkssj("1").indexOf('日') + 1) + "(星期六)";
        }
        if (ksSjDm == 2) {
            return KcSj.getkssj("3").substring(0,KcSj.getkssj("1").indexOf('日') + 1) + "(星期日)";
        }
        if (ksSjDm == 3) {
            return KcSj.getkssj("5").substring(0,KcSj.getkssj("1").indexOf('日') + 1) + "(星期六)";
        }
        if (ksSjDm == 4) {
            return KcSj.getkssj("7").substring(0,KcSj.getkssj("1").indexOf('日') + 1) + "(星期日)";
        } else return "";
    }

    public String setkssj(List<String> sign,List<String> kssj){
        List<KsDate> ksDates = dateMapper.selectList(null);
        for (int i = 0; i < ksDates.size(); i++) {
            KsDate ksDate = ksDates.get(i);
            for (int j = 0; j < sign.size(); j++) {
                if (sign.get(j).equals(ksDate.getSign())){
                    UpdateWrapper<KsDate> uw = new UpdateWrapper<>();
                    uw.eq("sign",sign.get(j)).set("sj",kssj.get(j));
                    dateMapper.update(null,uw);
                }
            }
        }
        return "s";
    }
}
