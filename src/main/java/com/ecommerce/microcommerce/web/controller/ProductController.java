package com.ecommerce.microcommerce.web.controller;


import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.exceptions.ProduitNotFindException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;


@Api("API, Manager all operation off CRUD for product")//Swagger : Add description
@RestController//Indicat Rest controller
public class ProductController {

    @Autowired
    private ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @ApiOperation(value = "List all products")
    @GetMapping("/Produits")
    public MappingJacksonValue productList(){
        //return productDao.findAll();
        //List<Product> listProduct = productDao.findAll();
        Iterable<Product> listProduct = productDao.findAll();

        //Si on a un seule champs à ignorer
        /*SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listAllProduct = new SimpleFilterProvider().addFilter("myDynamicFilter", myFilter);*/

        //plusieurs champs à ignorer
        FilterProvider listAllProduct = new SimpleFilterProvider().addFilter("myDynamicFilter", SimpleBeanPropertyFilter.serializeAllExcept("prixAchat", "id"));
        MappingJacksonValue productsFilted = new MappingJacksonValue(listProduct);
        productsFilted.setFilters(listAllProduct);
        return  productsFilted;
    }

    @ApiOperation(value = "Home page")
    @GetMapping("/Home")
    public String homePage(){
        return "My home page";
    }

    @ApiOperation(value = "Find product by id")
    @GetMapping("/Produits/{id}")//Ajout d'un param à url
    public Product getProductById(@PathVariable int id){
        Product product = productDao.findById(id);
        if(product == null) throw new ProduitNotFindException("Product with id " + id + " Not find");
        return product;
    }

    @GetMapping("find/produits/{prixLimit}")
    public List<Product> returnProductsGreaterThan(@PathVariable int prixLimit){
        //return productDao.findByPrixGreaterThan(prixLimit);
        //Use query
        return productDao.useQueryToFindById(prixLimit);
    }

    @ApiOperation( value = "Delete product")
    @DeleteMapping("/Produits/{id}")
    public String deleteProductById(@PathVariable int id){
        productDao.deleteById(id);
        return "Product was deleted...";
    }

    @ApiOperation( value = "Update product")
    @PutMapping("/Produits/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @Valid @RequestBody Product product){
        //find product to update
        Product productToUpdate = productDao.findById(id);
        if(productToUpdate != null){
            productToUpdate.setNom(product.getNom());
            productToUpdate.setPrix(product.getPrix());
            productToUpdate.setPrixAchat(product.getPrixAchat());
            productDao.save(productToUpdate);//save modifications
            return ResponseEntity.ok(productToUpdate);
        }
        return ResponseEntity.noContent().build();
    }


    //renvoi code 200
    /*@PostMapping(value = "/Produits")
    public  void addProduct(@RequestBody Product product){
        productDao.save(product);
    }*/

    //Pesonnalisation reponse(201)
    @ApiOperation(value = "Add product to list")
    @PostMapping(value = "/Produits")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {//@valid pout la validation des inputs
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
