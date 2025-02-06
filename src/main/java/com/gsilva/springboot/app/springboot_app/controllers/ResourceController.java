package com.gsilva.springboot.app.springboot_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsilva.springboot.app.springboot_app.services.CustomUserDetails;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @GetMapping("/user")
    public ResponseEntity<?> user(Authentication authentication) {
        return ResponseEntity.ok().body("Bienvenido " + authentication.getName() + ". Tus permisos son: "
                + authentication.getAuthorities() + " y tu Id de usuario es: " + getCurrentUserId(authentication));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // asegurarse de que en securityConfig este la anotacion
                                                // @EnableMethodSecurity para que funcionen estas anotaciones de
                                                // autorizacion.
    @GetMapping("/admin")
    public ResponseEntity<?> admin(Authentication authentication) {
        return ResponseEntity.ok().body("Bienvenido Administrador: " + authentication.getName() + ". Tus permisos son: "
                + authentication.getAuthorities() + " y tu Id de usuario es: " + getCurrentUserId(authentication));
    }

    private Long getCurrentUserId(Authentication authentication) {
        // Verificar que el principal sea una instancia de CustomUserDetails

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        if (userId != null) {
            return userId; // Devuelve el userId
        }

        throw new IllegalArgumentException("User ID not found in the authentication details");
    }

}
