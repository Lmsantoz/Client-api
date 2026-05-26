package com.lucasmarques.clientapi.repository;

import com.lucasmarques.clientapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    List<Client> findByName(String name);

    Optional<Client> findByEmail(String email);

}
