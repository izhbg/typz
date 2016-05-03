package com.izhbg.typz.sso.util;

public interface CustomPasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
