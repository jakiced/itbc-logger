package com.itbc.logger.services;

import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    public Log save(Log log);
    int countByClient(Client client);
    List<Log> searchLogs(String username, LocalDateTime dateFrom, LocalDateTime dateTo, String message, LogType logType);
}
