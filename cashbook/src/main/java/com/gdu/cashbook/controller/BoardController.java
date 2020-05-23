package com.gdu.cashbook.controller;

import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//insert board
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session,Model model) {
		//session
		if(session.getAttribute("loginAdmin") == null && session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		//loginAdmin
		if(session.getAttribute("loginAdmin") != null) {
			String loginAdmin = ((Admin)(session.getAttribute("loginAdmin"))).getAdminId();
			model.addAttribute("loginAdmin", loginAdmin);
		}
		
		//loginMember
		if(session.getAttribute("loginMember") != null) {
			String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
			model.addAttribute("loginMember", loginMember);
		}
		
		return "addBoard";
	}
	
	//insert board action
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session, Board board) {
		//관리자가 접속했을 때와 일반회원이 접속했을 때 다른 뷰로 페이지 연결
		if(session.getAttribute("loginMember") != null) {//일반회원이 접속해있을 경우
			boardService.addBoard(board);
			return "redirect:/getBoardListMember";
		}else if(session.getAttribute("loginAdmin") != null) {
			boardService.addBoard(board);
			return "redirect:/getBoardListAdmin";
		}
		
		//둘 다 아닌 경우 로그인한 세션이 없는 것으로 index 뷰로 연결
		return "redirect:/index";
	}
	
	//관리자용 게시판 리스트
	@GetMapping("/getBoardListAdmin")
	public String getBordListAdmin(HttpSession session, Model model) {
		//session
		if(session.getAttribute("loginAdmin") == null ) {
			return "redirect:/login";
		}
		
		//loginAdmin
		String loginAdmin = ((Admin)(session.getAttribute("loginAdmin"))).getAdminId();
		
		//로그인 된 세선값 넣기
		Board board = new Board();
		board.setAdminId(loginAdmin);
		
		//list
		List<Board> b = boardService.getBoardListAllAdmin(board);
		
		//model
		model.addAttribute("board", b);
		
		return "getBoardListAdmin";
	}
	
	//일반회원용 게시판 리스트
	@GetMapping("/getBoardListMember")
	public String getBoardListMember(HttpSession session, Model model) {
		//session
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		//loginMember
		String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		//로그인 된 세선값 넣기
		Board board = new Board();
		board.setMemberId(loginMember);
		
		//list
		List<Board> b = boardService.getBoardListAllMember(board);
		
		//model
		model.addAttribute("board", b);
		
		return "getBoardListMember";
	}
}
