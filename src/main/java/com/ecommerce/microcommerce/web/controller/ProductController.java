package com.ecommerce.microcommerce.web.controller;


import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController//Indicat Rest controller
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @GetMapping("/Produits")
    public MappingJacksonValue productList(){
        //return productDao.findAll();
        List<Product> listProduct = productDao.findAll();

        //Si on a un seule champs à ignorer
        /*SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listAllProduct = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);*/

        //plusieurs champs à ignorer
        FilterProvider listAllProduct = new SimpleFilterProvider().addFilter("myDynamicFilter", SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "id"));
        MappingJacksonValue productsFilted = new MappingJacksonValue(listProduct);
        productsFilted.setFilters(listAllProduct);
        return  productsFilted;
    }

    @GetMapping("/Home")
    public String homePage(){
        return "My home page";
    }

    @GetMapping("/Produits/{id}")//Ajout d'un param à url
    public Product getProductById(@PathVariable int id){
        return productDao.findById(id);
    }

    //renvoi code 200
    /*@PostMapping(value = "/Produits")
    public  void addProduct(@RequestBody Product product){
        productDao.save(product);
    }*/

    //Pesonnalisation reponse(201)
    @PostMapping(value = "/Produits")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product productToAdd = productDao.save(product);
        if (Objects.isNull(productToAdd)) {
            return ResponseEntity.noContent().build();//return 202 code
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productToAdd.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
