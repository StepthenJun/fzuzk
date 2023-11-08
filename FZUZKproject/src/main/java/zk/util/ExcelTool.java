package zk.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import zk.domain.DTO.ArrangeZy.TblKs;
import zk.domain.VO.ArrangeKs.Afternoon;
import zk.domain.VO.ArrangeKs.Date;
import zk.domain.VO.ArrangeKs.Morning;
import zk.domain.VO.ArrangeKs.ArrangeTableVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * author:赵国庆
 * date:2023/1/14
 * name:Excel导入/导出工具类
 */
@Component
public class ExcelTool {

    @Resource
    private SetTitle setTitle;
    /**
     * message:Excel导出功能
     * @param excelName Excel文件名
     * @param excelOutPath Excel文件保存路径
     * @param rowList Excel的内容
     * @return
     */
    public String excelOut(String excelName, String excelOutPath, ArrayList<ArrayList> rowList) throws IOException {

        String status = "0";    // 设置导出方法执行结果，0失败，1成功，2成功但文件名已存在
        String message = "";    // 设置提示信息
        JSONObject obj = new JSONObject();      // 存放执行信息status和message

        // 创建工作簿类
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名，表名为 传入的表名参数，请注意工作表名不是Excel文件名
        XSSFSheet sheet = workbook.createSheet(excelName);
        // 工作表中的行
        XSSFRow row = null;

        // rowList为预导出的数据集，结构为ArrayList套ArraList，外层为行，里层为列，如下图所示
        // [                                        excel内容
        //      [第一列, 第二列, 第三列 ...],        excel第一行
        //      [第一列, 第二列, ...],            excel第二行
        //      [第一列, ...],                 excel第三行
        //      ...                         以此类推
        // ]
        int rowNum = 0;     // 行下标

        // 创建excel行
        for (ArrayList<String> colList: rowList) {
            row = sheet.createRow(rowNum);
            int colNum = 0;     // 列下标

            // 对每行逐列插入值
            for (String colValue: colList) {
                row.createCell(colNum).setCellValue(colValue);
                colNum++;
            }

            rowNum++;
        }

        // 设置Excel文件路径，例如：E:\ + 学生表 + 20230114（这是我自己写的时间获取方法，别人用不了，删了就行） + .xlsx
        String excelFileName = excelOutPath + excelName  + ".xlsx";
        File outExcleFile = new File(excelFileName);
        int fileVersion = 0;

        // 判断路径下是否存在同名文件，如果不进行判断，会覆盖同名文件内容，造成同名信息丢失
        // 所以，若存在同名文件，给文件名加(1)、(2)这样的标识
        while (outExcleFile.exists()) {
            fileVersion++;
            excelFileName = excelOutPath + excelName +"(" + fileVersion + ").xlsx";
            outExcleFile = new File(excelFileName);
        }

        try {
            // 创建指向该路径的输出流
            FileOutputStream stream = new FileOutputStream(outExcleFile);

            // 将数据导出到Excel表格
            workbook.write(stream);

            // 关闭输出流
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("excel导出失败。");
            obj.put("status", status);
            obj.put("message", "excel导出失败。");

            return obj.toJSONString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileVersion == 0) {
            System.out.println("导出成功，保存路径为：" + excelFileName);
            obj.put("status", "1");
            obj.put("message", "导出成功，保存路径为：" + excelFileName);

            return obj.toJSONString();
        } else {
            System.out.println("导出成功，保存路径为：" + excelFileName);
            obj.put("status", "2");
            obj.put("message", "导出成功，保存路径为：" + excelFileName);

            return obj.toJSONString();
        }
    }

