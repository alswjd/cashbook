package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Mapper
public interface MemberMapper {
	//이미지 가지고 오기
	public String selectMemberPic(String memberId);
	//비밀번호 찾기
	public int updateMemberPw(Member member);
	//아이디 찾기
	public String selectMemberIdbyMember(Member member);
	//회원 탈퇴
	public int deleteMember(LoginMember loginMember);
	//회원정보 수정
	public int updateMember(Member member);
	//회원정보
	public Member selectMemberOne(LoginMember loginMember); 
	//아이디 중복 체크
	public String selectMemberId(String memberIdCheck);
	//로그인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입 - insert
	public int insertMember(Member member);
}
