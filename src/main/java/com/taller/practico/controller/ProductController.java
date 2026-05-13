/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.taller.practico.controller;

import com.taller.practico.dto.ProductRequestDTO;
import com.taller.practico.entity.Product;
import com.taller.practico.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Jainer Acosta
 */
// Indica que esta clase es un controlador REST (responde a peticiones HTTP)
@RestController
// Todas las rutas de este controlador empiezan con /products
@RequestMapping("/products")
public class ProductController {

    // Inyección de dependencia: Spring crea automáticamente el Service y lo asigna aquí
    @Autowired
    private ProductService service;

    // @Operation es para documentar en Swagger/OpenAPI
    @Operation(summary = "Listar productos")
    @GetMapping  // Responde a GET /products
    public Page<Product> getAllProducts(
            // Parámetros opcionales de la URL: ?page=0&size=5
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        // Crea un objeto de paginación con número de página y tamaño
        Pageable pageable = PageRequest.of(page, size);

        
        return service.getAllProducts(pageable);
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")  // Ejemplo: GET /products/1
    public Product getProductById(
            @PathVariable Long id) {  // Toma el {id} de la URL
        return service.getProductById(id);
    }

    @Operation(summary = "Crear producto")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        // Convertir DTO a Entidad Product
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());

        // Guardar el producto
        Product savedProduct = service.createProduct(product);

        // Crear respuesta con mensaje y producto creado
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Producto creado exitosamente");
        response.put("producto", savedProduct);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO productDTO) {

        // Convertir DTO a Entidad
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(productDTO.getCategory());

        // Actualizar el producto
        Product updatedProduct = service.updateProduct(id, product);

        // Crear respuesta con mensaje y producto actualizado
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Producto actualizado exitosamente");
        response.put("producto", updatedProduct);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        // Primero obtener el producto (antes de eliminarlo)
        Product deletedProduct = service.getProductById(id);

        // Luego eliminarlo
        service.deleteProduct(id);

        // Crear un mapa con el mensaje y los datos del producto eliminado
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Se ha eliminado el producto exitosamente");
        response.put("productoEliminado", deletedProduct);

        // Retornar el mensaje junto con el producto
        return ResponseEntity.ok(response);
    }
}