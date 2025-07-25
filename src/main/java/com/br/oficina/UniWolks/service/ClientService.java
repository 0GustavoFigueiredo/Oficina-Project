package com.br.oficina.UniWolks.service;

import com.br.oficina.UniWolks.model.Client;
import com.br.oficina.UniWolks.model.Role; // Make sure Role is imported
import com.br.oficina.UniWolks.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client registerClient(Client client) {
        String encryptedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encryptedPassword);

        // FIX #1 & #2: Check for null and set the role using the enum constant.
        if (client.getProfile() == null) {
            client.setProfile(Role.ROLE_USER);
        }

        return clientRepository.save(client);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByCpfCnpj(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with CPF/CNPJ: " + username));

        return User.builder()
                .username(client.getCpfCnpj())
                .password(client.getPassword())
                // FIX #3: First, get the enum's name as a String with .name(), then call .replace().
                .roles(client.getProfile().name().replace("ROLE_", ""))
                .build();
    }
}