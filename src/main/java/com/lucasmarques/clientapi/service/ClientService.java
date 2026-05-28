package com.lucasmarques.clientapi.service;

import com.lucasmarques.clientapi.entity.Client;
import com.lucasmarques.clientapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Client findById(UUID id){
        return  clientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public void delete(UUID id) {
        findById(id);
        clientRepository.deleteById(id);
    }

    public Client update(UUID id, Client client) {
        Client existing = findById(id);
        existing.setName(client.getName());
        existing.setEmail(client.getEmail());
        existing.setCpf(client.getCpf());
        existing.setBirthDate(client.getBirthDate());
        return clientRepository.save(existing);
    }
}
