package zk.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zk.dao.DateMapper.DateMapper;

import java.util.List;

@Component
@Data
@TableName("date")
public class KsDate {
    private String sj;
    private String sign;
}
