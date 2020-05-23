package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	//관리자용 게시판 리스트
	@GetMapping("/getBoardListAdmin")
	public String getBordListAdmin(HttpSession session, Model model) {
		//session
		if(session.getAttribute("loginAdmin") == null ) {
			return "redirect:/login";
		}
		
		//loginAdmin
		String loginAdmin = ((Admin)(session.getAttribute("loginAdmin"))).getAdminId();
		
		Board board = new Board();
		board.setAdminId(loginAdmin);
		
		List<Board> b = boardService.getBoardListAll(board);
		
		model.addAttribute("board", b);
		
		return "getBoardListAdmin";
	}
}
