package zk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.Result;
import zk.domain.VO.ZyYxMessage.ZyxqVO;
import zk.service.ZyMessageService;
import zk.service.ZyYxService;
import zk.service.ZyxqService;

import java.util.List;

@RestController
@RequestMapping("/FZUZK/zyyx")
@Api(tags = "看专业相关的接口")
public class ZyYxController {
    @Autowired
    private ZyYxService zyYxService;

    @Autowired
    private ZyMessageService zyMessageService;

    @Autowired
    private ZyxqService zyxqService;

    @DeleteMapping("/delete")
    public Result delete(String zy_dm){
        int deleted = zyYxService.Delete(zy_dm);
        return Result.success(deleted);
    }

    @ApiOperation("修改专业的")
    @PostMapping("/update")
    public Result<String> update(String zy_dm,String zy_mc,String zy_yx){
        String s = zyMessageService.updateZyMessage(zy_dm, zy_mc, zy_yx);
        return Result.success("成功！！！");
    }
    @ApiOperation("查看专业的")
    @GetMapping("/check")
    public Result<List<ZyYxMessage>> check(){
        List<ZyYxMessage> zyYxMessages = zyMessageService.checkZymessage();
        return Result.success(zyYxMessages);
    }

    @ApiOperation("修改国考课程")
    @PostMapping("/updategkkc")
    public Result<Integer> updategkkc(String kc_dm, Integer sj){
        int updategkkc = zyMessageService.updategkkc(kc_dm, sj);
        return Result.success(updategkkc);
    }

    @ApiOperation("查看专业详情")
    @GetMapping("/checkzyxq/{zy_dm}")
    public Result<ZyxqVO> checkzyxq(@PathVariable String zy_dm){
        ZyxqVO zyxqVO = zyxqService.checkZyxq(zy_dm);
        return Result.success(zyxqVO);
    }

    @ApiOperation("更新专业详情")
    @PostMapping("/updatezyxq")
    public Result<String> updatezyxq(ZyxqVO zyxqVO){
        String s = zyxqService.updateZyxq(zyxqVO);
        return Result.success(s);
    }
}
