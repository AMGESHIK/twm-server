package com.example.twm;

import com.example.twm.service.impl.ProfileFilesStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwmApplication implements CommandLineRunner {

    @Resource
    ProfileFilesStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(TwmApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }


}
