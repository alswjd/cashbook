package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	//객체를 자동으로 주입
	@Autowired
	private MemberService memberService;
	
	//로그인 폼 - login.html 연결
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	//로그인 액션 - select
	@PostMapping("/login")
	public String login(LoginMember loginMember, HttpSession session) {
		
		//session
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember + "<== returnLoginMember");
		
		//로그인 했을 때 아이디와 비밀번호 가 일치하지 않으면
		if(returnLoginMember == null) {
			//다시 login 뷰 연결
			return "login";
		}else { //일치한다면
			//redirect
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
	}
	
	//logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		//session 종료
		session.invalidate();
		return "redirect:/index";
	}
	
	//회원가입 폼 - addMember.html 뷰로 연결
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(Member member) {
		memberService.addMember(member);
		//데이터를 insert 한 후 redirect
		return "redirect:/index";
	}
}
