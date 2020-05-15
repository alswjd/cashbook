package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Service
@Transactional
public class MemberService {
	//객체 자동으로 주입
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberidMapper memberidMapper;

	
	//비밀번호 ?
	public int getMemberPw(Member member) { //id와 email
		//랜덤한 pw 추가
		UUID uuid = UUID.randomUUID();
		//앞에서 8자리만 가지고 옴
		String memberPw = uuid.toString().substring(0,8);
		member.setMemberPw(memberPw);
		
		int row = memberMapper.updateMember(member); //1이면 성공 0이면 실패
		
		if(row == 1) {
			System.out.println(memberPw+"<==memberPw");
			//메일로 update 성공한 랜덤 pw 전송
			//메일객체 new JavaMailSender();
		}
		
		return row;
	}
	
	//아이디 찾기
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdbyMember(member);
	}
	
	
	//회원탈퇴 - 트렌젝션 처리
	public void removeMember(LoginMember loginMember) {
		//1
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		this.memberidMapper.insertMemberid(memberid);
		
		//2
		memberMapper.deleteMember(loginMember);
	}
		
	
	//회원정보 수정
	public int updateMember(Member member) {
		return memberMapper.updateMember(member);
	}
	
	//회원정보
	public Member selectgetMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	//중복되는 아이디
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck);
	}
	
	//login
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입 - insert(add)
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
