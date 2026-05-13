/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.taller.practico.service;




import com.taller.practico.entity.Product;
import com.taller.practico.exception.ProductNotFoundException;
import com.taller.practico.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jainer Acosta
 */
@Service  // Indica que esta clase contiene lógica de negocio
public class ProductService {

    @Autowired
    private ProductRepository repository;  // Para acceder a la BD

    // Retorna una página de productos (no todos juntos, sino fragmentados)
    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        // Busca por ID. Si no existe, lanza la excepción personalizada
        return repository.findById(id)
                .orElseThrow(() -> 
                    new ProductNotFoundException("Producto no encontrado"));
    }

    public Product createProduct(Product product) {
        // createdAt se asigna automáticamente en la entidad con @PrePersist
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        // Primero verifica que existe (lanza excepción si no)
        Product existing = getProductById(id);

        // Actualiza solo los campos permitidos
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCategory(product.getCategory());
        // Nota: createdAt NO se modifica para mantener la fecha original

        // Guarda los cambios
        return repository.save(existing);
    }

    public void deleteProduct(Long id) {
        // Verifica existencia antes de eliminar
        Product product = getProductById(id);
        repository.delete(product);
    }
}