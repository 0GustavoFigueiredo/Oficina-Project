package com.br.oficina.UniWolks.service;

import com.br.oficina.UniWolks.model.Client;
import com.br.oficina.UniWolks.model.ServiceOrder;
import com.br.oficina.UniWolks.repository.ClientRepository;
import com.br.oficina.UniWolks.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, ClientRepository clientRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.clientRepository = clientRepository;
    }

    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado pelo id: " + clientId));

        serviceOrder.setClient(client);
        serviceOrder.setIsComplete(false);
        if(serviceOrder.getEntryDate() == null) {
            serviceOrder.setEntryDate(LocalDate.now());
        }

        return serviceOrderRepository.save(serviceOrder);
    }

    public ServiceOrder markAsComplete(Long orderId) {
        ServiceOrder order = serviceOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada pelo id: " + orderId));
        order.setIsComplete(true);
        return serviceOrderRepository.save(order);
    }

    public List<ServiceOrder> findOrdersByClientCpfCnpj(String cpfCnpj) {
        return serviceOrderRepository.findByClientCpfCnpj(cpfCnpj);
    }

    public List<ServiceOrder> searchOrdersByClientName(String name) {
        return serviceOrderRepository.findByClientNameContainingIgnoreCase(name);
    }

    public List<ServiceOrder> searchOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return serviceOrderRepository.findByEntryDateBetween(startDate, endDate);
    }
}