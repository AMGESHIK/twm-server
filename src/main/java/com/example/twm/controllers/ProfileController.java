package com.example.twm.controllers;

import com.example.twm.domain.User;
import com.example.twm.jwt.AuthService;
import com.example.twm.requests.ProfileRequest;
import com.example.twm.responses.ProfileResponse;
import com.example.twm.responses.files.FileInfo;
import com.example.twm.service.impl.ProfileFilesStorageService;
import com.example.twm.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("user/profile")
public class ProfileController {
    private final ProfileFilesStorageService storageService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        User user = userService.loadUserByUsername(username);
        return ResponseEntity.ok(ProfileResponse
                .builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .about(user.getAbout())
                .address(user.getAddress())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .build());
    }

    @PostMapping ()
    public ResponseEntity<?> setProfile(@RequestBody ProfileRequest profileRequest) {
        User user = userService.findById(authService.getAuthInfo().getId()).get();
        user.setEmail(profileRequest.getEmail());
        user.setName(profileRequest.getName());
        user.setAbout(profileRequest.getAbout());
        user.setAddress(profileRequest.getAddress());
        user.setMobile(profileRequest.getMobile());
        userService.saveUser(user);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/photo")
    public ResponseEntity<?> getProfilePhoto(@RequestParam Long id) throws IOException {
        var user = userService.findById(id);
        if (user.isPresent()) {
            Resource file = storageService.load(user.get().getPhoto());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(Files.probeContentType(file.getFile().toPath())))
                    .body(file);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photo of this user not found");
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ProfileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
