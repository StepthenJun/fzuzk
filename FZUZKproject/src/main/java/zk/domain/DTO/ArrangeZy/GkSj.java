package zk.domain.DTO.ArrangeZy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gk_sjtest")
public class GkSj {
    private String kc_dm;
    private String kc_mc;
    private String ks_sj;
}
