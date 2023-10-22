package zk.domain.DTO.ZyMessage;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("kc_message")
public class KcMessage {
    private Integer id;
    private String kc_type;
    private String kc_dm;
    private Integer xf;
    private String mxsh;
    private String tzgg;
    private String xjsd;
    private String jjhkc_dm;
    private String jjhkc_mc;
    private String bz;
    private String jcbb;
}
