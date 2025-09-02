package com.LocadoraFilmes;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;

import java.util.List;
@Repository
public interface LocadoraRepository extends JpaRepository<Locadora, Long> {
  Optional<Locadora> findByNomeIgnoreCase(String nome);
  Optional<Locadora> findByNome(String nome);
  Page<Locadora> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    
  List<Locadora> findByNomeContainingIgnoreCase(String nome);

 

}
