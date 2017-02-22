package com.yazilimokulu.mvc.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    UploadedPhotoInfo upload(MultipartFile file) throws IOException, UnsupportedFormatException;
}
