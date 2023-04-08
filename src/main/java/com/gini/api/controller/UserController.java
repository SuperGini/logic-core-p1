package com.gini.api.controller;

import com.gini.api.validation.UserValidation;
import com.gini.dto.request.user.LoginUserRequest;
import com.gini.dto.request.user.CreateUserRequest;
import com.gini.dto.response.UserResponse;
import com.gini.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/v1",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse loginUser(@RequestBody @Valid @UserValidation LoginUserRequest userRequest){
        return userService.loginUser(userRequest);
    }

//    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public File2 saveImage(@RequestPart File2 imageInfo, @RequestPart MultipartFile imageFile) throws IOException {
//
//        File2 file2 = new File2();
//        file2.setFileInBytes(imageFile.getBytes());
//        file2.setName(imageFile.getName());
//        file2.setContentType(imageFile.getContentType());
//
//
//
//        return new File2();
//    }


}
