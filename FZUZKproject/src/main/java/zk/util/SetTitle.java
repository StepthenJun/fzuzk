package zk.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SetTitle {
    public void exportTitle(XSSFWorkbook wb, XSSFSheet sheet, List<String> titleList, List<CellRangeAddress> cellRangeAddressList) {
        CellStyle style = createTitleCellStyle(wb);

        for (int i = 0; i < cellRangeAddressList.size(); i++) {
            sheet.addMergedRegion(cellRangeAddressList.get(i));
        }

        for (int i = 0; i < cellRangeAddressList.size(); i++) {
            CellRangeAddress cellRangeAddress = cellRangeAddressList.get(i);
            int firstRow = cellRangeAddress.getFirstRow();
            int firstColumn = cellRangeAddress.getFirstColumn();
            XSSFRow row = sheet.getRow(firstRow);
            if (row == null) {
                row = sheet.createRow(firstRow);
            }
            XSSFCell cell = row.createCell(firstColumn);
            String title = titleList.get(i); // 使用当前索引获取标题数据
            cell.setCellStyle(style);
            cell.setCellValue(title);
        }
    }



// 其他方法不变


    private static CellStyle createTitleCellStyle(XSSFWorkbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);//粗体显示style.setFont(font);//单元格样式cell1.setCellStyle(style);//给cell1这个单元格设置样式
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER); // 居中
        return style;
    }

    private static void setSheetTitle(XSSFSheet sheet, List<String> titleList, CellStyle style, int rownum) {
        XSSFRow row = sheet.createRow(rownum);
        for (int j = 0; j < titleList.size(); j++) {
            String title = titleList.get(j);
            XSSFCell cell = row.createCell(j);
            cell.setCellValue(title);
            cell.setCellStyle(style);
        }
/*        for (String title : ListUtils.emptyIfNull(titleList)) {
            XSSFCell cell = row.createCell(i.getAndIncrement());
            cell.setCellValue(title);
            cell.setCellStyle(style);
        }*/
    }

}
