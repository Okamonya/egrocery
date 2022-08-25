package com.example.egrocery.repository;

import com.example.egrocery.model.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepo extends CrudRepository<Product, Integer> {
    void deleteById(String id);
}
