package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private MemberService memberService;
	
	//admin login form
	@GetMapping("/adminLogin")
	public String adminLogin() {
		return "/adminLogin";
	}
	
	//admin login
	@PostMapping("/adminLogin")
	public String adminLogin(HttpSession session,Admin admin, LoginMember loginMember) {
		//session
		Admin returnLoginAdmin = adminService.adminLogin(admin);
		System.out.println(returnLoginAdmin+"<==adminLogin");
		
		//member session
		LoginMember returnLoginMember = memberService.login(loginMember);
		session.setAttribute("loginMember", returnLoginMember);
		
		//로그인 했을 때 아이디 비번 일치하지 않으면
		if(returnLoginAdmin == null) {
			//login 뷰 연결
			return "redirect:/adminLogin";
		}else {//일치
			session.setAttribute("loginAdmin", returnLoginAdmin);
			session.setAttribute("loginMember", returnLoginMember);
			return "home";
		}
	}
}
