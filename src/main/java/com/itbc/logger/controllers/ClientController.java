package com.itbc.logger.controllers;

import com.itbc.logger.dto.ClientDTO;
import com.itbc.logger.dto.LoginDTO;
import com.itbc.logger.dto.TokenDTO;
import com.itbc.logger.model.Client;
import com.itbc.logger.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/clients")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> registerClient(@Valid @RequestBody ClientDTO clientDTO) {
        if (clientService.findByUsername(clientDTO.getUsername()).isPresent() || clientService.findByEmail(clientDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
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
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        Optional<Client> client = clientService.findByUsername(loginDTO.getAccount());
        if ((client.isEmpty()) || !(client.get().getPassword().equals(loginDTO.getPassword()))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(client.get().getUsername());
        return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
    }
}
