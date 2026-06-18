package com.error.dreamshop.request;

import com.error.dreamshop.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProduct {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private int inventory;
    private BigDecimal price;
    private Category category;
}
