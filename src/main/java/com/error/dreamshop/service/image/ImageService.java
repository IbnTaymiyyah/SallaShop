package com.error.dreamshop.service.image;

import com.error.dreamshop.dto.ImageDto;
import com.error.dreamshop.model.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file , Long imageId);
    void updateImage(MultipartFile file , Long imageId);

}
