package com.error.dreamshop.service.product;

import com.error.dreamshop.exceptions.ProductNotFoundException;
import com.error.dreamshop.exceptions.ResourceNotFoundException;
import com.error.dreamshop.model.Category;
import com.error.dreamshop.model.Product;
import com.error.dreamshop.repository.CategoryRepository;
import com.error.dreamshop.repository.ProductRepository;
import com.error.dreamshop.request.AddProductRequest;
import com.error.dreamshop.request.UpdateProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request ) {

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() ->{
                        Category newCategory = new Category(request.getCategory().getName());
                return categoryRepository.save(newCategory);

                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));

    }

    private Product createProduct(AddProductRequest request , Category category) {

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getInventory(),
                request.getPrice(),
                category

        );
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found!"));
    }

    @Override
    public Product updateProduct(UpdateProduct request, Long productId) {
       return productRepository.findById(productId)
               .map(product -> updateProduct(product,request))
               .map(productRepository :: save)
               .orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));

    }

    private Product updateProduct(Product product, UpdateProduct request ) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setInventory(request.getInventory());
        product.setPrice(request.getPrice());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);

        return product;

    }


    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete , () -> {throw  new ProductNotFoundException("Product Not Found! to delete");});

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
