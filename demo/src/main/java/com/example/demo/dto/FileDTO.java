package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private String uuid;
    private String saveDir;
    private String fileName;
    private int fileType;
    private long bno;
    private long fileSize;
    private LocalDateTime regDate, modDate;
}