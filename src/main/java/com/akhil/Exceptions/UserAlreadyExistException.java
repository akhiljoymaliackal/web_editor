package com.akhil.Exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message){
        super(message);
        log.error(message);

    }
}
