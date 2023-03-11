package com.digipay.digitalwallet.service;

import com.digipay.digitalwallet.model.dto.UserDto;
import com.digipay.digitalwallet.model.entity.User;

public interface UserService {
    public User registerUser(UserDto userDto);
}
