package com.itbc.logger.repositories;

import com.itbc.logger.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByIsAdmin(boolean isAdmin);

    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);
}
