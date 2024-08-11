package com.example.sanback.controller;

import com.example.sanback.model.CsvModel;
import com.example.sanback.service.CsvService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CsvController {

    @Autowired
    private CsvService csvService;

    @Autowired
    private ObjectMapper objectMapper;  // ObjectMapper를 주입받음

    @GetMapping("/csv/data")
    public Map<String, Object> getWeeklyTopCategoryData(@RequestParam("referenceDate") String referenceDateStr) throws IOException {
        String fileName = "park_2023-01.csv";
        List<CsvModel> transactions = csvService.readCsv(fileName);

        // referenceDate 파라미터를 LocalDate로 변환
        LocalDate referenceDate = LocalDate.parse(referenceDateStr);

        // 주 단위로 카테고리별 총 금액과 가장 높은 카테고리의 데이터 계산
        Map<LocalDate, Map<String, Object>> weeklyData = csvService.getWeeklyTopCategoryData(transactions, referenceDate);

        // 응답으로 반환할 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("weeklyData", weeklyData);

        // JSON 응답을 로그로 출력
        String jsonResponse = objectMapper.writeValueAsString(response);
        System.out.println("JSON Response: " + jsonResponse);
        
        return response;
    }
}
