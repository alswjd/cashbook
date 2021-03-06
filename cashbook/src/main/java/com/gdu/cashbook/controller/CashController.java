package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	
	//입력폼
	@GetMapping("/addCash")
	public String addCash(HttpSession session, Model model) {
		//session
		if(session.getAttribute("loginMember") == null) { 
			return "redirect:/login";
		}
		
		List<Category> category = cashService.categoryName();
		
		String memberId = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		model.addAttribute("memberId", memberId);
		model.addAttribute("category", category);
		
		return "addCash";
	}
	
	//입력
	@PostMapping("/addCash")
	public String addCash(Cash cash) {
		cashService.addCash(cash);
		return "redirect:/getCashListByDate";
	}
	
	//수정 폼
	@GetMapping("/getCashOne")
	public String getCashOne(HttpSession session,Model model,  @RequestParam("cashNo")String cashNo) {
		//session
		if(session.getAttribute("loginMember") == null) { 
			return "redirect:/login";
		}
		
		List<Category> category = cashService.categoryName();
		model.addAttribute("category", category);
		
		Cash cashOne = cashService.getCashOne(cashNo);
		model.addAttribute("cash", cashOne);
		
		return "getCashOne";
	}
	
	//수정 액션
	@PostMapping("/modifyCash")
	public String modifyCash(HttpSession session, Model model, Cash cash) {
		//session
		if(session.getAttribute("loginMember") == null) { 
			return "redirect:/login";
		}
		
		cashService.modifyCash(cash);
		System.out.println(cash + "<--modifycash");
		
		return "redirect:/getCashListByDate";
	}
	
	//삭제
	@GetMapping("/removeCash")
	public String removeCash(@RequestParam("cashNo") String cashNo) {
		cashService.removeCash(cashNo);
		System.out.println(0);
		return "redirect:/getCashListByDate";
	}
	

	//달력
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate day) {
		
		//session
		if(session.getAttribute("loginMember") == null) { 
			return "redirect:/login";
		}
		
		Calendar cDay = Calendar.getInstance();//오늘 날짜
		
		if(day == null) {
			day = LocalDate.now();
		}else {
			//day -> cDay로 형변환
			/*
			  	LocalDate -> Calendar
			  	LocalDate -> Date -> Calendar
			  	LocalDate -> String -> Calendar
			  	LocalDate -> Calendar
			 */
			cDay.set(day.getYear(), day.getMonthValue(), day.getDayOfMonth());
		}
		
		/*
		 	addAttribute
		 	0. 오늘 LocalDate 타입
		  	1. 오늘이 무슨 달인지 Calendar타입
		  	2. 이번달의 마지막 일
		  	3. 이번달 1일의 요일
		 */
		
		//일별 수입, 지출 총액
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year = cDay.get(Calendar.YEAR);
		int month = cDay.get(Calendar.MONTH)+1; //1월은 0부터 시작
		List<DayAndPrice> dayAndPrices = cashService.getDayAndPriceList(memberId, year, month);
		System.out.println(dayAndPrices);
		
		model.addAttribute("dayAndPriceList", dayAndPrices);
		model.addAttribute("day",day);
		model.addAttribute("month",cDay.get(Calendar.MONTH)+1);	//월 - day 값에 포함
		model.addAttribute("lastDay",cDay.getActualMaximum(Calendar.DATE));		//마지막 일
		
		Calendar firstDay= cDay;
	    firstDay.set(Calendar.DATE,1); //cDay 에서 일만 1일로 변경 
	    firstDay.get(Calendar.DAY_OF_WEEK); //요일      0->일요일,   1->월요일,   2->화요일    ......   6토요일
	    System.out.println("firstDay.get(Calendar.DAY_OF_WEEK)"+firstDay.get(Calendar.DAY_OF_WEEK));
	    model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK) ); //이번달의 1일이 무슨요일인지 
		
		return "getCashListByMonth";
	}
	
	
	//지출 수입 리스트
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, 
			@RequestParam(value="day", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate day) {
			//day값으로 널이 들어올 수도 있음 / 값을 문자열로 가지고 오면 자동적으로 형식 변환 yyyy-MM-dd 
		
		if(day == null) {
			day = LocalDate.now(); 
		}
		
		System.out.println(day +"dayyyyy");
		
		//session
		if(session.getAttribute("loginMember") == null) { 
			return "redirect:/login";
		}
		
		//로그인 아이디
		String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
		
		
		//cash : id + date(yyyy-mm-dd) 값이 들어가야 함
		Cash cash = new Cash();
		cash.setMemberId(loginMember);
		cash.setCashDate(day);
		
		//list
		Map<String, Object> map = cashService.getCashListByDate(cash);
		
		//model
		model.addAttribute("list", map.get("list"));
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("day", day);
		
		//debug
		/*for(Cash c : list) {
			System.out.println(c);
		}*/
		
		return "getCashListByDate";
		
	}
}
