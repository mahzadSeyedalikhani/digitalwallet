package com.digipay.digitalwallet.service.impl;

import com.digipay.digitalwallet.mapper.UserMapper;
import com.digipay.digitalwallet.model.dto.UserDto;
import com.digipay.digitalwallet.model.entity.User;
import com.digipay.digitalwallet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;




    @BeforeEach
    void setUp() {
        userDto = new UserDto("Nabat", "654", "789789");
    }


    @Test
    void givenUserDto_whenSaveUser_thenReturnUser() {

        User myUser = new User();
        myUser.setUserId("1");
        given(userMapper.userDtoToUser(userDto)).willReturn(myUser);
        given(userRepository.save(Mockito.any())).willReturn(myUser);
        User registeredUser = userService.registerUser(userDto);
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getUserId()).isNotNull();
    }
}