	/**
     * message:Excel导出功能，浏览器下载
     * @param excelName Excel文件名
     * @return
     */
    public String excelOutOnBrowser(HttpServletResponse resp, String excelName, List<ArrangeTableVO> arrangeTableVO) {

        String status = "0";    // 设置导出方法执行结果，0失败，1成功，2成功但文件名已存在
        String message = "";    // 设置提示信息
        JSONObject obj = new JSONObject();      // 存放执行信息status和message

        // 创建工作簿类
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名，表名为 传入的表名参数，请注意工作表名不是Excel文件名
        XSSFSheet sheet = workbook.createSheet(excelName);
        // 工作表中的行
        XSSFRow row = null;
        // 设置Excel文件名，例如：学生表 + 20230114（这是我自己写的时间获取方法，别人用不了，删了就行） + .xlsx
        String excelFileName = excelName + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth()+ ".xlsx";
        // 设置表头
        List<String> titleList = setTableTitle();
        // 设置表头合并
        List<CellRangeAddress> cellRangeAddressList = addMergeOrder();

        setTitle.exportTitle(workbook,sheet,titleList,cellRangeAddressList);



        // rowList为预导出的数据集，结构为ArrayList套ArraList，外层为行，里层为列，如下图所示
        // [                                        excel内容
        //      [第一列, 第二列, 第三列 ...],        excel第一行
        //      [第一列, 第二列, ...],            excel第二行
        //      [第一列, ...],                 excel第三行
        //      ...                         以此类推
        // ]
        int rowNum = 3;     // 行下标

        // 创建excel行
        /*for (ArrayList<String> colList: rowList) {
            row = sheet.createRow(rowNum);
            int colNum = 1;     // 列下标

            // 对每行逐列插入值
            for (String colValue: colList) {
                row.createCell(colNum).setCellValue(colValue);
                colNum++;
            }

            rowNum++;
        }*/
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        List<CellRangeAddress> cellRangeAddressList1 = new ArrayList<>();
        for (int i = 0; i < arrangeTableVO.size(); i++) {
            ArrangeTableVO table = arrangeTableVO.get(i);
            String zydm_mc = table.getZy_dm() +table.getZy_mc();
            String zyYx = table.getZy_yx();
            String cc = table.getCc();
            row = sheet.createRow(rowNum);
            int colNum = 0;
            for (int k = 0; k < 20; k = k + 2) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum,rowNum,colNum + k,colNum + k + 1);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddressList1.add(cellRangeAddress);
                sheet.setColumnWidth(colNum + k, 20 * 256);
            }
            row.setHeightInPoints(100);
            XSSFCell cellzy = row.createCell(colNum);
            cellzy.setCellValue(zydm_mc+ "("+ cc +")");
            XSSFCell cellzyyx = row.createCell(colNum + 2);
            cellzyyx.setCellValue(zyYx);

