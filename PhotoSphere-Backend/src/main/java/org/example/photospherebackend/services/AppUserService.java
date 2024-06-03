package org.example.photospherebackend.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AmazonS3 amazonS3;
    private final String bucketName = "photosphere-bucket";

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, AmazonS3 amazonS3) {
        this.appUserRepository = appUserRepository;
        this.amazonS3 = amazonS3;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> getUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public AppUser createUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser updateUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public void deleteUserById(Long id) {
        appUserRepository.deleteById(id);
    }

    public String uploadUserImage(MultipartFile file, Long id) {
        String fileName = generateFileName(file, id);
        File convertedFile = convertMultiPartToFile(file);
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        return fileName;
    }

    private String generateFileName(MultipartFile file, Long id) {
        return "users/"  + id + "/avatar/" + file.getOriginalFilename();
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipart file to file", e);
        }
        return convFile;
    }

    public AppUser updateUserImageUrl(Long userId, String imageUrl) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setImage(imageUrl);
            return appUserRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUserImage(Long userId) {
        Optional<AppUser> optionalUser = appUserRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            String imageUrl = user.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                deleteImageFromS3(imageUrl);
                user.setImage(null);
                appUserRepository.save(user);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private void deleteImageFromS3(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucketName, imageUrl);
    }

    public byte[] downloadUserImage(String imagePath) {
        S3Object s3Object = amazonS3.getObject(bucketName, imagePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error downloading image from S3", e);
        }
    }

    public String getMimeType(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine MIME type", e);
        }
    }
}