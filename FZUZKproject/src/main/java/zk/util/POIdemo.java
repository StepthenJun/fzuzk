package zk.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POIdemo {
    // Excel数据导出
    public static void main(String[] args) {
        // Excel2003版本（包含2003）以前使用HSSFWorkbook类，扩展名为.xls
        // Excel2007版本（包含2007）以后使用XSSFWorkbook类，扩展名为.xlsx

        // 创建工作簿类
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建工作表并设置表名
        XSSFSheet sheet = workbook.createSheet("学生信息");

        // 创建行，下标从0开始
        XSSFRow row = sheet.createRow(0);

        // 在行中创建列并赋值，下标从0开始
        row.createCell(0).setCellValue("学号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("专业");
        row.createCell(3).setCellValue("班级");

        row = sheet.createRow(1);

        row.createCell(0).setCellValue("2018010627");
        row.createCell(1).setCellValue("赵国庆");
        row.createCell(2).setCellValue("软件工程");
        row.createCell(3).setCellValue("软件1806");

        // 设置Excel文件路径
        File file = new File("C:\\Users\\86187\\Desktop\\poi_demo.xlsx");

        try {
            // 创建指向该路径的输出流
            FileOutputStream stream = new FileOutputStream(file);

            // 将数据导出到Excel表格
            workbook.write(stream);

            // 关闭输出流
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


