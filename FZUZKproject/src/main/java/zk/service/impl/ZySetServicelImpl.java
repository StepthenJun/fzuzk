package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zk.dao.TestTblKsMapper.TblKsMapper;
import zk.dao.YxMapper.YxtableMapper;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.DTO.ZyMessage.ZyYxMessage;
import zk.service.ZySetService;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ZySetServicelImpl implements ZySetService {
    @Autowired
    private YxtableMapper yxtableMapper;
    @Autowired
    private TblKsMapper tblKsMapper;

//    这部分是根据条件查询
    @Override
    public List<ZyYxMessage> getzyyxmessage(ZyYxMessage condition) {
        QueryWrapper<ZyYxMessage> queryWrapper = new QueryWrapper<>();
        Field[] fields = ZyYxMessage.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(condition);
                if (value != null && !StringUtils.isEmpty(value.toString())) {
                    queryWrapper.eq((field.getName()), value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return yxtableMapper.selectList(queryWrapper);


//        chatgpt给的答案
/*        Specification<YourEntity> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Class<? extends EntitySearchDTO> dtoClass = searchDTO.getClass();

            for (Field field : dtoClass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(searchDTO);

                    if (value != null) {
                        predicates.add(criteriaBuilder.equal(root.get(field.getName()), value));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return yourRepository.findAll(spec);
    }*/
    }

    @Override
    public String Delete(String zy_dm) {
        QueryWrapper<ZyYxMessage> qw1 = new QueryWrapper<>();
        qw1.eq("zy_dm",zy_dm);
        QueryWrapper<TblKs> qw2 = new QueryWrapper<>();
        qw2.eq("zy_dm",zy_dm);
        int deletezytable = yxtableMapper.delete(qw1);
        int deletezyxq = tblKsMapper.delete(qw2);
        return "删除成功";
    }
}
