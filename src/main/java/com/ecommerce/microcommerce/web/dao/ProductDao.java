package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {//Product comme entité et le type de l'id

    //List<Product> findAll();
    Product findById(int id);
    Product save(Product product);
    //List<Product> findByPrixGreaterThan(int prixLimit);
    //Product deleteById(int id);
    //When you want to use query
    @Query(value = "SELECT * FROM Product p WHERE p.prix > :prixLimit", nativeQuery = true)
    List<Product> useQueryToFindById(@Param("prixLimit") int prix);
}
