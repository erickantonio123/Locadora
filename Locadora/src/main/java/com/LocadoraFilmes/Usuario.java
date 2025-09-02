package com.LocadoraFilmes;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import java.util.Collection;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Usuario() {}

    public Usuario(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Métodos obrigatórios do UserDetails:

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> (GrantedAuthority) () -> role.getNome())
            .toList();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode personalizar
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode personalizar
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode personalizar
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode personalizar
    }
}