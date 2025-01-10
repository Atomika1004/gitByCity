package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Attachment;
import com.atomika.gitByCity.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/base/image")
public class ImageDataController {
    @Autowired
    private AttachmentService attachmentService;

//    @PostMapping("{id}")
//    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {
//        Attachment response = attachmentService.uploadImage(file, id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @GetMapping("/info/{name}")
//    public ResponseEntity<?>  getImageInfoByName(@PathVariable("name") String name){
//        Attachment image = attachmentService.getInfoByImageByName(name);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(image);
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<?>  getImageByName(@PathVariable("name") String name){
//        byte[] image = attachmentService.getImage(name);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(image);
//    }

    @DeleteMapping("delete/{id}")
    public Long deleteImage(@PathVariable("id") Long id){
        return attachmentService.delete(id);
    }

    @GetMapping()
    public List<Attachment> getAllImages(){
        return attachmentService.findAll();
    }
}
