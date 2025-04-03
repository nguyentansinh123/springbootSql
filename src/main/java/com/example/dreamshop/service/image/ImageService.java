package com.example.dreamshop.service.image;

import com.example.dreamshop.dto.ImageDto;
import com.example.dreamshop.exceptions.ResourceNotFoundException;
import com.example.dreamshop.model.Image;
import com.example.dreamshop.model.Product;
import com.example.dreamshop.repository.ImageRepository;
import com.example.dreamshop.service.product.IProductService;
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
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Image not found with id " + id));
    }

    @Override
    public void deleteImageById(long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();

        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image saveImage = imageRepository.save(image);

                saveImage.setDownloadUrl(buildDownloadUrl+ saveImage.getId());
                imageRepository.save(saveImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(saveImage.getId());
                imageDto.setFileName(saveImage.getFileName());
                imageDto.setDownloadUrl(saveImage.getDownloadUrl());

                savedImageDto.add(imageDto);

            }catch (IOException | SQLException e){
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
