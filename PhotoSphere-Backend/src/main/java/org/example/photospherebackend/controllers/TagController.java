package org.example.photospherebackend.controllers;

import org.example.photospherebackend.DTOs.TagDTO;
import org.example.photospherebackend.models.Tag;
import org.example.photospherebackend.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> getAllTags() {
        return tagService.getAllTags().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.getTagById(id);
        if (tag.isPresent()) {
            return ResponseEntity.ok(convertToDTO(tag.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        Tag tag = convertToEntity(tagDTO);
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok(convertToDTO(createdTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTagById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TagDTO> getTagByName(@PathVariable String name) {
        Optional<Tag> tag = tagService.getTagByName(name);
        if (tag.isPresent()) {
            return ResponseEntity.ok(convertToDTO(tag.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    private Tag convertToEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }
}
