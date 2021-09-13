package com.akhil.controller;

import com.akhil.Exceptions.UserAlreadyExistException;
import com.akhil.dto.UserDto;
import com.akhil.entity.request.RequestSignupDataEntity;
import com.akhil.entity.response.UserResponse;
import com.akhil.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("signup")
public class SignupController {
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse signup(@RequestBody RequestSignupDataEntity signupDataEntity){
    log.info("New user creation request : " + signupDataEntity.getEmailId());
        UserDto signInUserDto = new UserDto();
        BeanUtils.copyProperties(signupDataEntity,signInUserDto);
        try {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(userService.createUser(signInUserDto),userResponse);
            log.info("user successfully created "+userResponse.getEmailId());
            return userResponse;
        }
        catch (UserAlreadyExistException ex){}
        return null;
    }
}
