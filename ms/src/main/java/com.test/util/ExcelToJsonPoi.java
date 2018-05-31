package com.test.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.*;
import java.util.Iterator;
//读取本地文件
public class ExcelToJsonPoi {
    public void readExcel(File file) {
        XSSFWorkbook book;
        XSSFSheet sheet;
        JSONArray jsons = null;
        XSSFRow row;
        String[] o = new String[]{"name", "age", "sex", "tel", "adress", "e-mail"};
        int passNum = 1;
        try {
            InputStream is = new FileInputStream(new File( "E:\\1.xlsx"));
//            book = new XSSFWorkbook(is);
            WorkbookFactory factory = new WorkbookFactory();
            Workbook workbook = factory.create(file);
            sheet = (XSSFSheet) workbook.getSheetAt(0);
            jsons = new JSONArray();
            Row fisrtRow = sheet.getRow(0);
            int curCellNum = fisrtRow.getLastCellNum();
            Iterator<Row> i = sheet.iterator();
            for (int n = 0; n < passNum; n++) {
                if (i.hasNext())
                    i.next();
            }
            while (i.hasNext()) {
                row = (XSSFRow) i.next();
                if (row != null) {
                    JSONObject json = new JSONObject();
                    //对于纯数字内容要做这一操作
                    for (int j = 0; j < o.length - 1; j++) {
                        try {
//                            int ss = Cell.CELL_TYPE_STRING;
//                            System.out.println(row.getCell(j));
                            row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                            json.put(o[j], row.getCell(j).getStringCellValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    jsons.add(json);
                }
            }
            System.out.println(jsons.toString());
            workbook.close();
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}


