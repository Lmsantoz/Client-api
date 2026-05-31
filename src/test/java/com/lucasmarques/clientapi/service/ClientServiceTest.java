package com.lucasmarques.clientapi.service;

import com.lucasmarques.clientapi.entity.Client;
import com.lucasmarques.clientapi.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientService clientService;

    @Test
    public void findAll()
    {
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        List<Client> clients = new ArrayList<>();

        Client client1 = new Client();
        client1.setId(uuid);
        client1.setName("Client1");
        client1.setEmail("client1@gmail.com");
        clients.add(client1);

        Client client2 = new Client();
        client2.setId(uuid2);
        client2.setName("Client2");
        client2.setEmail("client2@gmail.com");
        clients.add(client2);

        Pageable pageable = PageRequest.of(0, 10);
        when(clientRepository.findAll(pageable)).thenReturn(new PageImpl<>(clients));

        Page<Client> response = clientService.findAll(pageable);

        Assertions.assertEquals(2, response.getTotalElements());
        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals("Client1", response.getContent().get(0).getName());
    }

    @Test
    public void findById()
    {
     Client  client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");

        when(clientRepository.findById( client1.getId())).thenReturn(Optional.of(client1));

        Assertions.assertEquals(client1, clientService.findById(client1.getId()));
    }

    @Test
    public void findByIdNotFound()
    {
        Client  client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");

        when(clientRepository.findById( client1.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, ()->clientService.findById(client1.getId()) );
    }

    @Test
    public void deleteById()
    {
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");

        when(clientRepository.findById(client1.getId())).thenReturn(Optional.of(client1));

        clientService.delete(client1.getId());

        verify(clientRepository).deleteById(client1.getId());
    }

    @Test
    public void deleteByIdNotFound()
    {
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");

        when(clientRepository.findById(client1.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, ()->clientService.delete(client1.getId()) );
    }

    @Test
    public void update()
    {
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");
        when(clientRepository.findById(client1.getId())).thenReturn(Optional.of(client1));

        clientService.update(client1.getId(), client1);

        verify(clientRepository).save(client1);
    }

    @Test
    public void updateNotFound()
    {
        Client client1 = new Client();
        client1.setId(UUID.randomUUID());
        client1.setName("Client1");
        when(clientRepository.findById(client1.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, ()->clientService.update(client1.getId(), client1));
    }

    @Test
    public void createClient()
    {
        Client client1 = new Client();
        client1.setName("Client1");

        when(clientRepository.save(client1)).thenReturn(client1);

        Assertions.assertEquals(client1, clientService.create(client1));
    }

}
