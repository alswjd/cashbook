package com.gdu.cashbook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {

	@Autowired private BoardService boardService;
	@Autowired private CommentService commentService;
	
	
	/*COMMENT*/
	//addComment action
	@PostMapping("/addComment")
	public String addComment(Comment comment) {
		System.out.println(comment+"<---comment");
		
		commentService.addComment(comment);
		return "redirect:/boardListDetail?boardNo="+comment.getBoardNo();
	}
	
	//remove comment
	@GetMapping("/removeComment")
	public String removeComment(@RequestParam(value="commentNo", required=false)int commentNo,@RequestParam(value="boardNo", required=false)int boardNo) {
		commentService.removeComment(commentNo);
		return "redirect:/boardListDetail?boardNo="+boardNo;
	}
	
	/*BOARD*/
	//modify Form
	@GetMapping("/modifyBoard")
	public String modifyBoard(HttpSession session, Model model, int boardNo) {
		//session
		if(session.getAttribute("loginAdmin") == null && session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		Board b = boardService.boardListDetail(boardNo);
		model.addAttribute("b", b);
		
		return "modifyBoard";
	}
	
	//modify Action
	@PostMapping("/modifyBoard")
	public String modifyBoard(HttpSession session, Board board) {
		//loginAdmin
		if(session.getAttribute("loginAdmin") != null) {
			String loginAdmin = ((Admin)(session.getAttribute("loginAdmin"))).getAdminId();
			boardService.modifyBoard(board);
			return "redirect:/getBoardListAdmin";
		}
		
		//loginMember
		if(session.getAttribute("loginMember") != null) {
			String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
			boardService.modifyBoard(board);
			return "redirect:/getBoardListMember";
		}
		
		
		return "redirect:/";
	}
	
	
	//deleteBoard
	@GetMapping("/removeBoard")
	public String removeBoard(HttpSession session, @RequestParam("boardNo") int boardNo) {
		//loginAdmin
		if(session.getAttribute("loginAdmin") != null) {
			boardService.removeBoard(boardNo);
			return "redirect:/getBoardListAdmin";
		}
		
		//loginMember
		if(session.getAttribute("loginMember") != null) {
			boardService.removeBoard(boardNo);
			return "redirect:/getBoardListMember";
		}
		
		return "redirect:/index";
	}
	
	//boardListDetail
	@GetMapping("/boardListDetail")
	public String boardListDetail(HttpSession session, Model model, @RequestParam(value="boardNo", required=false)int boardNo) {
		//session
		if(session.getAttribute("loginAdmin") == null && session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		if(session.getAttribute("loginAdmin") != null) {
			model.addAttribute("loginAdmin", session.getAttribute("loginAdmin"));
		}else if(session.getAttribute("loginMember") != null) {
			model.addAttribute("loginMember", session.getAttribute("loginMember"));	
		}
		
		Board b = boardService.boardListDetail(boardNo);
		model.addAttribute("b", b);
		
		//commentList
		List<Comment> list = commentService.commentList(boardNo);
		model.addAttribute("comment", list);
		
		return "boardListDetail";
	}
	
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
	public String getBordListAdmin(HttpSession session, Model model,@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value= "boardTitle", defaultValue = "") String boardTitle) {
		//session
		if(session.getAttribute("loginAdmin") == null ) {
			return "redirect:/login";
		}
		
		//loginAdmin
		String loginAdmin = ((Admin)(session.getAttribute("loginAdmin"))).getAdminId();
		
		//로그인 된 세선값 넣기
		Board board = new Board();
		board.setAdminId(loginAdmin);
		board.setBoardTitle(boardTitle);
		
		System.out.println(boardTitle +"<--검색");
		
		int rowPerPage = 5;
		int beginRow = (currentPage -1)*rowPerPage;
		
		//list
		Map<String, Object> map = boardService.getBoardListAllAdmin(beginRow,rowPerPage,board);
		
		//model
		model.addAttribute("list", map.get("list"));
		model.addAttribute("lastPage", map.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		
		return "getBoardListAdmin";
	}
	
	//일반회원용 게시판 리스트
	@GetMapping("/getBoardListMember")
	public String getBoardListMember(HttpSession session, Model model,@RequestParam(value = "currentPage", defaultValue = "1")int currentPage) {
		//session
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		//loginMember
		String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		//로그인 된 세선값 넣기
		Board board = new Board();
		board.setMemberId(loginMember);
		
		int rowPerPage = 5;
		int beginRow = (currentPage - 1)*rowPerPage;
		
		//list
		Map<String,Object> map = boardService.getBoardListAllMember(beginRow, rowPerPage, board);
		
		//model
		model.addAttribute("list", map.get("list"));
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("lastPage", map.get("lastPage"));
		
		return "getBoardListMember";
	}
}
