package com.akhil.service;

import com.akhil.Exceptions.UserAlreadyExistException;
import com.akhil.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) throws UserAlreadyExistException;
}
