package zk.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.DTO.YxMessage.ZyfromYx;
import zk.domain.Result;
import zk.domain.VO.YxMessage.ZyxqMessge;
import zk.domain.VO.YxMessage.YxZyMessageVo;
import zk.service.Yx2ZyService;
import zk.service.ZyMessageService;

import java.util.List;
@RestController
@RequestMapping("/FZUZK/yx")
@Api(tags = "专业主考学校界面的接口")
public class YxController {
    @Autowired
    private ZyMessageService zyMessageService;
    @Autowired
    private Yx2ZyService yx2ZyService;

    @ApiOperation("点击详情获取专业里的面板的数据")
    @GetMapping("/getboard")
    public Result<ZyxqMessge> getboard(@RequestParam(value = "zy_dm") String zy_dm){
        ZyxqMessge getallmessage = yx2ZyService.getallmessage(zy_dm);
        return Result.success(getallmessage);
    }

    @ApiOperation("专业院校树（左侧）")
    @PostMapping("/zyyxType")
    public Result zytype(){
        List<String> zyType = zyMessageService.getZyType();
        return Result.success(zyType);
    }

    @ApiOperation("根据主考学校获取专业列表")
    @PostMapping("/yxzy")
    public Result<YxZyMessageVo> getzyfromyx(String zy_yx){
        YxZyMessageVo getzyfromyx = zyMessageService.getzyfromyx(zy_yx);
        return Result.success(getzyfromyx);
    }

    @ApiOperation("开考专业备注（面板数据里修改）")
    @PostMapping("/getboard/updatezybz")
    public Result<String> updatekczybz(String zybz,String zy_dm){
        String updatekkzybz = zyMessageService.updatekkzybz(zybz, zy_dm);
        return Result.success(updatekkzybz);
    }

    @ApiOperation("根据条件查询专业")
    @PostMapping("/checkby")
    public Result checkby(ZyfromYx zyfromYx){
        List<ZyfromYx> checkby = yx2ZyService.checkby(zyfromYx);
        return Result.success(checkby);
    }
}
