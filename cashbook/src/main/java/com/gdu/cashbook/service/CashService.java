package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
public class CashService {
	
	@Autowired private CashMapper cashMapper;
	
	//입력
	public int addCash(Cash cash) {
		return cashMapper.insertCash(cash);
	}
	
	//수정
	public int modifyCash(Cash cash) {
		System.out.println("modify");
		return cashMapper.updateCash(cash);
	}
	
	//categoryName
	public List<Category> categoryName(){
		return cashMapper.categoryName();
	}
	
	//수정 폼
	public Cash getCashOne(String cashNo) {
		return cashMapper.getCashOne(cashNo);
	}
	
	//지출 수입 합계
	public List<DayAndPrice> getDayAndPriceList(String memberId, int year, int month){
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberId",memberId);
		map.put("year", year);
		map.put("month", month);
		
		return cashMapper.selectDayAndPriceList(map);
	}
	
	//삭제
	public int removeCash(String cashNo) {
		System.out.println(1);
		return cashMapper.deleteCash(cashNo);
	}
	
	//금일 지출 수입 리스트
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> list = cashMapper.selectCashListByDate(cash); 
		int cashKindSum = cashMapper.selectCashKindSum(cash);
		
		Map<String, Object> map = new HashMap<>();
		map.put("list",list);
		map.put("cashKindSum", cashKindSum);
		
		return map;
	}
}
