package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.dto.auth.SignupDto;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller  // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;

	// public AuthController(AuthService authService){
	// 	this.authService = authService;
	//}
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "/auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "/auth/signup";
	}
	
	// 회원가입버튼 -> /auth/signup -> /auth/signin
	// 회원가입버튼 -> x
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {  // key = value (x-www-form-urlencoded) 		
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				 errorMap.put(error.getField(), error.getDefaultMessage());
				 System.out.println("====================");
				 System.out.println(error.getDefaultMessage());
				 System.out.println("===================="); 
			}
			throw new CustomValidationException("유효성 검사 실패함", errorMap);
		}else {
			// User <- SignupDto
			User user = signupDto.toEntity();
			authService.회원가입(user);
			//System.out.println(userEntity);
			return "/auth/signin";
		}
	}
}
