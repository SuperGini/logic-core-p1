package com.gini.api.controller;

import com.gini.api.validation.UserValidation;
import com.gini.dto.request.folder.FolderInfoRequest;
import com.gini.dto.request.user.LoginUserRequest;
import com.gini.dto.request.user.CreateUserRequest;
import com.gini.dto.response.user.UserResponse;
import com.gini.services.FileService;
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
import java.util.List;

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
    private final FileService fileService;

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

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveImage(@RequestPart FolderInfoRequest folderInfo, @RequestPart List<MultipartFile> imageFile) throws IOException {


        fileService.saveFilesToFolder(folderInfo, imageFile);


//        Path mainDirectory = Paths.get("directory");
//
//        if(!Files.exists(mainDirectory)){
//            Files.createDirectory(mainDirectory);
//        }
//
//        Path createSubDirectory = Paths.get("directory","projectDirectory");
//
//        Files.createDirectories(createSubDirectory);
//
//
//
//        imageFile
//                .forEach(x -> {
//                    File2 file2 = new File2();
//                    try {
//                        file2.setFileInBytes(x.getBytes());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    file2.setName(x.getOriginalFilename());
//                    file2.setContentType(x.getContentType());
//
//                    try {
//                        Path createFile = createSubDirectory.resolve(x.getOriginalFilename());
//                        Files.write(createFile, x.getBytes(), StandardOpenOption.CREATE);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//
//
//        File2 file2 = new File2();
//        file2.setFileInBytes(imageFile.getBytes());
//        file2.setName(imageFile.getOriginalFilename());
//        file2.setContentType(imageFile.getContentType());

 //       file2Repository.save(file2);

//        Path mainDirectory = Paths.get("directory");
//
//        if(!Files.exists(mainDirectory)){
//            Files.createDirectory(mainDirectory);
//        }
//
//        Path createSubDirectory = Paths.get("directory","projectDirectory");
//
//        Files.createDirectories(createSubDirectory);
//
//        Path createFile = createSubDirectory.resolve(imageFile.getOriginalFilename());

    //    imageFile.transferTo(createFile);


    //    Files.write(createFile, imageFile.getBytes(), StandardOpenOption.CREATE);


   //     return file2;
    }


}
