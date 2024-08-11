package com.example.sanback.service;

import com.example.sanback.model.CsvModel;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvService {

    public List<CsvModel> readCsv(String fileName) {
        try {
            // 파일을 클래스패스에서 가져오기
            ClassPathResource resource = new ClassPathResource(fileName);
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            List<CsvModel> transactions = new CsvToBeanBuilder<CsvModel>(reader)
                    .withType(CsvModel.class)
                    .build()
                    .parse();

            return transactions;
        } catch (Exception e) {
            System.err.println("CSV 파싱 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 주 단위로 카테고리별 금액을 계산하고, 가장 높은 카테고리의 데이터 반환
    public Map<LocalDate, Map<String, Object>> getWeeklyTopCategoryData(List<CsvModel> transactions, LocalDate referenceDate) {
        // 저번 주의 시작일과 종료일을 계산
        LocalDate startOfWeek = referenceDate.minusWeeks(1).with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        Map<LocalDate, Map<String, Object>> weeklyTopCategories = new HashMap<>();
        List<LocalDate> weeks = getWeekRange(startOfWeek, endOfWeek);

        for (LocalDate weekStart : weeks) {
            LocalDate weekEnd = weekStart.plusDays(6);

            // 해당 주의 트랜잭션 필터링 및 카테고리별 총 금액 계산
            Map<String, Double> categoryTotals = transactions.stream()
                    .filter(transaction -> transaction.getDate() != null)
                    .filter(transaction -> !transaction.getDate().isBefore(weekStart) && !transaction.getDate().isAfter(weekEnd))
                    .collect(Collectors.groupingBy(CsvModel::getCategory,
                            Collectors.summingDouble(CsvModel::getAmount)));

            // 가장 높은 금액을 가진 카테고리 찾기
            String topCategory = categoryTotals.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (topCategory != null) {
                // 가장 높은 금액을 가진 카테고리의 트랜잭션 내역 찾기
                List<Map<String, Object>> topCategoryDetails = transactions.stream()
                        .filter(transaction -> transaction.getCategory().equals(topCategory))
                        .filter(transaction -> !transaction.getDate().isBefore(weekStart) && !transaction.getDate().isAfter(weekEnd))
                        .map(transaction -> {
                            Map<String, Object> detail = new HashMap<>();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
                            detail.put("amount", transaction.getAmount());
                            detail.put("date", transaction.getDate().format(formatter));
                            detail.put("businessType", transaction.getBusinessType());
                            return detail;
                        })
                        .collect(Collectors.toList());

                // 주 단위 총 금액과 가장 높은 카테고리의 데이터를 포함하는 응답 구성
                Map<String, Object> weekData = new HashMap<>();
                weekData.put("totalAmount", categoryTotals.get(topCategory));
                weekData.put("topCategory", topCategory);
                weekData.put("details", topCategoryDetails);

                weeklyTopCategories.put(weekStart, weekData);
            }
        }
        return weeklyTopCategories;
    }

    // 주 범위를 계산하는 헬퍼 메서드
    private List<LocalDate> getWeekRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> weeks = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            weeks.add(current);
            current = current.plusWeeks(1);
        }
        return weeks;
    }
}
