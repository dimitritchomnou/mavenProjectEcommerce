package com.ecommerce.microcommerce.web.controller;


import com.ecommerce.microcommerce.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController//Indicat Rest controller
public class ProductController {

    @GetMapping("/Produits")//url permettand d'atteindre la methode et le verbe Get
    public String listProduits(){
        return "Liste de nos produits Test";
    }

    @GetMapping("/Home")
    public String homePage(){
        return "My home page";
    }

    /*@GetMapping("/Produits/{id}")//Ajout d'un param à url
    public String getProductById(@PathVariable int id){
        return "You have requested the product id : " + id;
    }*/

    @GetMapping("/Produits/{id}")//Ajout d'un param à url
    public Product getProductById(@PathVariable int id){
        return new Product(1, new String("PC Elite Book"), 890);
    }
}