            List<Date> date = table.getDate();
            int colNumtemp = colNum + 4;
            for (int l = 0; l < date.size(); l++) {
                List<Morning> morningList = date.get(l).getMorningList();
                List<Afternoon> afternoonList = date.get(l).getAfternoonList();
                String morning = "";
                String afternoon = "";
                if (morningList != null){
                    for (int a = 0; a < morningList.size(); a++) {
                        String bz = morningList.get(a).getBz();
                        if (bz.equals("国考")){
                            morning += morningList.get(a).getKc_dm() + morningList.get(a).getKc_mc() + "\n";
                        }
                        else
                        morning += morningList.get(a).getKc_dm() + morningList.get(a).getKc_mc() + "△"+ "\n";
                    }
                }
                if (afternoonList != null){
                    for (int b = 0; b < afternoonList.size(); b++) {
                        String bz = afternoonList.get(b).getBz();
                        if (bz.equals("国考")){
                            afternoon += afternoonList.get(b).getKc_dm() + afternoonList.get(b).getKc_mc() + "\n";
                        }
                        else
                        afternoon += afternoonList.get(b).getKc_dm() + afternoonList.get(b).getKc_mc() + "△"+ "\n";
                    }
                }
                    XSSFCell cellkcmorning = row.createCell(colNumtemp);
                    colNumtemp += 2;
                    cellkcmorning.setCellValue(morning);
//                    cellkcmorning.setCellStyle(style);
                    XSSFCell cellafternoon = row.createCell(colNumtemp);
                    colNumtemp += 2;
                    cellafternoon.setCellValue(afternoon);
//                    cellafternoon.setCellStyle(style);

            }
            rowNum++;
            for (CellRangeAddress cellRangeAddress : cellRangeAddressList) {
                int firstRow = cellRangeAddress.getFirstRow();
                int lastRow = cellRangeAddress.getLastRow();
                int firstCol = cellRangeAddress.getFirstColumn();
                int lastCol = cellRangeAddress.getLastColumn();

                for (int r = firstRow; r <= lastRow; r++) {
                    Row currentRow = sheet.getRow(r);
                    if (currentRow == null) {
                        currentRow = sheet.createRow(r);
                    }
                    for (int c = firstCol; c <= lastCol; c++) {
                        Cell cell = currentRow.getCell(c);
                        if (cell == null) {
                            cell = currentRow.createCell(c);
                        }
                        cell.setCellStyle(style);
                    }
                }
            }
            for (CellRangeAddress cellRangeAddress : cellRangeAddressList1) {
                int firstRow = cellRangeAddress.getFirstRow();
                int lastRow = cellRangeAddress.getLastRow();
                int firstCol = cellRangeAddress.getFirstColumn();
                int lastCol = cellRangeAddress.getLastColumn();

                for (int r = firstRow; r <= lastRow; r++) {
                    Row currentRow = sheet.getRow(r);
                    if (currentRow == null) {
                        currentRow = sheet.createRow(r);
                    }
                    for (int c = firstCol; c <= lastCol; c++) {
                        Cell cell = currentRow.getCell(c);
                        if (cell == null) {
                            cell = currentRow.createCell(c);
                        }
                        cell.setCellStyle(style);
                    }
                }
            }
        }




        try {
            // 设置响应格式，让浏览器知道是下载操作
            resp.setContentType("applicaton/x-mdownload");
            // 设置下载后的文件名
            resp.setHeader("Content-Disposition", "atachment;filename=" + new String(excelFileName.getBytes("utf-8"),"ISO8859-1"));
            // 设置响应编码
            resp.setContentType("text/html;charcet=UTF-8");
            // 建立输出流的连接
            OutputStream outputStream = resp.getOutputStream();

            // 将数据导出到Excel表格
            workbook.write(outputStream);

            // 关闭输出流
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("excel导出失败。");
            obj.put("status", status);
            obj.put("message", "excel导出失败。");

            return obj.toJSONString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        status = "1";
        obj.put("status", status);
        obj.put("message", "excel导出成功。");

        return obj.toJSONString();
    }


    private List<String> setTableTitle(){
/*
        List<String> titleList = Lists.newArrayList("开考专业","4月13日（星期六）,"4月14日（星期日）","10月26日（星期六）","10月27日（星期日）","专业代码名称","面向社会开考主考学校","上午（9：00-11：30）","下午（14：30-17：00）","上午（9：00-11：30）","下午（14：30-17：00）","上午（9：00-11：30）","下午（14：30-17：00）","上午（9：00-11：30）","下午（14：30-17：00）");
*/
        List<String> titleList = Lists.newArrayList("开考专业",
                KcSj.getkssj("1").substring(0,KcSj.getkssj("1").indexOf('日') + 1),
                KcSj.getkssj("3").substring(0,KcSj.getkssj("3").indexOf('日') + 1),
                KcSj.getkssj("5").substring(0,KcSj.getkssj("5").indexOf('日') + 1),
                KcSj.getkssj("7").substring(0,KcSj.getkssj("7").indexOf('日') + 1),
                "专业代码名称","面向社会开考主考学校",
                KcSj.getkssj("1").substring(KcSj.getkssj("1").indexOf('日') + 1),
                KcSj.getkssj("2").substring(KcSj.getkssj("2").indexOf('日') + 1),
                KcSj.getkssj("1").substring(KcSj.getkssj("1").indexOf('日') + 1),
                KcSj.getkssj("2").substring(KcSj.getkssj("2").indexOf('日') + 1),
                KcSj.getkssj("1").substring(KcSj.getkssj("1").indexOf('日') + 1),
                KcSj.getkssj("2").substring(KcSj.getkssj("2").indexOf('日') + 1),
                KcSj.getkssj("1").substring(KcSj.getkssj("1").indexOf('日') + 1),
                KcSj.getkssj("2").substring(KcSj.getkssj("2").indexOf('日') + 1));
        return titleList;
    }

    private List<CellRangeAddress> addMergeOrder() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 0, 3));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 4, 7));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 8, 11));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 12, 15));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 16, 19));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 0, 1));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 2, 3));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 4, 5));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 6, 7));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 8, 9));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 10,11));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 12,13));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 14,15));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 16,17));
        cellRangeAddressList.add(new CellRangeAddress(1, 2, 18,19));
        return cellRangeAddressList;
    }
