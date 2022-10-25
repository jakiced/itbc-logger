package com.itbc.logger.model;

import java.time.LocalDateTime;

public class Log {

    private Long logId;
    private String message;
    private LogType logType;
    private LocalDateTime createdDate = LocalDateTime.now();
}
