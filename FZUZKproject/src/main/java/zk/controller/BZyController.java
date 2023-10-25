package zk.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zk.domain.DTO.ArrangeZy.GkSj;
import zk.domain.Result;
import zk.domain.DTO.ZyMessage.KcMessage;
import zk.domain.DTO.ZyMessage.YxMessage;
import zk.domain.DTO.ZyMessage.ZyMessage;
import zk.domain.DTO.ZyYxMessage.ZyYxMessage;
import zk.domain.VO.ArrangeKs.ZyTable;
import zk.domain.VO.ZyMessage.KAZMessge;
import zk.service.BZyService;
import zk.service.ZyMessageService;
import zk.service.ZyYxService;


/**
 * <p>
 * 专业表 前端控制器
 * </p>
 *
 * @author yan
 * @since 2023-10-09
 */
@RestController
@RequestMapping("/FZUZK/zykc")
@Api(tags = "编排表的接口")
public class BZyController {
    @Autowired
    private BZyService bZyService;
    @Autowired
    private ZyMessageService zyMessageService;
    @Autowired
    private ZyYxService zyYxService;

    @ApiOperation("重新编排")
    @PostMapping("/orderlist")
    public Result returnList(){
        bZyService.orderlist();
        bZyService.orderlistlater();
        return Result.success();
    }
    @ApiOperation("获取全部的专业信息")
    @GetMapping("/getkstable")
    public Result<List<ZyTable>> returnKstable(){
        List<ZyTable> zyTable = bZyService.getZyTable();
        return Result.success(zyTable);
    }

    @ApiOperation("专业信息进行分页查询")
    @GetMapping("/paginate-list")
    public Result<List<ZyTable>> paginateList(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) throws JsonProcessingException {
        List<ZyTable> zyTable = bZyService.getZyTable();
        int startIndex = (page - 1) * pageSize;
        // 根据起始索引和每页大小，获取分页后的数据
        List<ZyTable> paginatedList = zyTable.subList(startIndex, Math.min(startIndex + pageSize, zyTable.size()));
        return Result.success(paginatedList);
    }


    @ApiOperation("获取专业信息")
    @PostMapping("/getzyyxmessage")
    public Result getzyyxmessage(ZyYxMessage zyYxMessage){
        List<ZyYxMessage> getzyyxmessage = zyYxService.getzyyxmessage(zyYxMessage);
        return Result.success(getzyyxmessage);
    }

    @ApiOperation("导入国考安排时间的excel")
    @PostMapping("/importgksj")
    public Result importgkmessage(GkSj gkSj){
        String importgksj = bZyService.importgksj(gkSj);
        return Result.success();
    }

}

