package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class IndexController {
	
	@Autowired private MemberService memberService;
	
	//index 
	@GetMapping("/index")
	public String index() {
		return "index"; //index뷰로 이동
	}
	
	//home
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		//로그인 되어 있는 상태에서만 접근 가능. 없으면 로그인 화면으로 redirect
		if(session.getAttribute("loginMember") == null && session.getAttribute("loginAdmin") == null) {
			return "redirect:/";
		}
		
		//session
		Member member = this.memberService.selectgetMemberOne((LoginMember)(session.getAttribute("loginMember")));
		
		
		//model
		model.addAttribute("member", member);
		
		return "home";
	}
}
