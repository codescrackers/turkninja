package com.yazilimokulu.mvc.services;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service("photoService")
public class PhotoServiceImpl implements PhotoService {

    @Value("${uploading.dirpath}")
    private String uploadingDirPath;

    @Autowired
    private FileNameGenerator fileNameGenerator;

    public PhotoServiceImpl() {
        ImageIO.setUseCache(false);
    }

    public final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    @Override
    public UploadedPhotoInfo upload(MultipartFile file) throws IOException, UnsupportedFormatException {
        String fileName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();

        if (!SUPPORTED_EXTENSIONS.contains(ext))
            throw new UnsupportedFormatException(fileName);

        String name = fileNameGenerator.getFileName(fileName, "photo");
        String imageName = name + "_big" + "." + ext;

        BufferedImage image = ImageIO.read(file.getInputStream());

        new File(uploadingDirPath).mkdirs();

        ImageIO.write(image, ext, new File(uploadingDirPath + imageName));

        return new UploadedPhotoInfo(imageName);
    }


    public FileNameGenerator getFileNameGenerator() {
        return fileNameGenerator;
    }

    public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
    }
}
