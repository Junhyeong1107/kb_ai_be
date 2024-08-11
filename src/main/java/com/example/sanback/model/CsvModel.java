package com.example.sanback.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDate;

public class CsvModel {

    @CsvBindByName(column = "카테고리")
    private String category;

    @CsvBindByName(column = "금액")
    private double amount;

    @CsvBindByName(column = "업종")
    private String businessType;

    @CsvDate("yyyy-MM-dd")
    @CsvBindByName(column = "날짜")
    private LocalDate date;

    // 시간은 만약 사용하지 않는다면 제거하거나, 필요시 보유할 수 있습니다.
    // @CsvBindByName(column = "시간")
    // private String time;

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
