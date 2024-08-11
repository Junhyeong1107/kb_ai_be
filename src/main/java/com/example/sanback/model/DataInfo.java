package com.example.sanback.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DataInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fileDate;
    private String filePath;
    private String fileId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDate fileDate) {
        this.fileDate = fileDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
