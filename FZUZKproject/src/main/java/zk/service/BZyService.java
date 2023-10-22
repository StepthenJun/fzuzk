package zk.service;

import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.VO.ArrangeKs.ZyTable;

import java.util.List;

/**
 * <p>
 * 专业表 服务类
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
public interface BZyService{
    List<TblKs> orderlist();
    int[] arrangeGk(List<TblKs> ksList);
    int[] arrangeHx(List<TblKs> ksList,List<TblKs> zyHx);

    List<TblKs> orderlistlater();
    int[] arrangeGkLater(List<TblKs> ksList);
    int[] arrangeHxLater(List<TblKs> gK,List<TblKs> zyHx);

    public List<ZyTable> getZyTable();
    void setf_gk();
}
