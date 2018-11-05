package com.alex.blaclistexample.dto;

import com.alex.blaclistexample.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String username;
    private String role;

    public static UserDto from(User user) {
        return new UserDto(user.getUsername(), user.getRole().toString());
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .sorted((o1, o2) -> o1.username.compareToIgnoreCase(o2.username))
                .collect(Collectors.toList());
    }
}
