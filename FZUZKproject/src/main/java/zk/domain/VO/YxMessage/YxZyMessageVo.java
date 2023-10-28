package zk.domain.VO.YxMessage;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zk.domain.DTO.YxMessage.ZyfromYx;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("专业主考学校根据学校展示专业列表的显示类")
public class YxZyMessageVo {
    private String zy_yx;
    private List<ZyfromYx> zyMessageList;
}
