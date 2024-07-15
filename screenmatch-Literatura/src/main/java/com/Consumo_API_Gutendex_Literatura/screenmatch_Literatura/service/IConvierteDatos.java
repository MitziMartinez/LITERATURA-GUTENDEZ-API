package com.Consumo_API_Gutendex_Literatura.screenmatch_Literatura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos (String json, Class<T> clase);
}
