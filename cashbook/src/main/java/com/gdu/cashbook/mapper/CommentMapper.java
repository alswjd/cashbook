package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;

@Mapper
public interface CommentMapper {

	//commentList
	public List<Comment> commentList(int boardNo);
}
