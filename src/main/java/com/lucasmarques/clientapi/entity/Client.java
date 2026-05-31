package com.lucasmarques.clientapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String cpf;

    @NotNull
    @Column(nullable = false)
    private LocalDate birthDate;

}
