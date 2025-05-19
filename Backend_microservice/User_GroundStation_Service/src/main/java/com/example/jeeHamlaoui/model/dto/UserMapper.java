package com.example.jeeHamlaoui.model.dto;

import com.example.jeeHamlaoui.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "user_name", target = "user_name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "groundStation", target = "groundStation")
    @Mapping(source = "created_at", target = "created_at")
    UserDto toDto(User user);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "user_name", target = "user_name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "groundStation", target = "groundStation")
    @Mapping(source = "created_at", target = "created_at")
    User toEntity(UserDto userDTO);
}
