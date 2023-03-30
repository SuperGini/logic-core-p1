package com.gini.controller;

import com.gini.dto.request.UserRequest;
import com.gini.model.entities.File2;
import com.gini.model.entities.User;
import com.gini.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserRequest userRequest){
       return userService.createUser(userRequest);

    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public File2 saveImage(@RequestPart File2 imageInfo, @RequestPart MultipartFile imageFile) throws IOException {

        File2 file2 = new File2();
        file2.setFileInBytes(imageFile.getBytes());
        file2.setName(imageFile.getName());
        file2.setContentType(imageFile.getContentType());



        return new File2();
    }





}
