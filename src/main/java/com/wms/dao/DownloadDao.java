package com.wms.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Repository;

@Repository
public class DownloadDao {
    // 用于下载
    public String download(HttpServletResponse response,String fileName,
            JSONArray json,String []keys,String columns[]) throws IOException {
        
        if (fileName == null || "".equals(fileName)) {
            fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() + "_wms";
        }
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            createWorkBook(json,keys,keys).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        ServletOutputStream out = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            out = response.getOutputStream();
        } catch (Exception e) {
            // TODO: handle exception
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        
        return null;
    }
    
    /**
     * 创建excel文档，
     * @param json 传入数据
     * @param keys list中map的key数组集合
     * @param columns excel的列名
     * */
    private Workbook createWorkBook(JSONArray json,String []keys,String columns[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        JSONObject jsonObject = null;
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet1");
        
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35 * 100));
        }
 
        // 创建第一行
        Row row = sheet.createRow((short) 0);
 
        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
 
        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
 
        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
 
        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        
        /*
         
        Font f3=wb.createFont();
        f3.setFontHeightInPoints((short) 10);
        f3.setColor(IndexedColors.RED.getIndex());
 
        */
 
        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);
 
        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0; i<columns.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 0; i < json.size(); i++) {
            jsonObject = JSONObject.fromObject(json.get(i));
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0; j<jsonObject.size(); j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(jsonObject.get(keys[j]) == null?" ": String.valueOf(jsonObject.get(keys[j])));
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

}
