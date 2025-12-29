package com.LocadoraFilmes.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LocadoraFilmes.model.Plataforma;
  @Repository
public interface PlataformaRepository  extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNome(String nome);
}



   