package com.itbc.logger.controllers;

import com.itbc.logger.dto.*;
import com.itbc.logger.model.Client;
import com.itbc.logger.services.ClientService;
import com.itbc.logger.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/clients")
@RestController
public class UserController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private LogService logService;

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        if (clientService.findByUsername(clientDTO.getUsername()).isPresent() || clientService.findByEmail(clientDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("Username or email already exists", HttpStatus.CONFLICT);
        }
        Client client = new Client();
        client.setEmail(clientDTO.getEmail());
        client.setUsername(clientDTO.getUsername());
        client.setPassword(clientDTO.getPassword());
        client.setAdmin(false);
        clientService.save(client);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Client> client = clientService.findByUsername(loginDTO.getAccount());
        if ((client.isEmpty()) || !(client.get().getPassword().equals(loginDTO.getPassword()))) {
            return new ResponseEntity<>("Email/Username or password incorrect", HttpStatus.BAD_REQUEST);
        }

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(client.get().getUsername());
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getClients(@RequestHeader("Authorization") String token) {
        Optional<Client> optionalClient = clientService.findByUsername(token);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>("Incorrect Token", HttpStatus.FORBIDDEN);
        }
        if (!optionalClient.get().isAdmin()) {
            return new ResponseEntity<>("Correct Token but not admin", HttpStatus.UNAUTHORIZED);
        }
        List<Client> clients = clientService.findByIsAdmin(false);
        List<ClientSearchDTO> clientSearchDTOS = new ArrayList<>();
        for(Client client: clients) {
            ClientSearchDTO clientSearchDTO = new ClientSearchDTO();
            clientSearchDTO.setId(client.getId());
            clientSearchDTO.setEmail(client.getEmail());
            clientSearchDTO.setUsername(client.getUsername());
            clientSearchDTO.setLogCount(logService.countByClient(client));
            clientSearchDTOS.add(clientSearchDTO);
        }
        return new ResponseEntity<>(clientSearchDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{clientId}/reset-password")
    public ResponseEntity changePassword(@RequestHeader("Authorization") String token, @PathVariable long clientId, @RequestBody @Valid PasswordDTO passwordDTO) {
        Optional<Client> client = clientService.findByUsername(token);
        if (client.isEmpty()) {
            return new ResponseEntity("Incorrect token", HttpStatus.FORBIDDEN);
        }
        if (!client.get().isAdmin()) {
            return new ResponseEntity("Correct token but not admin", HttpStatus.UNAUTHORIZED);
        }
        Optional<Client> changeClientPassword = clientService.findById(clientId);
        changeClientPassword.get().setPassword(passwordDTO.getPassword());

        clientService.save(changeClientPassword.get());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
