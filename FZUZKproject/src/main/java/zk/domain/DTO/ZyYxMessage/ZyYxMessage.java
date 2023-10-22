package zk.domain.DTO.ZyYxMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("zkyx_message")
public class ZyYxMessage {
    private String zy_yx;
    private String zy_mc;
    private String byxf;
    private String xlcc;
    private String zysp;
    private String zstf;
    private String zytk;
    private String sftzzs;
    private String kkzt;
}
