package com.example.demo.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDTO {
    private Long id;
    private String email;
    private String role;
}
