package com.LocadoraFilmes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RollsRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}