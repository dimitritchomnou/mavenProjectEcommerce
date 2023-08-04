package com.ecommerce.microcommerce.web.controller;


import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.dao.ProductDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//Indicat Rest controller
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }


    @GetMapping("/Produits")
    public List<Product> productList(){
        return productDao.findAll();
    }

    @GetMapping("/Home")
    public String homePage(){
        return "My home page";
    }

    @GetMapping("/Produits/{id}")//Ajout d'un param à url
    public Product getProductById(@PathVariable int id){
        return productDao.findById(id);
    }
}
