package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	//index 
	@GetMapping("/index")
	public String index() {
		return "index"; //index뷰로 이동
	}
	
	//home
	@GetMapping("/home")
	public String home(HttpSession session) {
		//로그인 되어 있는 상태에서만 접근 가능. 없으면 로그인 화면으로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		return "home";
	}
}
