package zk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import zk.dao.GkSjMapper;
import zk.dao.TblKsMapper;
import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.service.BZyService;
import zk.service.GknewService;

import java.util.*;

@SpringBootTest
@ComponentScan("zk/*")
class FzuzKprojectApplicationTests {

    @Autowired
    private BZyService bZyService;
    @Autowired
    private TblKsMapper tblKsMapper;
    @Autowired
    private GkSjMapper gkSjMapper;
    @Autowired
    private GknewService gknewService;
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
    public void testMap(){
        //国考时间应该重新编写
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select("zy_dm","kc_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Map<String,Integer> zyMap = new HashMap<>();
        for (TblKs tbl : tblKs){
            String zy_dm = tbl.getZy_dm();
            if (zyMap.containsKey(zy_dm)){
                zyMap.put(zy_dm,zyMap.get(zy_dm) + 1);
            }else {
                zyMap.put(zy_dm,1);
            }
        }

//        通过for循环对每个专业进行抽取
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(zyMap.entrySet());
        for (int i = 0; i < entryList.size(); i++) {
            Map.Entry<String,Integer> entry = entryList.get(i);
//            加强匹配（专业名称和专业代码双重匹配）
            String zy_dm = entry.getKey();
//            获取在循环相关专业的课程
            QueryWrapper<TblKs> qw1 = new QueryWrapper<>();
            qw1.eq("zy_dm",zy_dm).eq("sf_gk","#N/A");;
            List<TblKs> gK = tblKsMapper.selectList(qw1);
//            int[] ints = bZyService.arrangeGk(gK);
//            System.out.println(Arrays.toString(ints));
//            System.out.println();
            QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
            qw2.eq("zy_dm",zy_dm).eq("xjks_fs","专业核心").eq("sf_gk","否");
            List<TblKs> zyHx = tblKsMapper.selectList(qw2);
            int[] counts = bZyService.arrangeHx(gK,zyHx);

//            对list里的对象随机取出，再对其进行时间的赋值
            //创建一个单独专业的list
            List<TblKs> randomSelectionList = new ArrayList<>();
            Random random = new Random();
            QueryWrapper<TblKs> qw3 = new QueryWrapper<>();
            qw3.select()
                .eq("zy_dm",zy_dm)
                .and
                (wrapper -> wrapper.or(wrapper1 -> wrapper1.eq("xjks_fs","公共基础").eq("xjks_fs",null)))
                .and
                (wrapper -> wrapper.or(wrapper1 -> wrapper1.eq("sf_gk",null).eq("sf_gk","否")));
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

                        if (counts[k] <= min){
                            sj = k + 1;
                            counts[k]++;
                            break;
                        }
                    }
                    randomSelectionList.get(j).setKs_sj(sj);
                    uw.eq("kc_dm",randomSelectionList.get(j).getKc_dm())
                            .set("ks_sj",sj);
                    tblKsMapper.update(null,uw);
            }
        }
    }

    @Test
    public void testProject(){
/*        bZyService.orderlist();*/
        bZyService.orderlistlater();
    }


    @Test
    public void insertsj2new(){
        List<GkSj> gkSjs = gkSjMapper.selectList(null);
        gknewService.arrangeGk(gkSjs);
    }
}
