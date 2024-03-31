package com.project.cms.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.*;

import org.springframework.core.io.WritableResource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

@Component
public class FileUploadUtil {
	
	 private BlobServiceClient blobServiceClient;
	 
	 public String containerName="files";
	 public String connectionString="DefaultEndpointsProtocol=https;AccountName=cmscontents;AccountKey=McuEbG/wP7yN3WmClbkJqxBrcFRcarfY7EamEnnNPPoHmxKCsrEOJ4kGKi/L4YBTY8w0bem1vk8x+AStcvLmww==;EndpointSuffix=core.windows.net";

	 @PostConstruct
	    public void init() {
	        blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
	    }
    public String saveNewFile(String uploadDir,
                            String fileName,
                            MultipartFile multipartFile) throws IOException {
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IOException("Could not save file");
//        }
        System.out.print("calling azure");

    	   BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
           
    	   if (!containerClient.exists()) {
               containerClient.create();
           }

           BlobClient blobClient = containerClient.getBlobClient(fileName);

            blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);

            String imageUrl = blobClient.getBlobUrl();
            System.out.print(imageUrl);
          return imageUrl;
    	
       
    }

    public String updateFile(String uploadDir,
                           String oldFileName,
                           String newFileName,
                           MultipartFile multipartFile) throws IOException {
//        Path uploadPath = Paths.get(uploadDir);
//
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path oldFilePath = uploadPath.resolve(oldFileName);
//            Path newFilePath = uploadPath.resolve(newFileName);
//            Files.deleteIfExists(oldFilePath);
//            Files.copy(inputStream, newFilePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IOException("Could not update file");
//        }
    	BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(oldFileName);

        // Upload the new content to update the blob
        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true);
        String imageUrl = blobClient.getBlobUrl();
        System.out.print(imageUrl);
        return imageUrl;

    }

    public void deleteFile(String uploadDir, String fileName) throws IOException {
//        Path uploadPath = Paths.get(uploadDir);
//        Path filePath = uploadPath.resolve(fileName);
//        Files.deleteIfExists(filePath);
    	BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(fileName);

        blobClient.delete();

    }
}
