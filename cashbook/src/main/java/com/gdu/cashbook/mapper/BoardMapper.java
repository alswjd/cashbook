package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface BoardMapper {
	//boardListAll
	public List<Board> boardListAllAdmin(Board board);
}
