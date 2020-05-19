package com.gdu.cashbook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	//지출 수입 리스트
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model) {
		//session
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		//로그인 아이디
		String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		//오늘 날짜를 구해서 원하는 문자열 형태로 변경
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(today);
		System.out.println(strToday);
		
		//cash : id + date(yyyy-mm-dd) 값이 들어가야 함
		Cash cash = new Cash();
		cash.setMemberId(loginMember);
		cash.setCashDate(strToday);
		
		//list
		List<Cash> list = cashService.getCashListByDate(cash);
		
		//model
		model.addAttribute("list", list);
		model.addAttribute("today", today);
		
		//debug
		for(Cash c : list) {
			System.out.println(c);
		}
		
		return "getCashListByDate";
	}
}
