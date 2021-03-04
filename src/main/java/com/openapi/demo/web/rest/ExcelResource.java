package com.openapi.demo.web.rest;

import com.openapi.demo.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@Transactional
public class ExcelResource {

    @GetMapping("/importExcel")
    @ApiOperation(value = "导出测试")
    public ResponseEntity<String> importExcel(Pageable pageable) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet1");

        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("啦啦啦啦啦啦");

        String fileName = "导出测试.xlsx";
        String path = "C:\\Users\\wkn\\Desktop\\" + fileName;

        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(fileName);
    }
}
