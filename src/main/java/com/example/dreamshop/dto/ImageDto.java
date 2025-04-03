package com.example.dreamshop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ImageDto {
    private Long id;
    private String fileName;
    private String downloadUrl;

}