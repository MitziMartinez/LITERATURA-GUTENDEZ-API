package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.repository;

import com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface IAutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContains(String nombreAutor);

    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeMuerteGreaterThanEqual(Integer fechaDeNacimiento, Integer fechaDeMuerte);
}
