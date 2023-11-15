package zk.dao.TestTblKsMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Scope;
import zk.domain.DTO.ArrangeZy.TblKs;
/*
*
 * <p>
 * 专业表 Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */

@Mapper
@Scope("prototype")
public interface TblKsMapper extends BaseMapper<TblKs> {

}
