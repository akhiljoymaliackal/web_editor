package com.akhil.service.impl;

import com.akhil.Exceptions.UserAlreadyExistException;
import com.akhil.dto.UserDto;
import com.akhil.entity.UserEntity;
import com.akhil.repository.UserRepository;
import com.akhil.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException {
        if (userRepository.findByEmailId(userDto.getEmailId())==null){
            userDto.setUserId(DigestUtils.sha256Hex(userDto.getEmailId()));
            userDto.setPassword(userDto.getUnEncryptedPassword());
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userDto,userEntity);
            userRepository.save(userEntity);
            return userDto;
        }
        else throw new UserAlreadyExistException("User Already Exist");
    }
}
