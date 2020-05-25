package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;

@Service
public class CommentService {

	@Autowired private CommentMapper commentMapper;
	
	//commentList
	public List<Comment> commentList(int boardNo){
		return commentMapper.commentList(boardNo);
	}
}
