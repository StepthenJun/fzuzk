package zk.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import zk.domain.DTO.ZyMessage.ZyYxMessage;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;
import zk.service.ArrangeKcService;
import zk.service.ZyMessageService;
import zk.service.ZySetService;


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
public class BpzyController {
    @Autowired
    private ArrangeKcService arrangeKcService;
    @Autowired
    private ZyMessageService zyMessageService;
    @Autowired
    private ZySetService zySetService;

    @ApiOperation("重新编排")
    @PostMapping("/orderlist")
    public Result returnList(){
        arrangeKcService.orderlist();
        arrangeKcService.orderlistlater();
        return Result.success();
    }
    @ApiOperation("获取全部的专业信息")
    @GetMapping("/getkstable")
    public Result<List<ArrangeTableVO>> returnKstable(){
        List<ArrangeTableVO> arrangeTableVO = arrangeKcService.getZyTable();
        return Result.success(arrangeTableVO);
    }

    @ApiOperation("专业信息进行分页查询")
    @GetMapping("/paginate-list")
    public Result<List<ArrangeTableVO>> paginateList(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) throws JsonProcessingException {
        List<ArrangeTableVO> arrangeTableVO = arrangeKcService.getZyTable();
        int startIndex = (page - 1) * pageSize;
        // 根据起始索引和每页大小，获取分页后的数据
        List<ArrangeTableVO> paginatedList = arrangeTableVO.subList(startIndex, Math.min(startIndex + pageSize, arrangeTableVO.size()));
        return Result.success(paginatedList);
    }


    @ApiOperation("获取专业信息")
    @PostMapping(value = "/getzyyxmessage")
    public Result getzyyxmessage(ZyYxMessage zyYxMessage){
        List<ZyYxMessage> getzyyxmessage = zySetService.getzyyxmessage(zyYxMessage);
        return Result.success(getzyyxmessage);
    }

    /*@ApiOperation("导入国考安排时间的excel")
    @PostMapping(value = "/importgksj")
    public Result importgkmessage(@RequestBody List<GkSj> gkSj){
        String importgksj = arrangeKcService.importgksj(gkSj);
        return Result.success(importgksj);
    }*/
    @ApiOperation("导入国考安排时间的excel")
    @PostMapping(value = "/importgksj")
    public Result importgkmessage(List<GkSj> gkSj){
        String importgksj = arrangeKcService.importgksj(gkSj);
        return Result.success(importgksj);
    }
    @ApiOperation("导入国考安排时间的excel")
    @PostMapping(value = "/importgksje")
    public Result importgkemessage(List<String> kcdms, List<String> kcmcs, List<String> kssjs){

        return Result.success();
    }

    @ApiOperation("设置考试时间")
    @PostMapping("/setkssj")
    public Result setkssj(List<String> sign,List<String> kssj){
        String setkssj = arrangeKcService.setkssj(sign, kssj);
        return Result.success(setkssj);
    }
}

