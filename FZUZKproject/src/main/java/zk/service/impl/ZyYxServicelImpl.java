package zk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public List<ZyYxMessage> getzyyxmessage(ZyYxMessage zyYxMessage) {
        QueryWrapper<ZyYxMessage> qw = new QueryWrapper<>();
        Class cls = zyYxMessage.getClass();
        Field[] fields = cls.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            Field f = fields[i];
            f.setAccessible(true);
                try {
                    if (!f.get(zyYxMessage).toString().equals(""))
                    {String value = f.get(zyYxMessage).toString();String name = f.getName();
                        qw.eq(name,value);}
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
            }
        }
        List<ZyYxMessage> zyYxMessages = zyYxMapper.selectList(qw);



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
        return zyYxMessages;
    }

    @Override
    public int Delete(String zy_dm) {
        QueryWrapper<ZyYxMessage> qw = new QueryWrapper<>();
        qw.eq("zy_dm",zy_dm);
        int delete = zyYxMapper.delete(qw);
        return delete;
    }
}
