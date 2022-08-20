package com.example.file;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Husan Narzullayev , чт 0:22. 18.08.2022
 */
@Service

public class FileService {
    @Value(value = "${image.folder.path}")
    private String PATH;

    @Autowired
    private FileRepo repository;


    @SneakyThrows
    public HttpEntity<?> save(MultipartHttpServletRequest files) {
        Iterator<String> fileNames = files.getFileNames();
        File file1 = new File();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            if (fileName.equals("image")) {
                MultipartFile file = files.getFile(fileName);
                file1.setContentType(Objects.requireNonNull(file).getContentType());
                file1.setSize(file.getSize());
                file1.setOriginalName(file.getOriginalFilename());
                String[] split = file.getOriginalFilename().split("\\.");
                String name = UUID.randomUUID() + "." + split[split.length - 1];
                file1.setName(name);
                Path path = Paths.get(PATH + name);
                Files.copy(file.getInputStream(), path);
                file1.setImageUrl(path.toString());
            }

        }
        File file2 = repository.save(file1);
        return ResponseEntity.ok(file2);
    }

    @SneakyThrows
    public HttpEntity<?> get(String id, HttpServletResponse response) {
//        String base64String = "iVBORw0KGgoAAAANSUhEUgAAAGQAAABkAQAAAABYmaj5AAAA7ElEQVR42tXUsZHEIAwFUHk2cHZuQDO0QeaWTAN4twK3REYbzNAAyhww1ombvd1NbBHeMQS8CPERAH+MAn9YBWCBzAEGTcR13W8cZaEpoLdpiuA6tIb86JWhHnH1tq7vyk4l53MR3fu0p2pZzbJ8JXiqYtHP6H53uBAH3mKadpg0HRZhRrCZNBHzxnWIadBUbILRbK/KzkXxRhEHNpumMuLXLPOZ4IVoz4flA5LTlTzkO+CkqeU/Sgy65G59q92QptbXLIEZVhXQsblDlxZIy8iPDsmrIn5mdiWui/QCoKr2pq35CUPRf/nBPvUNct67nP2Y9j8AAAAASUVORK5CYII=";
//
//        // Convert Base64 String to File
//        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
//        Files.write(Paths.get("D:\\image.png"), decodedBytes);
        Optional<File> file = repository.findById(Long.valueOf(id));
        if (file.isEmpty()) {
            return ResponseEntity.status(409).build();
        }
        response.setHeader("Content-Disposition",
                "attachment; filename=\""
                        + file.get().getOriginalName() + "\"");

        // File ni Content Type
        response.setContentType(file.get().getContentType());

        // inputStream va response.getOutputStream berishimiz kerak, endi shu yerda unikal name qilganimizni ishlatamiz
        // Buning uchun bitta FileInputStream ochvolamiz va uni ichiga olmoqchi bo'lgan file limizni yo'lini va
        // name ni bervoramiz va pasdagi FileCopyUtils.copy(); ==> methodiga bervoramiz:

        FileInputStream inputStream = new FileInputStream(file.get().getImageUrl());
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        return ResponseEntity.status(200).build();
    }
}
