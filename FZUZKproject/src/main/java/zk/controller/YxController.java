package zk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.dao.YxMapper.ZyfromYxMapper;
import zk.dao.zymessage.YxMessageMapper;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.domain.Result;
import zk.domain.VO.ZyMessage.KAZMessge;
import zk.domain.VO.ZyMessage.YxZyMessageVo;
import zk.service.BZyService;
import zk.service.Yx2ZyService;
import zk.service.ZyMessageService;
import zk.service.ZyYxService;

import java.util.List;
@RestController
@RequestMapping("/FZUZK/yx")
@Api(tags = "专业主考学校的接口")
public class YxController {
    @Autowired
    private ZyMessageService zyMessageService;
    @Autowired
    private Yx2ZyService yx2ZyService;

    @ApiOperation("点击详情获取专业里的面板的数据")
    @GetMapping("/getboard")
    public Result<KAZMessge> getboard(@RequestParam(value = "zy_dm") String zy_dm){
        KAZMessge getallmessage = yx2ZyService.getallmessage(zy_dm);
        return Result.success(getallmessage);
    }

    @ApiOperation("专业院校类别")
    @PostMapping("/zyyxType")
    public Result zytype(){
        List<String> zyType = zyMessageService.getZyType();
        return Result.success(zyType);
    }

    @ApiOperation("根据主考学校显示专业")
    @PostMapping("/yxzy")
    public Result<YxZyMessageVo> getzyfromyx(String zy_yx){
        YxZyMessageVo getzyfromyx = zyMessageService.getzyfromyx(zy_yx);
        return Result.success(getzyfromyx);
    }
}
