package com.itbc.logger.dto;

import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

public class LogDTO {
    private String message;
    @Range(min = 0, max = 2)
    private int logType;
    private LocalDateTime createdDate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
