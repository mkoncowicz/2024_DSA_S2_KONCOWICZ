package org.example.photospherebackend.controllers;

import org.example.photospherebackend.DTOs.AppUserDTO;
import org.example.photospherebackend.DTOs.CommentDTO;
import org.example.photospherebackend.models.AppUser;
import org.example.photospherebackend.models.Comment;
import org.example.photospherebackend.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUserDTO> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable Long id) {
        Optional<AppUser> user = appUserService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(convertToDTO(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<AppUserDTO> getUserByEmail(@PathVariable String email) {
        Optional<AppUser> existingUserOpt = appUserService.getUserByEmail(email);
        if (!existingUserOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        AppUser existingUser = existingUserOpt.get();
        return ResponseEntity.ok(convertToDTO(existingUser));
    }

    @GetMapping("/get-id-of-user/{email}")
    public Optional<Integer> getIdOfUser(@PathVariable String email) {
        return appUserService.getIdOfUser(email);
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return appUserService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<AppUser> existingUserOpt = appUserService.getUserById(id);
        if (!existingUserOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        AppUser existingUser = existingUserOpt.get();

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(AppUser.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingUser, value);
            }
        });

        AppUser updatedUser = appUserService.updateUser(existingUser);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<AppUser> existingUser = appUserService.getUserById(id);
        if (existingUser.isPresent()) {
            appUserService.deleteUserImage(existingUser.get().getId());
            appUserService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadUserImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        Optional<AppUser> existingUser = appUserService.getUserById(id);
        if (existingUser.isPresent()) {
            appUserService.deleteUserImage(existingUser.get().getId());
            String imageUrl = appUserService.uploadUserImage(image, existingUser.get().getId());
            existingUser.get().setImage(imageUrl);
            appUserService.updateUserImageUrl(id, imageUrl);
            File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
            if (file.exists()) {
                file.delete();
            }
            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/delete-image")
    public ResponseEntity<String> deleteUserImage(@PathVariable Long id) {
        Optional<AppUser> existingUser = appUserService.getUserById(id);
        if (existingUser.isPresent()) {
            appUserService.deleteUserImage(existingUser.get().getId());
            return ResponseEntity.ok("Image deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/download-image")
    public ResponseEntity<byte[]> downloadUserImage(@PathVariable Long id) {
        Optional<AppUser> existingUser = appUserService.getUserById(id);
        if (existingUser.isPresent()) {
            String imagePath = existingUser.get().getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                byte[] imageBytes = appUserService.downloadUserImage(imagePath);
                String mimeType = appUserService.getMimeType(imagePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagePath.substring(imagePath.lastIndexOf('/') + 1) + "\"")
                        .body(imageBytes);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private AppUserDTO convertToDTO(AppUser user) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(user.getId());
        appUserDTO.setNickname(user.getNickname());
        appUserDTO.setFirstName(user.getFirstName());
        appUserDTO.setLastName(user.getLastName());
        appUserDTO.setEmail(user.getEmail());
        appUserDTO.setGender(user.getGender());
        appUserDTO.setDayOfBirth(user.getDayOfBirth());
        appUserDTO.setDescription(user.getDescription());
        appUserDTO.setImage(user.getImage());
        return appUserDTO;
    }

}