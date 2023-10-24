package zk.domain.DTO.ArrangeZy;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_kstest")
public class TblKs {
    private String zy_dm;
    private String zy_mc;
    private String zy_yx;
    private Integer ks_sj;
    private String kc_mc;
    private String kc_dm;
    private String sf_gk;
    private String xjks_fs;
    private Integer ks_sjlater;
    private String ks_fs;
    private String bz;
}
