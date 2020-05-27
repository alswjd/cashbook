package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;

@Mapper
public interface CashMapper {
	//수입 지출 합계
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map);
	//로그인 사용자의 오늘 날짜 cash 목록 나타내기
	public List<Cash> selectCashListByDate(Cash cash);
	//입력
	public int insertCash(Cash cash);
	//삭제
	public int deleteCash(String cashNo);
	//수정
	public int updateCash(Cash cash);
	//수정 폼
	public Cash getCashOne(String cashNo);
	//합계 
	public int selectCashKindSum(Cash cash);
	//category name
	public List<Category> categoryName();
}
