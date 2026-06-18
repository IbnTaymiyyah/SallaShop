package com.error.dreamshop.repository;

import com.error.dreamshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Imagerepository extends JpaRepository<Image,Long> {
}
