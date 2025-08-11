package com.mathildas.ecommerce.mapper;

import com.mathildas.ecommerce.dto.UserDTO;
import com.mathildas.ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "userRole")
    UserDTO toDTO(User user);

    @Mapping(source = "userRole", target = "role")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "img", ignore = true)
    User toEntity(UserDTO userDTO);
}
