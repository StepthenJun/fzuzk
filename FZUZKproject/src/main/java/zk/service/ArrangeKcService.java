package zk.service;

import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;

import java.util.List;

/**
 * <p>
 * 专业表 服务类
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
public interface ArrangeKcService {
    List<TblKs> orderlist();
    int[] arrangeGk(List<TblKs> ksList);
    int[] arrangeHx(List<TblKs> ksList,List<TblKs> zyHx);

    List<TblKs> orderlistlater();
    int[] arrangeGkLater(List<TblKs> ksList);
    int[] arrangeHxLater(List<TblKs> gK,List<TblKs> zyHx);

    public List<ArrangeTableVO> getZyTable();
   /* void setf_gk();*/

    String importgksj(List<GkSj> gkSj);

    String importegksj(List<String> kcdms,List<String> kcmcs,List<String> kssjs);
}
