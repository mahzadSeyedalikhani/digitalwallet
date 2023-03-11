package com.digipay.digitalwallet.mapper;

import com.digipay.digitalwallet.model.dto.UserDto;
import com.digipay.digitalwallet.model.dto.UserRequest;
import com.digipay.digitalwallet.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    UserDto userRequestToUserDto(UserRequest userRequest);
    User userDtoToUser(UserDto userDto);
}
