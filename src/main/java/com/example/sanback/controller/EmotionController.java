package com.example.sanback.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmotionController {

    private List<Map<String, Object>> emotionData = new ArrayList<>();
    private List<Map<String, Object>> stressData = new ArrayList<>();

    // 파일 저장 경로
    private final String EMOTION_DATA_PATH = "/home/ec2-user/json/emotion_data.json";
    private final String STRESS_DATA_PATH = "/home/ec2-user/json/stress_data.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/receiveEmotionData")
    public void receiveEmotionData(@RequestBody List<Map<String, Object>> emotionData) {
        this.emotionData = emotionData;  // 데이터를 메모리에 저장
        System.out.println("Received emotion data: " + emotionData);
        
        // JSON 파일로 저장
        saveDataToFile(emotionData, EMOTION_DATA_PATH);
    }

    @GetMapping("/getEmotionData")
    public ResponseEntity<Resource> getEmotionData() {
        return getFileResponse(EMOTION_DATA_PATH, "emotion_data.json");
    }

    @PostMapping("/receiveStressData")
    public void receiveStressData(@RequestBody List<Map<String, Object>> stressData) {
        this.stressData = stressData;  // 데이터를 메모리에 저장
        System.out.println("Received stress data: " + stressData);
        
        // JSON 파일로 저장
        saveDataToFile(stressData, STRESS_DATA_PATH);
    }

    @GetMapping("/getStressData")
    public ResponseEntity<Resource> getStressData() {
        return getFileResponse(STRESS_DATA_PATH, "stress_data.json");
    }

    // 데이터를 파일로 저장하는 메서드
    private void saveDataToFile(List<Map<String, Object>> data, String filePath) {
        try {
            File file = new File(filePath);
            // 폴더가 없으면 생성
            file.getParentFile().mkdirs();
            // 데이터를 JSON 파일로 저장 (덮어쓰기)
            objectMapper.writeValue(file, data);
            System.out.println("Data saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save data to file: " + filePath);
        }
    }

    // 파일을 ResponseEntity로 반환하는 메서드
    private ResponseEntity<Resource> getFileResponse(String filePath, String fileName) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
