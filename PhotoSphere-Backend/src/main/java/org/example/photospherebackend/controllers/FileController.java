package org.example.photospherebackend.controllers;

import com.amazonaws.services.s3.model.S3Object;
import org.example.photospherebackend.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private S3Service s3Service;

    @GetMapping
    public String listFiles(Model model) {
        List<String> files = s3Service.listFiles();
        model.addAttribute("files", files);
        return "fileList";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            s3Service.uploadFile(file);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "File upload failed: " + file.getOriginalFilename());
        }
        return "redirect:/files";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String fileName) throws IOException {
        S3Object s3Object = s3Service.downloadFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType(s3Object.getObjectMetadata().getContentType()))
                .contentLength(s3Object.getObjectMetadata().getContentLength())
                .body(new InputStreamResource(s3Object.getObjectContent()));
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable String fileName) throws IOException {
        S3Object s3Object = s3Service.downloadFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(s3Object.getObjectMetadata().getContentType()))
                .contentLength(s3Object.getObjectMetadata().getContentLength())
                .body(new InputStreamResource(s3Object.getObjectContent()));
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileName) {
        s3Service.deleteFile(fileName);
        return noContent().build();
    }
}

