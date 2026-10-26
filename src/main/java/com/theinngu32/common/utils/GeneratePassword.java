package com.theinngu32.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeneratePassword {

	public static void main(String args[]) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pwd = "123";
		String encodedPassword = passwordEncoder.encode(pwd);
		System.out.println(encodedPassword);

	}
}
