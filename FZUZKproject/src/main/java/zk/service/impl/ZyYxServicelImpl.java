package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zk.dao.ZyYxmessage.ZyYxMapper;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.service.ZyYxService;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class ZyYxServicelImpl implements ZyYxService {
    @Autowired
    private ZyYxMapper zyYxMapper;

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

        return zyYxMapper.selectList(queryWrapper);


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
    public int Delete(String zy_dm) {
        QueryWrapper<ZyYxMessage> qw = new QueryWrapper<>();
        qw.eq("zy_dm",zy_dm);
        int delete = zyYxMapper.delete(qw);
        return delete;
    }

    
}
