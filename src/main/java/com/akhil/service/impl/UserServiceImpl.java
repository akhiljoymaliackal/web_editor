package com.akhil.service.impl;

import com.akhil.Exceptions.UserAlreadyExistException;
import com.akhil.dto.UserDto;
import com.akhil.entity.UserEntity;
import com.akhil.repository.UserRepository;
import com.akhil.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException {
        if (userRepository.findByEmailId(userDto.getEmailId())==null){
            userDto.setUserId(DigestUtils.sha256Hex(userDto.getEmailId()));
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(userDto,userEntity);
            userRepository.save(userEntity);
            log.info("New user "+ userDto.getEmailId()+" has been successfully created");
            return userDto;
        }
        else throw new UserAlreadyExistException("User Already Exist");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmailId(s);
        if (userEntity==null) throw new UsernameNotFoundException("User didn't exist "+ s );
        return new User(s,userEntity.getPassword(), new ArrayList<GrantedAuthority>());
    }
}
