package com.itbc.logger.repositories;

import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    int countByClient(Client client);
    @Query("select l from Log l WHERE l.client.username = :username and (:dateFrom is null or l.createdDate >= :dateFrom) and (:dateFrom is null or l.createdDate <= :dateTo) and" +
            " (:dateFrom is null or l.createdDate <= :dateTo) and (:message is null or l.message like %:message%) and (:logType is null or l.logType = :logType)")
    List<Log> searchLogs(String username, LocalDateTime dateFrom, LocalDateTime dateTo, String message, LogType logType);
}
