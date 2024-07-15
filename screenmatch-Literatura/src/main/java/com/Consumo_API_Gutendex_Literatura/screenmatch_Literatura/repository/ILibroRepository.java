package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository;

import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ILibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomasContains(String idiomas);
}
