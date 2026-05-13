/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.taller.practico.exception;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author Marilyn Acosta
 */
@ControllerAdvice  // Intercepta excepciones de TODOS los controladores
public class GlobalExceptionHandler {

    // Este método se ejecuta cuando ocurre una ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException ex) {
        
        // Crea un mapa (objeto JSON) con el mensaje de error
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        // Retorna: { "message": "Producto no encontrado" } con código HTTP 404
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
