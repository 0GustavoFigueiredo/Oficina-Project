package com.br.oficina.UniWolks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String cpfCnpj;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING) // Tells JPA to store the role as a String in the DB
    @Column(nullable = false)
    private Role profile;

}
