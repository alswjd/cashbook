package com.gdu.cashbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	//index 페이지
	@GetMapping("/index")
	public String index() {
		return "index"; //index뷰로 이동
	}
}
