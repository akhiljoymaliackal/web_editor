package com.akhil.security;

public interface SecurityConstants {
    String SIGNUP_URL = "/signup";
    long EXPIRATION_TIME = 864000000;
    String SECURITY_TOKEN ="akhilJoysSecretToken";
    String HEADER_STRING = "Authorization";
    String TOKEN_PREFIX = "Bearer";
}
