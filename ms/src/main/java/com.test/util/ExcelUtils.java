package com.test.util;

import java.text.DecimalFormat;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.io.IOException;
import static org.apache.poi.ss.usermodel.Cell.*;

public class ExcelUtils {
    /**
     * 解决思路：采用Apache的POI的API来操作Excel，读取内容后保存到List中，再将List转Json（推荐Linked，增删快，与Excel表顺序保持一致）
     *
     *          Sheet表1  ————>    List1<Map<列头，列值>>
     *          Sheet表2  ————>    List2<Map<列头，列值>>
     *
     * 步骤1：根据Excel版本类型创建对于的Workbook以及CellSytle
     * 步骤2：遍历每一个表中的每一行的每一列
     * 步骤3：一个sheet表就是一个Json，多表就多Json，对应一个 List
     *       一个sheet表的一行数据就是一个 Map
     *       一行中的一列，就把当前列头为key，列值为value存到该列的Map中
     *
     *
     *
     * @return Map  一个线性HashMap，以Excel的sheet表顺序，并以sheet表明作为key，sheet表转换json后的字符串作为value
     * @throws IOException
     */
    public static LinkedHashMap<String,String> excel2json(MultipartFile file,String[] p) throws IOException {

        System.out.println("excel2json方法执行....");

        // 返回的map
        LinkedHashMap<String,String> excelMap = new LinkedHashMap<>();

        // Excel列的样式，主要是为了解决Excel数字科学计数的问题
        CellStyle cellStyle;
        // 根据Excel构成的对象
        Workbook wb;

        // 如果是2007及以上版本，则使用想要的Workbook以及CellStyle
        if(file.getOriginalFilename().endsWith("xlsx")){
            System.out.println("是2007及以上版本  xlsx");
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        }else{
            System.out.println("是2007以下版本  xls");
            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
            wb = new HSSFWorkbook(fs);
            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // 设置Excel列的样式为文本
            cellStyle.setDataFormat(dataFormat.getFormat("@"));
        }

        // sheet表个数
        int sheetsCounts = wb.getNumberOfSheets();
        // 遍历每一个sheet
        for (int i = 0; i < sheetsCounts; i++) {
            Sheet sheet = wb.getSheetAt(i);
            System.out.println("第" + i + "个sheet:" + sheet.toString());

            // 一个sheet表对于一个List
            List list = new LinkedList();

            // 将第一行的列值作为正个json的key
             p = new String[]{"name","age","sex","tel","address","e-mail","phone"};
            // 取第一行列的值作为key
            Row fisrtRow = sheet.getRow(0);
            // 如果第一行就为空，则是空sheet表，该表跳过
            if(null == fisrtRow){
                continue;
            }
            // 得到第一行有多少列
//            int curCellNum = fisrtRow.getLastCellNum();
//            System.out.println("第一行的列数：" + curCellNum);
            // 根据第一行的列数来生成列头数组
//            cellNames = new String[curCellNum];
            // 单独处理第一行，取出第一行的每个列值放在数组中，就得到了整张表的JSON的key
//            for (int m = 0; m < p.length - 1; m++) {
//                Cell cell = fisrtRow.getCell(m);
//                // 设置该列的样式是字符串
//                cell.setCellStyle(cellStyle);
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                // 取得该列的字符串值
//                p[m] = cell.getStringCellValue();
//            }
//            for (String s : p) {
//                System.out.print("得到第" + i +" 张sheet表的列头： " + s + ",");
//            }
//            System.out.println();
            // 从第二行起遍历每一行
            int rowNum = sheet.getLastRowNum();
            System.out.println("总共有 " + rowNum + " 行");
            for (int j = 1; j <=rowNum; j++) {
                try {
                    // 一行数据对于一个Map
                    LinkedHashMap rowMap = new LinkedHashMap();
                    // 取得某一行
//                    Row row = sheet.getRow(j);
//                    int cellNum = row.getLastCellNum();
                    Row row = sheet.getRow(j);
                    //遇到空行跳过
                    if (row == null)
                        continue;
                    for (int k = 0; k < p.length - 1; k++) {
                        Cell cell = row.getCell(k);
                        //遇到空数据跳过
                        if (null == cell)
                            continue;
                       cell.setCellStyle(cellStyle);
//                       row.getCell(k).setCellType(Cell.CELL_TYPE_STRING);
                        // 保存该单元格的数据到该行中
                        try {
                            switch (cell.getCellType()) {
                                case CELL_TYPE_NUMERIC: // 数字
                                    cell.setCellType(CELL_TYPE_STRING);
                                    rowMap.put(p[k],row.getCell(k).getStringCellValue());
                                    System.out.print(cell.getStringCellValue() + "   ");
                                    break;
                                case CELL_TYPE_STRING: // 字符串
                                    cell.setCellType(CELL_TYPE_STRING);
                                    rowMap.put(p[k],row.getCell(k).getStringCellValue());
                                    System.out.print(cell.getStringCellValue() + "   ");
                                    break;
                                case CELL_TYPE_BOOLEAN: // Boolean
                                    cell.setCellType(CELL_TYPE_STRING);
                                    rowMap.put(p[k],row.getCell(k).getStringCellValue());
                                    System.out.println(cell.getStringCellValue() + "   ");
                                    break;
                                case CELL_TYPE_FORMULA: // 公式
                                    cell.setCellType(CELL_TYPE_STRING);
                                    rowMap.put(p[k],row.getCell(k).getStringCellValue());
                                    System.out.print(cell.getCellFormula() + "   ");
                                    break;
                                case CELL_TYPE_ERROR: // 故障
                                    cell.setCellType(CELL_TYPE_STRING);
                                    rowMap.put(p[k],row.getCell(k).getStringCellValue());
                                    System.out.println(" ");
                                    break;
                                default:
                                    System.out.print("未知类型   ");
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    // 保存该行的数据到该表的List中
                    list.add(rowMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(list);
            // 将该sheet表的表名为key，List转为json后的字符串为Value进行存储
            excelMap.put(sheet.getSheetName(), JSON.toJSONString(list,false));
        }
        System.out.println("excel2json方法结束....");
        return excelMap;
    }

}
