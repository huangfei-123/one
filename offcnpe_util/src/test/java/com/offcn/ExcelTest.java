package com.offcn;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    @Test
    public void test1() throws IOException {
        //读取excel文件
        XSSFWorkbook  workbook=new XSSFWorkbook(new FileInputStream("C:\\Users\\mwx\\uploadFile\\hello.xlsx"));
        //获取有数据的sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取第一行数据
        XSSFRow title = sheet.getRow(0);
        //获取第一行第一列
        String name = title.getCell(0).getStringCellValue();
        //获取第一行第二列
        String age=title.getCell(1).getStringCellValue();
        //获取第一行第三列表
        String sex=title.getCell(2).getStringCellValue();
        System.out.println(name+"----"+age+"------"+sex);
        //获取最后一行的索引
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<lastRowNum;i++){
            XSSFRow nextRow = sheet.getRow(i + 1);
            String nextName=nextRow.getCell(0).getStringCellValue();
            double nextAge = nextRow.getCell(1).getNumericCellValue();
            String nextSex = nextRow.getCell(2).getStringCellValue();
            System.out.println(nextName+"----"+nextAge+"------"+nextSex);
        }
    }
    @Test
    public void test2() throws IOException {
        //创建一个excel文档
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建第一个sheet
        XSSFSheet sheet = workbook.createSheet();
        //创建第一行数据
        XSSFRow title = sheet.createRow(0);
        //创建第一行各列数据
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("电话");
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("13178687676");
        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("李四");
        row2.createCell(1).setCellValue("15565656565");

        FileOutputStream fileOutputStream=new FileOutputStream("C:\\Users\\mwx\\uploadFile\\offcn.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        workbook.close();

    }
}
