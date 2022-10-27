package com.itbc.logger.services.impl;
import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;
import com.itbc.logger.repositories.LogRepository;
import com.itbc.logger.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public int countByClient(Client client) {
        return logRepository.countByClient(client);
    }

    @Override
    public List<Log> searchLogs(String username, LocalDateTime dateFrom, LocalDateTime dateTo, String message, LogType logType) {
        return logRepository.searchLogs(username, dateFrom, dateTo, message, logType);
    }
}
