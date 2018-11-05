package com.alex.blaclistexample.dto;

import com.alex.blaclistexample.models.Token;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenDto {
    private String value;
    private String expiredDate;
    private String username;

    public static TokenDto from(Token token) {
        return TokenDto.builder()
                .value(token.getToken())
                .expiredDate(token.getExpiredDate().toString())
                .username(token.getUser().getUsername())
                .build();
    }

    public static List<TokenDto> from(List<Token> tokens) {
        return tokens.stream()
                .map(TokenDto::from)
                .collect(Collectors.toList());
    }
}
