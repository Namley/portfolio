package com.namley.portfolio.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @Value("${app.upload.dir:${user.dir}/src/main/resources/static/uploaded-images}")
    private String uploadFolder;

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Path path = Paths.get(uploadFolder + "/" + filename);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException exception) {
            System.out.println("url broken !!!!!!!!!");
            return ResponseEntity.notFound().build();
        }

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        } else if (!resource.isReadable()) {
            System.out.println("resource not readable!!!!!!!!!");
            return ResponseEntity.notFound().build();
        }
        MediaType mediaType;
        switch (getExtension(resource.getFilename().toLowerCase())) {
            case "jpeg", "jpg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
            default:
                System.out.println("media type wrong or smth: " + getExtension(resource.getFilename().toLowerCase()));
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }

    @GetMapping("/proxy-image")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String imageUrl) {
        try {
            // Create a RestTemplate to fetch the external image
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

            // Copy the headers from the original response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(response.getHeaders().getContentType());

            // Return the image data with appropriate headers
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index >= 0) {
            return filename.substring(index + 1);
        }
        return "";
    }
}
