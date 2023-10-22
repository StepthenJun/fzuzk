package zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zk.domain.Result;
import zk.service.ZyYxService;

@RestController
@RequestMapping("/FZUZK/zyyx")
public class ZyYxController {
    @Autowired
    private ZyYxService zyYxService;

    @DeleteMapping("/delete/{zy_dm}")
    public Result delete(@PathVariable String zy_dm){
        int deleted = zyYxService.Delete(zy_dm);
        return Result.success(deleted);
    }
}
