package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{
    public static List<Product> products = new ArrayList<>();
    static {
        products.add(new Product(1, new String("PC Toshiba"), 890));
        products.add(new Product(2, new String("PC HP Elite Book"), 990));
        products.add(new Product(3, new String("PC Dell"), 760));
        products.add(new Product(4, new String("PC Acer"), 670));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        for (Product product : products) {
                if(product.getId() == id) return product;
        }
        return null;
    }

    @Override
    public Product save(Product product) {
        if(product != null) products.add(product);
        return product;
    }
}
