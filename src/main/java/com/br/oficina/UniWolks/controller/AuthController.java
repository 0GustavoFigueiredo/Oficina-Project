// In AuthController.java

package com.br.oficina.UniWolks.controller;

import com.br.oficina.UniWolks.model.Client;
import com.br.oficina.UniWolks.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // NEW IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


@Controller
public class AuthController {

    private final ClientService clientService; // 1. INJECT ClientService

    @Autowired
    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String showLoginPage() { return "login"; }

    @GetMapping("/")
    public String showHomePage() { return "redirect:/check-status"; }

    // 2. ADD METHOD TO SHOW THE REGISTRATION FORM
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Create an empty client object to bind to the form
        model.addAttribute("client", new Client());
        return "register"; // Renders register.html
    }

    // 3. ADD METHOD TO PROCESS THE REGISTRATION
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Client client) {
        // The @ModelAttribute annotation automatically binds form data to the client object
        clientService.registerClient(client);
        // Redirect to the login page with a success message
        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication) {
        // Pega os perfis (authorities) do usuário logado
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            // Se o usuário for um ADMIN...
            if (auth.getAuthority().equals("ROLE_ADMIN")) {
                // ...redirecione para a página de criar nova ordem de serviço.
                return "redirect:/admin/orders/new";
            }
        }
        return "redirect:/check-status";
    }
}