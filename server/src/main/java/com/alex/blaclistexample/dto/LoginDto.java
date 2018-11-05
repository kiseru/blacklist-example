package com.alex.blaclistexample.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class LoginDto {
    private String username;
    private String password;
}
