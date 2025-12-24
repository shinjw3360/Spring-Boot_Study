package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;


@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private Long bno;
    private String title;
    private String writer;
    private String content;
    private int readCount;
    private int cmtQty;
    private int fileQty;
    private LocalDateTime regDate, modDate;

}
