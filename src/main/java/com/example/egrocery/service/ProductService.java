package com.example.egrocery.service;

import com.example.egrocery.dto.ProductDto;
import com.example.egrocery.exception.ProductDoesNotExistException;
import com.example.egrocery.model.Category;
import com.example.egrocery.model.Product;
import com.example.egrocery.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepo productRepo;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<Product>getAllProducts() {

        List<Product> products = new ArrayList<>();
        productRepo.findAll().forEach(products::add);
        return products;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        // throw an exception if product does not exist
        if (!optionalProduct.isPresent()) {
            throw new Exception("product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
    }

    public void deleteProduct(String id) {

        productRepo.deleteById(id);
    }

    public Product findById(Integer productId) throws ProductDoesNotExistException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
                if (optionalProduct.isEmpty()) {
                    throw new ProductDoesNotExistException("product is invalid" + productId);
                }
        return optionalProduct.get();
    }



}
