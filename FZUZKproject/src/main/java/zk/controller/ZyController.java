package zk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.DTO.ZyMessage.ZyYxMessage;
import zk.domain.Result;
import zk.domain.VO.ZyYxMessage.ZyxqVO;
import zk.service.ZyMessageService;
import zk.service.ZySetService;
import zk.service.ZyxqService;

import java.util.List;

@RestController
@RequestMapping("/FZUZK/zyyx")
@Api(tags = "专业设置界面的接口")

public class ZyController {
    @Autowired
    private ZySetService zySetService;

    @Autowired
    private ZyMessageService zyMessageService;

    @Autowired
    private ZyxqService zyxqService;


    @ApiOperation("删除专业")
    @DeleteMapping("/delete")
    public Result<String> delete(String zy_dm){
        String deleted = zySetService.Delete(zy_dm);
        return Result.success(deleted);
    }

    @ApiOperation("修改专业（修改哪些属性呢）")
    @PostMapping("/update")
    public Result<String> update(String zy_dm,String zy_mc,String zy_yx){
        String s = zyMessageService.updateZyMessage(zy_dm, zy_mc, zy_yx);
        return Result.success(s);
    }
    @ApiOperation("显示所有专业")
    @GetMapping("/check")
    public Result<List<ZyYxMessage>> check(){
        List<ZyYxMessage> zyYxMessages = zyMessageService.checkZymessage();
        return Result.success(zyYxMessages);
    }

    @ApiOperation("修改国考课程时间")
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
    public Result<String> updatezyxq(@RequestBody ZyxqVO zyxqVO){
        String s = zyxqService.updateZyxq(zyxqVO);
        return Result.success(s);
    }

    @ApiOperation("根据条件查询专业")
    @PostMapping("/checkzyby")
    public Result<List<ZyYxMessage>> checkbycondition(@RequestBody ZyYxMessage zyYxMessage){
        List<ZyYxMessage> getzyyxmessage = zySetService.getzyyxmessage(zyYxMessage);
        return Result.success(getzyyxmessage);
    }

    @ApiOperation("新增专业(存疑，加专业就要加课程)")
    @PostMapping("/insertzy")
    public Result insertzy(){
        return Result.success();
    }
}
