package com.akhil.service;

import com.akhil.Exceptions.UserAlreadyExistException;
import com.akhil.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
    UserDto getUserByEmailId(String emailId);

}
