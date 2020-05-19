package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;

@Service
public class CashService {
	
	@Autowired private CashMapper cashMapper;
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
