package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;

@Mapper
public interface CashMapper {
	//로그인 사용자의 오늘 날짜 cash 목록 나타내기
	public List<Cash> selectCashListByDate(Cash cash);
	
	//합계 
	public int selectCashKindSum(Cash cash);
}
