package zk.dao.DateMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zk.common.KsDate;
import zk.domain.VO.ArrangeKs.Date;

@Mapper
public interface DateMapper extends BaseMapper<KsDate> {
}
