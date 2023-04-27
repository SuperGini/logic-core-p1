package com.gini.config.folder;

import com.gini.error.error.LogicCoreException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "folder")
public class MainFolderConfig {

    private String mainDirectory;

    @PostConstruct
    private void postConstruct(){
        Path mainDirectoryPath = Paths.get(getMainDirectory());

        if(!Files.exists(mainDirectoryPath)){
            try {
                Files.createDirectory(mainDirectoryPath);
            } catch (IOException e) {
                log.error("Can not create main directory: {}", mainDirectory);
                throw new LogicCoreException("Can not create main directory", e);
            }
        }
    }


}
