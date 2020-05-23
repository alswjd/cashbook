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
	
	//boardListAll
	public List<Board> getBoardListAll(Board board) {
		return boardMapper.boardListAllAdmin(board);
	}

}
