package com.lucasmarques.clientapi.controller;

import com.lucasmarques.clientapi.entity.Client;
import com.lucasmarques.clientapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Client> findAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return clientService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client findById(@PathVariable UUID id) {
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody @Valid Client client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client update(@PathVariable UUID id, @RequestBody @Valid Client client) {
        return clientService.update(id,client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        clientService.delete(id);
    }
}
