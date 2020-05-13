package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	//객체를 자동으로 주입
	@Autowired
	private MemberService memberService;
	
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
