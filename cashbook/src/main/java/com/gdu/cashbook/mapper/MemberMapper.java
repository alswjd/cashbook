package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	//로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입 - insert
	public int insertMember(Member member);
}
