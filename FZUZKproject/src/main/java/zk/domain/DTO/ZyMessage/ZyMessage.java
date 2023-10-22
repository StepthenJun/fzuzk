package zk.domain.DTO.ZyMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("zy_messagetitle")
public class ZyMessage {
    private String zy_mc;
    private String zy_dm;
    private String cc;
    private String zy_yx;
    private String bk_tj;
}
