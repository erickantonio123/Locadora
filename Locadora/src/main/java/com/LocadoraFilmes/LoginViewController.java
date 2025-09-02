package com.LocadoraFilmes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginViewController {
    
    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Credenciais inválidas. Por favor, tente novamente.");
        }
        
        if (logout != null) {
            model.addAttribute("message", "Você foi desconectado com sucesso.");
        }
        
        return "login"; // login.html em templates
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // página para acesso negado
    }
}