//安排上下半年的课程安排
    public String excelOutkcOnBrowser(HttpServletResponse resp, String excelName, List<ArrangeTableVO> arrangeTableVO) {

        String status = "0";    // 设置导出方法执行结果，0失败，1成功，2成功但文件名已存在
        String message = "";    // 设置提示信息
        JSONObject obj = new JSONObject();      // 存放执行信息status和message

        // 创建工作簿类
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名，表名为 传入的表名参数，请注意工作表名不是Excel文件名
        XSSFSheet sheet = workbook.createSheet(excelName);
        // 工作表中的行
        XSSFRow row = null;
        // 设置Excel文件名，例如：学生表 + 20230114（这是我自己写的时间获取方法，别人用不了，删了就行） + .xlsx
        String excelFileName = excelName + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth()+ ".xlsx";
        // 设置表头
        List<String> titleList = setkcTableTitle();
        // 设置表头合并
        List<CellRangeAddress> cellRangeAddressList = addkcMergeOrder();

        setTitle.exportTitle(workbook,sheet,titleList,cellRangeAddressList);

        int rowNum = 1;     // 行下标
        int colNum = 0;
        for (int i = 0; i < arrangeTableVO.size(); i++) {
            ArrangeTableVO table = arrangeTableVO.get(i);
            List<Date> date = table.getDate();
            for (int l = 0; l < date.size(); l++) {
                List<Morning> morningList = date.get(l).getMorningList();
                List<Afternoon> afternoonList = date.get(l).getAfternoonList();
                if (morningList != null){
                    for (int a = 0; a < morningList.size(); a++) {
                        row = sheet.createRow(rowNum);
                        colNum = 0;
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,0,1));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,2,3));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,4,7));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,8,9));
                        XSSFCell kcdm = row.createCell(colNum);
                        colNum +=2;
                        String paddedKcDm = String.format("%05d", Integer.parseInt(morningList.get(a).getKc_dm()));
                        morningList.get(a).setKc_dm(paddedKcDm);
                        kcdm.setCellValue(morningList.get(a).getKc_dm());
                        XSSFCell kcmc = row.createCell(colNum);
                        colNum += 2;
                        kcmc.setCellValue(morningList.get(a).getKc_mc());
                        colNum += 2;
                        rowNum++;
                    }
                }
                if (afternoonList != null){
                    for (int a = 0; a < afternoonList.size(); a++) {
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,0,1));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,2,3));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,4,7));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,8,9));
                        row = sheet.createRow(rowNum);
                        colNum = 0;
                        XSSFCell kcdm = row.createCell(colNum);
                        colNum += 2;
                        String paddedKcDm = String.format("%05d", Integer.parseInt(afternoonList.get(a).getKc_dm()));
                        afternoonList.get(a).setKc_dm(paddedKcDm);
                        kcdm.setCellValue(afternoonList.get(a).getKc_dm());
                        XSSFCell kcmc = row.createCell(colNum);
                        colNum += 2;
                        kcmc.setCellValue(afternoonList.get(a).getKc_mc());
                        XSSFCell kssj = row.createCell(colNum);
                        kssj.setCellValue(date.get(l).getSj() + "下午14：30-17：00");
                        colNum += 2;
                        rowNum++;
                    }
                }
            }
        }



        try {
            // 设置响应格式，让浏览器知道是下载操作
            resp.setContentType("applicaton/x-mdownload");
            // 设置下载后的文件名
            resp.setHeader("Content-Disposition", "atachment;filename=" + new String(excelFileName.getBytes("utf-8"),"ISO8859-1"));
            // 设置响应编码
            resp.setContentType("text/html;charcet=UTF-8");
            // 建立输出流的连接
            OutputStream outputStream = resp.getOutputStream();

            // 将数据导出到Excel表格
            workbook.write(outputStream);

            // 关闭输出流
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("excel导出失败。");
            obj.put("status", status);
            obj.put("message", "excel导出失败。");

            return obj.toJSONString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        status = "1";
        obj.put("status", status);
        obj.put("message", "excel导出成功。");

        return obj.toJSONString();
    }


    public String excelOutlastOnBrowser(HttpServletResponse resp, String excelName, List<TblKs> tblKs) {

        String status = "0";    // 设置导出方法执行结果，0失败，1成功，2成功但文件名已存在
        String message = "";    // 设置提示信息
        JSONObject obj = new JSONObject();      // 存放执行信息status和message

        // 创建工作簿类
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名，表名为 传入的表名参数，请注意工作表名不是Excel文件名
        XSSFSheet sheet = workbook.createSheet(excelName);
        // 工作表中的行
        XSSFRow row = null;
        // 设置Excel文件名，例如：学生表 + 20230114（这是我自己写的时间获取方法，别人用不了，删了就行） + .xlsx
        String excelFileName = excelName + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth()+ ".xlsx";
        // 设置表头
        List<String> titleList = setkcTableTitle();
        // 设置表头合并
        List<CellRangeAddress> cellRangeAddressList = addkcMergeOrder();

        setTitle.exportTitle(workbook,sheet,titleList,cellRangeAddressList);

        int rowNum = 1;     // 行下标
        int colNum = 0;

        for (int i = 0; i < tblKs.size(); i++) {
            colNum = 0;
            row = sheet.createRow(rowNum);
            TblKs table = tblKs.get(i);
            String paddedKcDm = String.format("%05d", Integer.parseInt(table.getKc_dm()));
            table.setKc_dm(paddedKcDm);
            String kc_dm = table.getKc_dm();
            String kcMc = table.getKc_mc();
            String bz = table.getBz();
            Integer ksSj = table.getKs_sj();
            if (ksSj != null) {
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 7));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 8, 9));
                XSSFCell dmcel = row.createCell(colNum);
                colNum += 2;
                dmcel.setCellValue(kc_dm);
                XSSFCell mccel = row.createCell(colNum);
                colNum += 2;
                mccel.setCellValue(kcMc);
                XSSFCell kssj = row.createCell(colNum);
                kssj.setCellValue(getKssj(ksSj));
                colNum += 4;
                XSSFCell bzcell = row.createCell(colNum);
                bzcell.setCellValue(bz);
                rowNum++;
            }
        }



        try {
            // 设置响应格式，让浏览器知道是下载操作
            resp.setContentType("applicaton/x-mdownload");
            // 设置下载后的文件名
            resp.setHeader("Content-Disposition", "atachment;filename=" + new String(excelFileName.getBytes("utf-8"),"ISO8859-1"));
            // 设置响应编码
            resp.setContentType("text/html;charcet=UTF-8");
            // 建立输出流的连接
            OutputStream outputStream = resp.getOutputStream();

            // 将数据导出到Excel表格
            workbook.write(outputStream);

            // 关闭输出流
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("excel导出失败。");
            obj.put("status", status);
            obj.put("message", "excel导出失败。");

            return obj.toJSONString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        status = "1";
        obj.put("status", status);
        obj.put("message", "excel导出成功。");

        return obj.toJSONString();
    }
    public String excelOutlaterOnBrowser(HttpServletResponse resp, String excelName, List<TblKs> tblKs) {

        String status = "0";    // 设置导出方法执行结果，0失败，1成功，2成功但文件名已存在
        String message = "";    // 设置提示信息
        JSONObject obj = new JSONObject();      // 存放执行信息status和message

        // 创建工作簿类
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名，表名为 传入的表名参数，请注意工作表名不是Excel文件名
        XSSFSheet sheet = workbook.createSheet(excelName);
        // 工作表中的行
        XSSFRow row = null;
        // 设置Excel文件名，例如：学生表 + 20230114（这是我自己写的时间获取方法，别人用不了，删了就行） + .xlsx
        String excelFileName = excelName + LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().getDayOfMonth()+ ".xlsx";
        // 设置表头
        List<String> titleList = setkcTableTitle();
        // 设置表头合并
        List<CellRangeAddress> cellRangeAddressList = addkcMergeOrder();

        setTitle.exportTitle(workbook,sheet,titleList,cellRangeAddressList);

        int rowNum = 1;     // 行下标
        int colNum = 0;
        for (int i = 0; i < tblKs.size(); i++) {
            colNum = 0;
            row = sheet.createRow(rowNum);
            TblKs table = tblKs.get(i);
            String paddedKcDm = String.format("%05d", Integer.parseInt(table.getKc_dm()));
            table.setKc_dm(paddedKcDm);
            String kc_dm = table.getKc_dm();
            String kcMc = table.getKc_mc();
            Integer ksSj = table.getKs_sjlater();
            String bz = table.getBz();
            if (ksSj != null) {
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2, 3));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 4, 7));
                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 8, 9));
                XSSFCell dmcel = row.createCell(colNum);
                colNum += 2;
                dmcel.setCellValue(kc_dm);
                XSSFCell mccel = row.createCell(colNum);
                colNum += 2;
                mccel.setCellValue(kcMc);
                XSSFCell kssj = row.createCell(colNum);
                kssj.setCellValue(getKssj(ksSj));
                colNum += 4;
                XSSFCell bzcell = row.createCell(colNum);
                bzcell.setCellValue(bz);
                rowNum++;
            }
        }



        try {
            // 设置响应格式，让浏览器知道是下载操作
            resp.setContentType("applicaton/x-mdownload");
            // 设置下载后的文件名
            resp.setHeader("Content-Disposition", "atachment;filename=" + new String(excelFileName.getBytes("utf-8"),"ISO8859-1"));
            // 设置响应编码
            resp.setContentType("text/html;charcet=UTF-8");
            // 建立输出流的连接
            OutputStream outputStream = resp.getOutputStream();

            // 将数据导出到Excel表格
            workbook.write(outputStream);

            // 关闭输出流
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            System.out.println("excel导出失败。");
            obj.put("status", status);
            obj.put("message", "excel导出失败。");

            return obj.toJSONString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        status = "1";
        obj.put("status", status);
        obj.put("message", "excel导出成功。");

        return obj.toJSONString();
    }
