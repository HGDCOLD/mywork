package com.cj.security;

import com.cj.util.md5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Md5Encoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        return md5.encode(rawPassword.toString());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(md5.encode(rawPassword.toString()));
    }
}
