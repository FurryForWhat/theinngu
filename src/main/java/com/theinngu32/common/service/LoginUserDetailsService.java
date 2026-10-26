package com.theinngu32.common.service;

import com.theinngu32.common.dto.MyUserDetails;
import com.theinngu32.common.dto.user.LoginUserDTO;
import com.theinngu32.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService{

    private final UserRepository userRepository;

    public LoginUserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            return new LoginUserDTO(user.getId(), user.getName(), user.getEmail());
        }
        return null;
    }
}