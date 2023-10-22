package zk.dao.zymessage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import zk.domain.DTO.ZyMessage.ZyMessage;

@Mapper
public interface ZyMessageMapper extends BaseMapper<ZyMessage> {
}
