package com.br.oficina.UniWolks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ordens_servicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate entryDate;
    @Column(nullable = false)
    private Boolean isComplete = false;
    private BigDecimal totalValue;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Client client;

}
