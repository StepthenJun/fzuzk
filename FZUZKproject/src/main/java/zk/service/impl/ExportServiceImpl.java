package zk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;
import zk.service.ExportService;
import zk.util.ExcelTool;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ExcelTool excelTool;

    public String exportallkc(HttpServletResponse resp, String excelName, List< ArrangeTableVO > arrangeTableVO){
        excelTool.excelOutOnBrowser(resp,excelName,arrangeTableVO);
        return "s";
    }
}
