package com.LocadoraFilmes.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.LocadoraFilmes.Exception.CustomSuccessHandler;
import com.LocadoraFilmes.Service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity


public class SecurityConfig {

  private final CustomSuccessHandler successHandler;

  @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

      @Autowired
    private UserDetailsServiceImpl userDetailsService;

  @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   public SecurityConfig(CustomSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/adicionar/**", "/alterar/**", "/excluir/**").hasRole("ADMIN")
                .requestMatchers("/buscarfilmes/**", "/", "/busca").hasRole("USER")
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler) // 🔑 Aqui usamos o handler para decidir o redirect
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        // Habilita o console do H2
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Desabilita CSRF só para o H2
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }

      @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


       @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
}
