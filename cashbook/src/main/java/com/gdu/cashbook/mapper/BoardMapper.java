package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface BoardMapper {
	//관리자용 게시판
	public List<Board> boardListAllAdmin(int beginRow, int rowPerPage, Board board);
	//관리자용 게시판 COUNT
	public int listAdminCount();
	//관리자 검색 카운트
	public int listAdminCountSearch(String boardTitle);
	//일반회원용 게시판
	public List<Board> boardListAllMember(int beginRow, int rowPerPage,Board board);
	//일반회원용 게시판 COUNT
	public int listMemberCount(String memberId);
	//insert board
	public int insertBoard(Board board);
	//boardListDetail
	public Board boardListDetail(int boardNo);
	//deleteBoard
	public int deleteBoard(int boardNo);
	//updateBoard
	public int updateBoard(Board board);
}
