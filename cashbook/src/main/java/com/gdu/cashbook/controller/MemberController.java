package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {
	//객체를 자동으로 주입
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	

	//회원정보 수정 액션
	@PostMapping("/updateMember")
	public String updateMember(HttpSession session, MemberForm memberForm) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(memberForm);
		
		if(memberForm.getMemberPic() != null) {
			if(!memberForm.getMemberPic().getContentType().equals("image/jpeg") 
					&& !memberForm.getMemberPic().getContentType().equals("image/png")
						&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/updateMember";
			}
		}
		
		memberService.updateMember(memberForm);
		
		//수정한 후에 다시 수정했던 정보 보여줌
		return "redirect:/memberInfo";
	}
	

	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(MemberForm memberForm,HttpSession session) {
		//로그인 된 아이디가 있으면 redirect
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
	
		//memberForm
		//service : memberForm -> member + 폴더에 파일 저장
		System.out.println(memberForm+"<--form");
		
		//이미지 확장자 검사 (jpg / png / gif만 업로드 가능)
		if(memberForm.getMemberPic() != null) {
			if(!memberForm.getMemberPic().getContentType().equals("image/jpeg") 
					&& !memberForm.getMemberPic().getContentType().equals("image/png")
						&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember";
			}
		}
		
		memberService.addMember(memberForm);
		//데이터를 insert 한 후 redirect
		
		return "redirect:/index";
	}
	
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
	public String removeMember(HttpSession session,Model model, LoginMember loginMember) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		Member member = memberService.selectgetMemberOne((LoginMember)(session.getAttribute("loginMember")));
		model.addAttribute("member", member);
		
		return "removeMember"; //input type="password" name="memberPw"
	}
	
	//회원탈퇴 액션
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, Model model, @RequestParam("memberPw") String memberPw) {
		//로그인 되어 있지 않으면 index로 redirect
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		
		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		
		int row = memberService.removeMember(loginMember);
		
		if(row == 0) {//삭제 안 됨
			model.addAttribute("msg","비밀번호를 확인하세요");
			return "redirect:/removeMember";
		}else {//삭제 실행
			session.invalidate();
			return "redirect:/index";
		}
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
	public String login(LoginMember loginMember,Admin admin, HttpSession session) {
		
		//session
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println(returnLoginMember + "<== returnLoginMember");
		
		//admin session
		Admin returnLoginAdmin = adminService.adminLogin(admin);
		
		
		//로그인 했을 때 아이디와 비밀번호 가 일치하지 않으면
		if(returnLoginMember == null) {
			//다시 login 뷰 연결
			return "redirect:/login";
		}else { //일치한다면
			//redirect
			session.setAttribute("loginMember", returnLoginMember);
			session.setAttribute("loginAdmin", returnLoginAdmin);
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
	public String addMember(HttpSession session) {
		//로그인 된 아이디가 있으면 redirect
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		return "addMember";
	}
	
}
