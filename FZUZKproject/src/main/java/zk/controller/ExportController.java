package zk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zk.dao.TestTblKsMapper.TblKsMapper;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;
import zk.service.ArrangeKcService;
import zk.util.ExcelTool;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/FZUZK")
@Api("导出excel的接口")
public class ExportController {
    @Autowired
    private ExcelTool excelTool = new ExcelTool();
    @Autowired
    private ArrangeKcService arrangeKcService;
    @Autowired
    private TblKsMapper tblKsMapper;
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

    @ApiOperation("导出课上半年排考excel")
    @GetMapping("/halfexcel")
    public String ExportHalf(HttpServletResponse httpServletResponse){
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select().orderByAsc("ks_sj").orderByAsc(String.format("LPAD(%s, 5, '0')", "kc_dm")).orderByAsc("kc_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Set<String> uniqueKcMc = new HashSet<>();
        List<TblKs> filteredList = new ArrayList<>();

        for (TblKs entry : tblKs) {
            if (uniqueKcMc.add(entry.getKc_dm())) {
                // 如果成功添加到 Set 中，说明是第一次出现的 kc_mc 值
                filteredList.add(entry);
            }
        }
        excelTool.excelOutlastOnBrowser(httpServletResponse,"上半年课程",filteredList);
        return "s";
    }

    @ApiOperation("导出下半年排考excel")
    @GetMapping("/laterexcel")
    public String ExportLater(HttpServletResponse httpServletResponse){
        QueryWrapper<TblKs> qw = new QueryWrapper<>();
        qw.select().orderByAsc("ks_sjlater").orderByAsc(String.format("LPAD(%s, 5, '0')", "kc_dm")).orderByAsc("kc_mc");
        List<TblKs> tblKs = tblKsMapper.selectList(qw);
        Set<String> uniqueKcMc = new HashSet<>();
        List<TblKs> filteredList = new ArrayList<>();

        for (TblKs entry : tblKs) {
            if (uniqueKcMc.add(entry.getKc_dm())) {
                // 如果成功添加到 Set 中，说明是第一次出现的 kc_mc 值
                filteredList.add(entry);
            }
        }
        excelTool.excelOutlaterOnBrowser(httpServletResponse,"下半年课程",filteredList);
        return "s";
    }

}
