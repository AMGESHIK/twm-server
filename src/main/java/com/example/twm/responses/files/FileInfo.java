package com.example.twm.responses.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class FileInfo {
    private String name;
    private String url;
}
