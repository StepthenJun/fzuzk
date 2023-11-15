package zk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import zk.dao.DateMapper.DateMapper;
import zk.dao.GkMapper.GkSjMapper;
import zk.dao.TestTblKsMapper.TblKsMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;
import zk.domain.VO.ArrangeKs.Date;
import zk.service.ArrangeKcService;
import zk.service.GknewService;
import zk.service.ZyMessageService;
import zk.util.KcSj;
import zk.util.Md5Util;

import java.util.*;

@SpringBootTest
@ComponentScan("zk/*")
class FzuzKprojectApplicationTests {

    @Autowired
    private ArrangeKcService arrangeKcService;
    @Autowired
    private TblKsMapper tblKsMapper;
    @Autowired
    private GkSjMapper gkSjMapper;
    @Autowired
    private GknewService gknewService;
    @Autowired
    private TblKsMapper bzymapper;
    @Autowired
    private DateMapper dateMapper;
//    测试平均分的算法
//    不依赖数据库
    @Test
    void testlist() {
        int[] counts = {0,0,0,0};
        TblKs tblKs1 = new TblKs();
        TblKs tblKs2 = new TblKs();
        TblKs tblKs3 = new TblKs();
        TblKs tblKs4 = new TblKs();
        TblKs tblKs5 = new TblKs();
        TblKs tblKs6 = new TblKs();
        TblKs tblKs7 = new TblKs();
        List<TblKs> tklist = new ArrayList<>();
        tklist.add(tblKs1);
        tklist.add(tblKs2);
        tklist.add(tblKs3);
        tklist.add(tblKs4);
        tklist.add(tblKs5);
        tklist.add(tblKs6);
        tklist.add(tblKs7);
        int min = 0;
        for (int i = 0; i < tklist.size(); i++) {
            int sj = 0;
            for (int j = 0; j < counts.length; j++) {

                for (int k = 0; k < counts.length; k++) {
                    if (counts[k] <= min);
                    min = counts[k];
                }

                if (counts[j] <= min){
                    sj = j + 1;
                    counts[j]++;
                    break;
                }
            }
            tklist.get(i).setKs_sj(sj);
        }
    }
//   获取每个专业的种类和对应的数量（成功）
    @Test
    public void testGetCountZy(){
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select("zy_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Map<String,Integer> countZyMap = new HashMap<>();
        for (TblKs tbl : tblKs){
            String zy_mc = tbl.getZy_mc();
            if (countZyMap.containsKey(zy_mc)){
                countZyMap.put(zy_mc,countZyMap.get(zy_mc) + 1);
            }else {
                countZyMap.put(zy_mc, 1);
            }
        }

        for (Map.Entry<String,Integer> entry : countZyMap.entrySet()){
            String zy_mc = entry.getKey();
            Integer count = entry.getValue();
            System.out.println("zy_mc: " + zy_mc + "," + "count: " + count);
        }
    }
//    对封装的map进行索引测试（成功）

    @Test
    public void testProject(){
          arrangeKcService.orderlist();
/*        arrangeKcService.orderlistlater();*/
    }


    @Test
    public void insertsj2new(){
        List<GkSj> gkSjs = gkSjMapper.selectList(null);
        gknewService.arrangeGk(gkSjs);
    }

    @Test
    public void insert2zyyxmessage(){
        arrangeKcService.orderlist();
        arrangeKcService.orderlistlater();
    }


    @Test
    public void testarrangegk(){
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.orderByAsc("zy_dm").orderByAsc("cc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        for (TblKs tblK : tblKs) {
            System.out.println(tblK.getZy_dm() + tblK.getCc());
        }
    }
    @Test
    public void testgk() {

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
            Integer number = entry.getValue();
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
            int[] counts = arrangeGk(gK);
            QueryWrapper<TblKs> qw3 = new QueryWrapper<>();
            qw3.select()
                    .eq("zy_dm", zy_dm)
                    .eq("bz", "省考").eq("ks_fs", "笔试")
                    .orderByAsc("kc_dm");
            List<TblKs> zyList = tblKsMapper.selectList(qw3);
            zyList.sort(Comparator.comparing(TblKs::getKc_dm));
            int[] ksSjArray = new int[4];
            int min = 0;
//           可以对相对应专业的课程进行排序了
            for (int j = 0; j < zyList.size(); j++) {

                for (int k = 0; k < counts.length; k++) {
                    min = Arrays.stream(counts).min().getAsInt();
                    if (counts[k] <= min) {
                        ksSjArray[k]++;
                        counts[k]++;
                        break;
                    }
                }
            }
//            1.单个时间段按照顺序排
            // 3.为专业课程分配时间
            int arrayIndex = 0;
            UpdateWrapper<TblKs> uw = new UpdateWrapper<>();
            for (int j = 0; j < zyList.size() && arrayIndex < ksSjArray.length;) {
                TblKs Ks = zyList.get(j);
                for (int k = 0; k < ksSjArray[arrayIndex]; k++) {
                    Ks.setKs_sj(arrayIndex + 1);
                    uw.eq("kc_dm",Ks.getKc_dm()).set("ks_sj",arrayIndex + 1);
                    bzymapper.update(null, uw);
                    if (++j >= zyList.size()) {
                        break;  // 避免索引越界
                    }
                    Ks = zyList.get(j);
                }
                arrayIndex++;
            }

            System.out.println(Arrays.toString(ksSjArray));
            System.out.println(zy_dm);
            zyList.forEach(t ->{
                System.out.println(t.getKc_dm() + ","+t.getKs_sj() );
            });
            System.out.println(Arrays.toString(counts));
            System.out.println("------------------");
        }

    }
    public int[] arrangeGk(List<TblKs> ksList) {
        QueryWrapper<GkSj> qw = new QueryWrapper<>();
        List<GkSj> gkSjList = gkSjMapper.selectList(qw);
        int[] counts = {0, 0, 0, 0};
//        将国考对应的时间（代码1，2，3..）添加到考试表里的时间，先把国考的确定了
        for (int i = 0; i < ksList.size(); i++) {
            String kc_dm = ksList.get(i).getKc_dm();
            for (int j = 0; j < gkSjList.size(); j++) {
                GkSj gk = gkSjList.get(j);
                if (gk.getKc_dm().compareTo(kc_dm) == 0) {
                    String ksSj = gk.getKs_sj();
                    if (ksSj.equals(KcSj.getkssj("1"))) {
                        ksList.get(i).setKs_sj(1);
                        break;
                    }
                    if (ksSj.equals(KcSj.getkssj("2"))) {
                        ksList.get(i).setKs_sj(2);
                        break;
                    }
                    if (ksSj.equals(KcSj.getkssj("3"))) {
                        ksList.get(i).setKs_sj(3);
                        break;
                    }
                    if (ksSj.equals(KcSj.getkssj("4"))) {
                        ksList.get(i).setKs_sj(4);
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

    @Test
    public void test(){
        System.out.println(Md5Util.encrypt("Fjzkadmin123456"));
    }
}
