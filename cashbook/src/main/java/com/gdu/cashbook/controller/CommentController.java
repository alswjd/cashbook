package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.vo.Comment;

@Controller
public class CommentController {
	
	@Autowired private CommentService commentService;
	

}
