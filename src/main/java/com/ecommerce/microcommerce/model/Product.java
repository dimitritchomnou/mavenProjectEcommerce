package com.ecommerce.microcommerce.model;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

//@JsonIgnoreProperties(value = {"id", "prixAchat"})//permet de cacher les infos à ne pas exposer dans notre json
//@JsonFilter("myDynamicFilter")//Gestion des infos à cacher de façon dynamique
@Entity
public class Product {
    @Id//primary key
    private int id;
    @Size(min = 3, max = 25)//Validation input
    private String nom;
    @Min(value = 1)
    private int prix;
    //Info non exposée
    @Min(value = 1)
    private int prixAchat;


    //Constructeur vide
    public Product() {
    }

    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
