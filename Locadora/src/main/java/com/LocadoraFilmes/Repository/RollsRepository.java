package com.LocadoraFilmes.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.LocadoraFilmes.model.Role;

public interface RollsRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}