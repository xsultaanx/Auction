package org.auction.common.service.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Service
public interface ImageService {
    String uploadImage(MultipartFile file,String bucketName,String minioUrl);
}
