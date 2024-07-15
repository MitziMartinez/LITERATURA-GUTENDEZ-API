package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos (
        @JsonAlias("results") List<DatosLibro> resultados
){
}
