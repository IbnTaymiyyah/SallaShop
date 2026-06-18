package com.error.dreamshop.service.category;

import com.error.dreamshop.model.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long categoryId);
    Category getCategoryByName(String categoryName);
    List<Category> getCategories();

    Category addCategory(Category category);
    Category updateCategory(Category category , Long id);
    void deleteCategory(Long categoryId);

}
