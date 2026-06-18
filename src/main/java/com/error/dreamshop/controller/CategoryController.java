package com.error.dreamshop.controller;

import com.error.dreamshop.exceptions.AlreadyExistsException;
import com.error.dreamshop.exceptions.ResourceNotFoundException;
import com.error.dreamshop.model.Category;
import com.error.dreamshop.response.ApiResponse;
import com.error.dreamshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {

        try {
            List<Category> categoryList = categoryService.getCategories();
            return  ResponseEntity.ok(new ApiResponse("Found!",categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!",INTERNAL_SERVER_ERROR));
        }

    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {

        try {
            Category category = categoryService.addCategory(name);
            return  ResponseEntity.ok(new ApiResponse("Added!",category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id) {

        try {
            Category category = categoryService.getCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found!",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {

        try {
            Category category = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new ApiResponse("Found!",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {

        try {
            categoryService.deleteCategory(id);
            return  ResponseEntity.ok(new ApiResponse("Found!",null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id ,
                                                      @RequestBody Category name) {

        try {
           Category category = categoryService.updateCategory(name, id);
            return  ResponseEntity.ok(new ApiResponse("Found!",category));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }




}
