package zk.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.Result;
import zk.service.ZyMessageService;
import zk.service.ZyYxService;

import java.util.List;

@RestController
@RequestMapping("/FZUZK/zyyx")

public class ZyYxController {
    @Autowired
    private ZyYxService zyYxService;

    @Autowired
    private ZyMessageService zyMessageService;

    @DeleteMapping("/delete/{zy_dm}")
    public Result delete(@PathVariable String zy_dm){
        int deleted = zyYxService.Delete(zy_dm);
        return Result.success(deleted);
    }

    @ApiOperation("修改专业的")
    @PostMapping("/update")
    public Result update(String zy_dm,String zy_mc,String zy_yx){
        String s = zyMessageService.updateZyMessage(zy_dm, zy_mc, zy_yx);
        return Result.success("成功！！！");
    }
    @ApiOperation("查看专业的")
    @GetMapping("/check")
    public Result<List<ZyYxMessage>> check(){
        List<ZyYxMessage> zyYxMessages = zyMessageService.checkZymessage();
        return Result.success(zyYxMessages);
    }
}
