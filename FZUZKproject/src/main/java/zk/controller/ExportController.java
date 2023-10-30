package zk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;
import zk.service.ArrangeKcService;
import zk.util.ExcelTool;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/FZUZK")
@Api("导出excel的接口")
public class ExportController {
    @Autowired
    private ExcelTool excelTool = new ExcelTool();
    @Autowired
    private ArrangeKcService arrangeKcService;
// http://localhost:8080/FZUZK/excel
    @ApiOperation("导出excel")
    @GetMapping("/excel")
    public String Export(HttpServletResponse httpServletResponse ){
        ArrayList<ArrayList> row= new ArrayList<>();
        List<ArrangeTableVO> arrangeTableVOS = arrangeKcService.getZyTable();
        row.add((ArrayList) arrangeTableVOS);
        String s = excelTool.excelOutOnBrowser(httpServletResponse, "排考信息", arrangeTableVOS);
        return s;
    }

    @ApiOperation("导出课程需求的排考excel")
    @GetMapping("/halfexcel")
    public String ExportHalf(HttpServletResponse httpServletResponse){
        List<ArrangeTableVO> arrangeTableVOS = arrangeKcService.getZyTable();
        excelTool.excelOutkcOnBrowser(httpServletResponse,"上半年",arrangeTableVOS);
        return "s";
    }

    @ApiOperation("导出下半年排考excel")
    @GetMapping("/laterexcel")
    public String ExportLater(HttpServletResponse httpServletResponse){

        return "s";
    }
}
