package zk.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zk.common.KsDate;
import zk.dao.DateMapper.DateMapper;

import java.util.List;

@Component
public class KcSj {
    @Autowired
    public static DateMapper dateMapper;
    @Autowired
    public KcSj(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }
    public static String getkssj(String sign){
        String kssj = "";
        List<KsDate> ksDates = dateMapper.selectList(null);
        for (int i = 0; i < ksDates.size(); i++) {
            if (sign.equals(ksDates.get(i).getSign())){
                kssj =  ksDates.get(i).getSj();
            }
        }
        return kssj;
    }
}
