package com.itbc.logger.services;

import com.itbc.logger.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public Optional<Client> findByUsername(String username);
    public Optional<Client> findByEmail(String email);
    public Client save(Client client);
    public List<Client> findAll();
    public Optional<Client> findById(long id);

    List<Client> findByIsAdmin(boolean isAdmin);
}
