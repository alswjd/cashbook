package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;

@Service
public class CashService {
	
	@Autowired private CashMapper cashMapper;
	//금일 지출 수입 리스트
	public List<Cash> getCashListByDate(Cash cash){
		return cashMapper.selectCashListByDate(cash);
	}
}
