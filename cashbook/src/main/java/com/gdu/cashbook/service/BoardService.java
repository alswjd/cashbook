package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Service
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
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
	public List<Board> getBoardListAllAdmin(Board board) {
		return boardMapper.boardListAllAdmin(board);
	}
	
	//일반 회원용 게시판 리스트
	public List<Board> getBoardListAllMember(Board board){
		return boardMapper.boardListAllMember(board);
	}
	
	//insert board
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
}
