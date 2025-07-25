package com.br.oficina.UniWolks.controller;

import com.br.oficina.UniWolks.model.Client; // NEW IMPORT
import com.br.oficina.UniWolks.model.ServiceOrder;
import com.br.oficina.UniWolks.repository.ClientRepository; // NEW IMPORT
import com.br.oficina.UniWolks.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;
    private final ClientRepository clientRepository; // 1. ADD THE REPOSITORY DEPENDENCY

    @Autowired
    // 2. UPDATE THE CONSTRUCTOR TO INJECT IT
    public ServiceOrderController(ServiceOrderService serviceOrderService, ClientRepository clientRepository) {
        this.serviceOrderService = serviceOrderService;
        this.clientRepository = clientRepository;
    }

    // == PUBLIC ENDPOINTS (for clients) ==

    @GetMapping("/check-status")
    public String showStatusCheckForm() {
        // Returns the name of the HTML file: "check-status-form.html"
        return "check-status-form";
    }

    @PostMapping("/check-status")
    public String checkOrderStatus(@RequestParam String cpfCnpj, Model model) {
        // @RequestParam gets the "cpfCnpj" value from the submitted form
        List<ServiceOrder> orders = serviceOrderService.findOrdersByClientCpfCnpj(cpfCnpj);
        // Add the list of orders to the model so the HTML page can access it
        model.addAttribute("orders", orders);
        // Returns the name of the results page: "status-results.html"
        return "status-results";
    }

    // == ADMIN ENDPOINTS ==
    @GetMapping("/admin/orders/new")
    public String showNewOrderForm(Model model) {
        // 3. FETCH ALL CLIENTS FROM THE DATABASE
        List<Client> clients = clientRepository.findAll();

        // 4. ADD THE LIST OF CLIENTS TO THE MODEL
        model.addAttribute("clients", clients);

        // This line remains the same, preparing an empty object for the form
        model.addAttribute("serviceOrder", new ServiceOrder());

        // Returns "templates/admin/order-form.html"
        return "admin/order-form";
    }

    @PostMapping("/admin/orders")
    public String createServiceOrder(@ModelAttribute ServiceOrder serviceOrder, @RequestParam Long clientId) {
        serviceOrderService.createServiceOrder(serviceOrder, clientId);
        return "redirect:/admin/dashboard"; // Assuming you have a dashboard page
    }
}