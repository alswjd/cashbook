package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	//객체를 자동으로 주입
	@Autowired
	private MemberService memberService;
	
	
	//비밀번호 찾기 액션
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session,Member member, Model model) {
		int row = memberService.getMemberPw(member);
		
		String msg = "아이디와 메일을 확인하세요";
		if(row == 1) {
			msg = "비밀번호를 메일로 전송하였습니다";
		}
		model.addAttribute("msg", msg);
		
		return "memberPwView";
	}
	
	//비밀번호 찾기
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		//로그인 되어 있지 않아야 한다
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "findMemberPw";
	}
	
	//아이디 찾기
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		//로그인 되어 있지 않아야 한다
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "findMemberId";
	}
	
	//아이디 찾기 액션
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		//로그인 되어 있지 않아야 한다
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart+"<==memberIdPart");
		model.addAttribute("memberIdPart", memberIdPart);
		
		return "memberIdView";
	}
	
	//회원탈퇴 폼
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		String memberPw = ((LoginMember)(session.getAttribute("loginMember"))).getMemberPw();
		System.out.println(memberPw +"<==memberpw");
		
		return "removeMember"; //input type="password" name="memberPw"
	}
	
	//회원탈퇴 액션
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		
		memberService.removeMember(loginMember);
		
		session.invalidate();
		
		return "redirect:/index";
	}
	
	//회원정보 수정 폼
	@GetMapping("/updateMember")
	public String updateMember(HttpSession session, Model model) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		
		//session
		Member m = this.memberService.selectgetMemberOne((LoginMember)(session.getAttribute("loginMember")));
		
		//model
		model.addAttribute("m", m);
		
		return "updateMember";
	}
	
	//회원정보 수정 액션
	@PostMapping("/updateMember")
	public String updateMember(HttpSession session, Member member) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(member);
		memberService.updateMember(member);
		
		//수정한 후에 다시 수정했던 정보 보여줌
		return "redirect:/memberInfo";
	}
	
	
	//회원정보 출력
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		//로그인되어 있지 않다면 login뷰로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		//session : object 값 -> LoginMember 형태로 형변환
		Member member = this.memberService.selectgetMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member + "<== member");
		
		//model :데이터 넘겨줌
		model.addAttribute("member",member);
		
		return "memberInfo";
	}
	
	//중복되는 아이디
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		//로그인 된 아이디가 있으면 redirect
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		//입력받는 값을 테이블 값과 비교한 후 confirmMemberId에 넣음 
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		
		if(confirmMemberId != null) {
			//아이디를 사용할 수 없습니다.
			model.addAttribute("msg", "사용중인 아이디입니다.");
		}else {
			//아이디를 사용할 수 있습니다.
			model.addAttribute("memberIdCheck",memberIdCheck);
		}
		
		//포워딩
		return "addMember"; 
	}
	
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
			return "redirect:/login";
		}else { //일치한다면
			//redirect
			session.setAttribute("loginMember", returnLoginMember);
			return "home";
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
