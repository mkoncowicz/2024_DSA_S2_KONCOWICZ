package org.example.photospherebackend.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.example.photospherebackend.models.Post;
import org.example.photospherebackend.repositories.PostRepository;
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
public class PostService {

    private final PostRepository postRepository;
    private final AmazonS3 amazonS3;
    private final String bucketName = "photosphere-bucket";

    @Autowired
    public PostService(PostRepository postRepository, AmazonS3 amazonS3) {
        this.postRepository = postRepository;
        this.amazonS3 = amazonS3;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public String uploadPostImage(MultipartFile file, Long postId, Long userId) throws IOException {
        String fileName = generateFileName(file, postId, userId);
        File convertedFile = convertMultiPartToFile(file);
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        return fileName;
    }

    private String generateFileName(MultipartFile file, Long postId, Long userId) {
        return "users/" + userId + "/posts/" + postId + "/" + file.getOriginalFilename();
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

    public Post updatePostImageUrl(Long postId, String imageUrl) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setImageUrl(imageUrl);
            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public void deletePostImage(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            String imageUrl = post.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                deleteImageFromS3(imageUrl);
                post.setImageUrl(null);
                postRepository.save(post);
            }
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    private void deleteImageFromS3(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucketName, imageUrl);
    }

    public byte[] downloadPostImage(String imagePath) {
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

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }
}