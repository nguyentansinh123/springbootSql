package com.example.dreamshop.service.image;

import com.example.dreamshop.dto.ImageDto;
import com.example.dreamshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(long id);
    void deleteImageById(long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
