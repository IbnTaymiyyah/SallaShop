package com.error.dreamshop.service.image;

import com.error.dreamshop.dto.ImageDto;
import com.error.dreamshop.exceptions.ResourceNotFoundException;
import com.error.dreamshop.model.Image;
import com.error.dreamshop.model.Product;
import com.error.dreamshop.repository.Imagerepository;
import com.error.dreamshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final Imagerepository imagerepository;
    private final ProductService  productService;

    @Override
    public Image getImageById(Long id) {
        return imagerepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(Long id) {

        imagerepository.findById(id).ifPresentOrElse(imagerepository::delete , () -> {
            throw new ResourceNotFoundException("Image not found" + id);
        });

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> imageDtoList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imagerepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl  + savedImage.getId());
                imagerepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDtoList.add(imageDto);

            }catch (IOException | SQLException e){
                throw  new RuntimeException(e.getMessage());
            }
        }
        return imageDtoList;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);

        try {
            image.setFileType(file.getOriginalFilename());
            image.setName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));

            imagerepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
