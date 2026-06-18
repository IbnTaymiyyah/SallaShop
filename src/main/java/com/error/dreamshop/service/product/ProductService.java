package com.error.dreamshop.service.product;

import com.error.dreamshop.model.Product;
import com.error.dreamshop.request.AddProductRequest;
import com.error.dreamshop.request.UpdateProduct;

import java.util.List;

public interface ProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);
    Product updateProduct(UpdateProduct product , Long productId);
    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductByName(String name);
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByBrandAndName(String brand, String name);

    Long countProductByBrandAndName(String brand, String name);




}