/*    public String getKssj(Integer ksSjDm) {
        if (ksSjDm == 1) {
            return "4月13日上午9:00-11:30";
        }
        if (ksSjDm == 2) {
            return "4月13日下午14:30-17:00";
        }
        if (ksSjDm == 3) {
            return "4月14日上午9:00-11:30";
        }
        if (ksSjDm == 4) {
            return "4月14日下午14:30-17:00";
        }if (ksSjDm == 5) {
            return "10月26日上午9:00-11:30";
        }if (ksSjDm == 6) {
            return "10月26日下午14:30-17:00";
        }if (ksSjDm == 7) {
            return "10月27日上午9:00-11:30";
        }if (ksSjDm == 8) {
            return "10月27日下午14:30-17:00";
        } else return "";
    }*/
    public String getKssj(Integer ksSjDm) {
        if (ksSjDm == 1) {
            return KcSj.getkssj("1");
        }
        if (ksSjDm == 2) {
            return KcSj.getkssj("2");
        }
        if (ksSjDm == 3) {
            return KcSj.getkssj("3");
        }
        if (ksSjDm == 4) {
            return KcSj.getkssj("4");
        }if (ksSjDm == 5) {
            return KcSj.getkssj("5");
        }if (ksSjDm == 6) {
            return KcSj.getkssj("6");
        }if (ksSjDm == 7) {
            return KcSj.getkssj("7");
        }if (ksSjDm == 8) {
            return KcSj.getkssj("8");
        } else return "";
    }
    private List<String> setkcTableTitle(){
        List<String> titleList = Lists.newArrayList("课程代码","课程名称","考试时间","课程备注");
        return titleList;
    }

    private List<CellRangeAddress> addkcMergeOrder() {
        List<CellRangeAddress> cellRangeAddressList = new ArrayList<>();
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 0, 1));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 2, 3));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 4, 7));
        cellRangeAddressList.add(new CellRangeAddress(0, 0, 8, 9));
        return cellRangeAddressList;
    }



    /**
     * name:Excel导入功能
     * remark:该Excel导入功能，是一张表对应一个实体类，一行对应一条实体对象信息，一列对应一项实体对象属性
     * 所以针对不同实体类，需要进行微调
     * @param excelFile 预导入的Excel文件
     * @return
     */
   /* public List<Student> excelInput(MultipartFile excelFile) {

        String excelFileName = excelFile.getOriginalFilename();     // 获取excel文件名
        int excelVersion = getExcelVersion(excelFileName);          // 获取excel文件的版本

        Workbook workbook = null;
        List<Student> studentList = null;

        // 2003版本xls
        if (excelVersion == 1) {
            try {
                workbook = new HSSFWorkbook(excelFile.getInputStream());
                studentList = readExcelValue(workbook);

                return studentList;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 2007版本xlsx
        if (excelVersion == 2) {
            try {
                workbook = new XSSFWorkbook(excelFile.getInputStream());
                studentList = readExcelValue(workbook);

                return studentList;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // 判断要导入的excel信息，0代表未接收到，1代表2003版，2代表2007版
    public int getExcelVersion(String excelFileName) {
        int flag = 0;

        if (excelFileName.matches("^.+\\.(?i)(xls)$")) {
            flag = 1;
        }

        if (excelFileName.matches("^.+\\.(?i)(xlsx)$")) {
            flag = 2;
        }

        return flag;
    }

    // 读取Excel内容，将其放入对应的实体对象中
    private List<Student> readExcelValue(Workbook workbook) {

        int totalRows = 0;
        int totalCells = 0;

        // 获取第一张表
        Sheet sheet = workbook.getSheetAt(0);

        // 得到表的总行数
        totalRows = sheet.getPhysicalNumberOfRows();

        // 根据第一行（标题行）获得总列数，获得总列数得根据某行确定
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<Student> studentList = new ArrayList<>();

        // 逐行取数据，每行对应一条实体对象信息
        for (int rowNum = 1; rowNum < totalRows; rowNum++) {
            // 跳过标题行
            Row row = sheet.getRow(rowNum);

            if (row == null){
                continue;
            }

            Student student = new Student();
            // 逐列取数据，每列对应一个实体对象属性
            for (int colNum = 0; colNum < totalCells; colNum++) {
                System.out.println("第" + (rowNum + 1) + "行，第" + (colNum + 1) + "列");

                Cell cell = row.getCell(colNum);

                if (null != cell) {
                    if (colNum == 0) {           //第一列
                        // 如果是纯数字,将单元格类型转为String
                        if(cell.getCellTypeEnum() != CellType.STRING){
                            cell.setCellType(CellType.STRING);
                        }
                        student.setId(Long.valueOf(cell.getStringCellValue()));
                    }
                    else if (colNum == 1){
                        if(cell.getCellTypeEnum() != CellType.STRING){
                            cell.setCellType(CellType.STRING);
                        }
                        student.setName(cell.getStringCellValue());
                    }
                    else if (colNum == 2){
                        if(cell.getCellTypeEnum() != CellType.STRING){
                            cell.setCellType(CellType.STRING);
                        }

                        student.setAge(Integer.valueOf(cell.getStringCellValue()));
                    }
                    else if (colNum == 3){
                        if(cell.getCellTypeEnum() != CellType.STRING){
                            cell.setCellType(CellType.STRING);
                        }
                        student.setSex(cell.getStringCellValue());
                    }
                }
            }

            // 添加到studentList
            studentList.add(student);
        }

        return studentList;
    }*/
}
