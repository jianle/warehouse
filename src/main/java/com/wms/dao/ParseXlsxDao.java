package com.wms.dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class ParseXlsxDao {

    private Logger logger = LoggerFactory.getLogger(ParseXlsxDao.class);

    public List<List<String>> parseExcel(FileInputStream file, int ignoreRows)
             {

        logger.info("parseExcel start.......");
        
        BufferedInputStream in = new BufferedInputStream(file);
        
        // 打开HSSFWorkbook
        Workbook workbook = null;
        try {
            workbook = ParseXlsxDao.create(in);
        } catch (InvalidFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        // 获取sheet个数
        int sheetCounter = workbook.getNumberOfSheets();
        logger.info("input file excel have " + sheetCounter + " sheets");
        if(sheetCounter<=0){
            return null;
        }

        // 定义返回值
        List<List<String>> data = new ArrayList<List<String>>();
        
        Cell cell = null;
        
        for (int sheetIndex = 0; sheetIndex < sheetCounter; sheetIndex++) {
            Sheet st = workbook.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                Row row = st.getRow(rowIndex);
                
                if (row == null) {
                    continue;
                }
                List<String> tmpList = new ArrayList<String>();
                for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_NUMERIC: 
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                   Date date = cell.getDateCellValue();
                                   if (date != null) {
                                       value = new SimpleDateFormat("yyyy-MM-dd")
                                              .format(date);
                                   } else {
                                       value = "";
                                   }
                                } else {
                                   value = new DecimalFormat("0").format(cell
                                          .getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                   value = cell.getStringCellValue();
                                } else {
                                   value = cell.getNumericCellValue() + "";
                                }
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                value = "";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y"
    
                                       : "N");
                                break;
                            default:
                                value = "";
                            }
                        } 
                    tmpList.add(value);
                }
                data.add(tmpList);
            }
        }
        
        try {
            in.close();
            file.close();
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("关闭流失败.");
        }
        return data;
    }


    public static Workbook create(InputStream inputStream) throws IOException,
            InvalidFormatException {
        if (!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            return new HSSFWorkbook(inputStream);
        }
        if (POIXMLDocument.hasOOXMLHeader(inputStream)) {
            return new XSSFWorkbook(OPCPackage.open(inputStream));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");
    }
    
}
