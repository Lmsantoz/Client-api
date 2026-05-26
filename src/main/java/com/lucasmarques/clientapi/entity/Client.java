package com.lucasmarques.clientapi.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthDate;

}
