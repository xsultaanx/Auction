package org.auction.common.service.image;

import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import org.auction.common.config.MinioConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final MinioConfig minioConfig;

    @Override
    public String uploadImage(MultipartFile file,String bucketName,String minioUrl) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            minioConfig.createMinioClient().putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки файла в MinIO", e);
        }
    }

}
