package com.example.sanback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmotionController {

    private List<Map<String, Object>> emotionData = new ArrayList<>();
    private List<Map<String, Object>> stressData = new ArrayList<>();

    @PostMapping("/receiveEmotionData")
    public void receiveEmotionData(@RequestBody List<Map<String, Object>> emotionData) {
        this.emotionData = emotionData;  // 데이터를 저장
        System.out.println("Received emotion data: " + emotionData);
    }

    @GetMapping("/getEmotionData")
    public List<Map<String, Object>> getEmotionData() {
        return emotionData;  // 저장된 데이터를 반환
    }

    @PostMapping("/receiveStressData")
    public void receiveStressData(@RequestBody List<Map<String, Object>> stressData) {
        this.stressData = stressData;  // 데이터를 저장
        System.out.println("Received stress data: " + stressData);
    }

    @GetMapping("/getStressData")
    public List<Map<String, Object>> getStressData() {
        return stressData;  // 저장된 데이터를 반환
    }
}
