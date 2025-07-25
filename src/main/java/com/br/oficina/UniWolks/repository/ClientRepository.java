package com.br.oficina.UniWolks.repository;

import com.br.oficina.UniWolks.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpfCnpj(String cpfCnpj);
}
