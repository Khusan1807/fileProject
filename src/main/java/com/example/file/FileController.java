package com.example.file;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Husan Narzullayev , чт 0:19. 18.08.2022
 */
@RestController
@RequestMapping(value = "/file")

public class FileController {

    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping(value = "/save")
    public HttpEntity<?> saveFile(MultipartHttpServletRequest files) {
        return service.save(files);
    }


    @GetMapping(value = "/get/{id}")
    public HttpEntity<?> getFile(HttpServletResponse response, @PathVariable String id) {
        return service.get(id, response);
    }
}
