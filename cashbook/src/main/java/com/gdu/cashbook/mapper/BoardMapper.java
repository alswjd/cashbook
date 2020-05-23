package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface BoardMapper {
	//관리자용 게시판
	public List<Board> boardListAllAdmin(Board board);
	//일반회원용 게시판
	public List<Board> boardListAllMember(Board board);
	//insert board
	public int insertBoard(Board board);
}
