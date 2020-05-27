package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private MemberMapper memberMapper;
	
	//modifyBoard
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	//deleteBoard
	public int removeBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}
	
	//boardListDetail
	public Board boardListDetail(int boardNo) {
		return boardMapper.boardListDetail(boardNo);
	}
	
	//관리자용 게시판 리스트
	public Map<String,Object> getBoardListAllAdmin(int beginRow, int rowPerPage, Board board) {
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		//total row
		int totalRow = 0;
		
		String boardTitle = board.getBoardTitle();
		
		//search
		if(board.getBoardTitle().equals("")) {
			totalRow = boardMapper.listAdminCount();
		}else {
			totalRow = boardMapper.listAdminCountSearch(boardTitle);
		}
		
		int lastPage = totalRow / rowPerPage ;
		
		if((totalRow / rowPerPage) != 0) {
			lastPage += 1;
		}
		
		map.put("lastPage", lastPage);
		
		//list
		List<Board> list = boardMapper.boardListAllAdmin(beginRow,rowPerPage,board);
		map.put("list", list);
		
		return map; 
	}
	
	//일반 회원용 게시판 리스트
	public Map<String,Object> getBoardListAllMember(int beginRow, int rowPerPage, Board board){
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		
		//list
		System.out.println(board);
		List<Board> list = boardMapper.boardListAllMember(beginRow,rowPerPage,board);
		map.put("list", list);
		
		//total Row
		int totalRow = boardMapper.listMemberCount(board.getMemberId());
		System.out.println(totalRow+"totalRow");
		int lastPage = totalRow / rowPerPage;
		
		if((totalRow / rowPerPage) != 0) {
			lastPage += 1;
		} 
		
		System.out.println(lastPage+"lastPage");
		map.put("lastPage", lastPage);
		
		return map;
	}
	
	//insert board
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
}
