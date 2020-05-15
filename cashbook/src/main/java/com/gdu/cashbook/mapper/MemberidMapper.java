package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Memberid;

@Mapper
public interface MemberidMapper {
	//삭제된 멤버 아이디 가지고 와서 insert
	public void insertMemberid(Memberid memberid);
}
