package com.offcn.controller;

import com.offcn.service.MemberService;
import com.offcn.service.ReportService;
import com.offcn.service.SetmealService;
import com.offcn.util.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;
    /**
     * 获取会员报表信息
     * @return
     */
    @RequestMapping("getMemberReport")
    public Result getMemberReport(){
        Result result = memberService.getMemberReport();
        return result;
    }

    /**
     * 获取套餐报表信息
     * @return
     */
    @RequestMapping("getSetmealReport")
    public Result getSetmealReport(){
        Result result = setmealService.getSetmealReport();
        return result;
    }

    /**
     * 统计商业数据
     * @return
     */
    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData(){
        Result result = reportService.getBusinessReportData();
        return result;
    }

    /**
     * 导出商业数据
     * @return
     */
    @RequestMapping("exportBusinessReport")
    public ResponseEntity<byte[]> exportBusinessReport() throws IOException {
        //获取要导出的数据
        Result result = reportService.getBusinessReportData();
        Map<String,Object> map=(Map<String,Object>)result.getData();
        //创建excel文件
        String fileName="businessReport.xlsx";
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建sheet
        XSSFSheet sheet = workbook.createSheet();
        //创建第一行数据
        XSSFRow row1 = sheet.createRow(0);
        //设置第一行每一列的数据
        row1.createCell(0).setCellValue("当天统计日期");
        row1.createCell(1).setCellValue("当天新增会员数量");
        row1.createCell(2).setCellValue("总的会员数量");
        row1.createCell(3).setCellValue("本周新增会员数量");
        row1.createCell(4).setCellValue("本月新增会员数量");
        row1.createCell(5).setCellValue("当天订单数量");
        row1.createCell(6).setCellValue("本周订单数量");
        row1.createCell(7).setCellValue("本月订单数量");
        //创建第二行数据
        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue((String) map.get("reportDate"));
        row2.createCell(1).setCellValue((Integer)map.get("todayNewMember"));
        row2.createCell(2).setCellValue((Integer)map.get("totalMember"));
        row2.createCell(3).setCellValue((Integer)map.get("thisWeekNewMember"));
        row2.createCell(4).setCellValue((Integer)map.get("thisMonthNewMember"));
        row2.createCell(5).setCellValue((Integer)map.get("todayOrderNumber"));
        row2.createCell(6).setCellValue((Integer)map.get("thisWeekOrderNumber"));
        row2.createCell(7).setCellValue((Integer)map.get("thisMonthOrderNumber"));
        //创建第三行
        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("套餐名称");
        row3.createCell(1).setCellValue("订单数量");
        row3.createCell(2).setCellValue("订单占比");
        //获取热门套餐
        List<Map<String,Object>> hotSetmeal=(List<Map<String,Object>>)map.get("hotSetmeal");
        for(Map<String,Object> map1:hotSetmeal){
            //获取上一行数据
            int lastRowNum = sheet.getLastRowNum();
            //创建下一行数据
            XSSFRow nextRow = sheet.createRow(lastRowNum + 1);
            nextRow.createCell(0).setCellValue((String) map1.get("name"));
            nextRow.createCell(1).setCellValue((Long)map1.get("setmeal_count"));
            nextRow.createCell(2).setCellValue(String.valueOf(map1.get("proportion")));
        }
        //将excel文件workbook转换未字节数组
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        //创建响应头
        HttpHeaders httpHeaders=new HttpHeaders();
        //设置响应类型
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置浏览器打开方式
        httpHeaders.setContentDispositionFormData("attachment",fileName);

        return new ResponseEntity<>(bytes,httpHeaders, HttpStatus.OK);

    }
}
