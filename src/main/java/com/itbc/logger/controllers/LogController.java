package com.itbc.logger.controllers;
import com.itbc.logger.dto.LogDTO;
import com.itbc.logger.model.Client;
import com.itbc.logger.model.Log;
import com.itbc.logger.model.LogType;
import com.itbc.logger.services.ClientService;
import com.itbc.logger.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/api/logs")
@RestController
public class LogController {

    @Autowired
    ClientService clientService;

    @Autowired
    LogService logService;

    @PostMapping("/create")
    public ResponseEntity<LogDTO> createLog(@RequestHeader("Authorization") String token, @Valid @RequestBody LogDTO logDTO) {
        Optional<Client> client = clientService.findByUsername(token);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (logDTO.getMessage().length() >= 1024) {
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE);
        }

        Log log = new Log();
        log.setMessage(logDTO.getMessage());
        log.setClient(client.get());
        log.setCreatedDate(LocalDateTime.now());

        if (logDTO.getLogType() == 0) log.setLogType(LogType.ERROR);
        if (logDTO.getLogType() == 1) log.setLogType(LogType.WARNING);
        if (logDTO.getLogType() == 2) log.setLogType(LogType.INFO);

        logService.save(log);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
