package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.model.dto.UserDto;
import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.repository.UserRepository;
import com.digipay.digitalwallet.service.UserService;
import com.digipay.digitalwallet.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public User registerUser(UserDto userDto){
        User user = userMapper.userDtoToUser(userDto);
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }
}
