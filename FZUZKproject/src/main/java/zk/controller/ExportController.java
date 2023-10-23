package zk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.domain.VO.ArrangeKs.ZyTable;
import zk.service.BZyService;
import zk.util.ExcelTool;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/FZUZK")
public class ExportController {
    @Autowired
    private ExcelTool excelTool = new ExcelTool();
    @Autowired
    private BZyService bZyService;
// http://localhost:8080/FZUZK/excel
    @GetMapping("/excel")
    public String testExport(HttpServletResponse httpServletResponse ){
        ArrayList<ArrayList> row= new ArrayList<>();
        List<ZyTable> zyTables = bZyService.getZyTable();
        row.add((ArrayList) zyTables);
        String s = excelTool.excelOutOnBrowser(httpServletResponse, "排考信息", row,zyTables);
        return s;
    }
}
