package com.br.oficina.UniWolks.repository;

import com.br.oficina.UniWolks.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

    List<ServiceOrder> findByClientNameContainingIgnoreCase(String name);


    List<ServiceOrder> findByEntryDateBetween(LocalDate startDate, LocalDate endDate);


    List<ServiceOrder> findByClientCpfCnpj(String cpfCnpj);
}