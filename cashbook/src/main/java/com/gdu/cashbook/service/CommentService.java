package com.gdu.cashbook.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;

@Service
public class CommentService {

	@Autowired private CommentMapper commentMapper;
	
	//removeComment
	public int removeComment(int commentNo) {
		return commentMapper.deleteComment(commentNo);
	}
	
	//commentList
	public List<Comment> commentList(int boardNo){
		return commentMapper.commentList(boardNo);
	}
	
	//insertComment
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
}
