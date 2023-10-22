package zk.domain.DTO.ZyMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yx_message")
public class YxMessage {
    private String zy_yx;
    private String zy_dm;
    private String zy_bz;
    private String tzgg_yx;
    private String xjsd_yx;
    private String xjexl_yx;
    private String kkzy_bz;
    private String tzxs_yx;
}
