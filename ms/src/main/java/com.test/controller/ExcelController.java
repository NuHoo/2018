package com.test.controller;

import com.test.util.ExcelUtils;
import com.test.util.POIUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class ExcelController {
    @RequestMapping("/upload")
    public void upload(@RequestParam(value = "fileinfo") MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException{
        List<String[]> list = (List<String[]>) POIUtil.readExcel(file);
        for (String[] s:list){
            for (String a : s){
                System.out.print("|"+a+"|");
            }
        }
    }
    @RequestMapping("/u")
    public void u(@RequestParam(value = "fileinfo") MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException{
        String[] o = {""};
        LinkedHashMap<String,String> linkedHashMap = ExcelUtils.excel2json(file,o);
        System.out.println(linkedHashMap);
    }
}


