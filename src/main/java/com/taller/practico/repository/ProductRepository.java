package com.taller.practico.repository;


import com.taller.practico.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jainer Acosta
 */

// JpaRepository<Product, Long> significa:
// - Product: la entidad que maneja
// - Long: el tipo de la clave primaria
// ¡No necesita implementación! Spring Data JPA la crea automáticamente

public interface ProductRepository extends JpaRepository<Product, Long> {

}