package com.digipay.digitalwallet.controller;

import com.digipay.digitalwallet.mapper.UserMapper;
import com.digipay.digitalwallet.model.dto.UserDto;
import com.digipay.digitalwallet.model.dto.UserRequest;
import com.digipay.digitalwallet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public void registerUser(@RequestBody @Valid UserRequest userRequest){
        UserDto userDto = userMapper.userRequestToUserDto(userRequest);
        userService.registerUser(userDto);
    }
}
