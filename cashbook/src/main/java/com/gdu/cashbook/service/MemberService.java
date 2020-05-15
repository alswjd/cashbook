package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	@Autowired private JavaMailSender javaMailSender; //@Component
	
	//비밀번호 찾기
	public int getMemberPw(Member member) { //id와 email
		//랜덤한 pw 추가
		UUID uuid = UUID.randomUUID(); //랜덤 문자열 생성 라이브러리(API)
		//앞에서 8자리만 가지고 옴
		String memberPw = uuid.toString().substring(0,8);
		System.out.println(memberPw+"<---memberPw");
		member.setMemberPw(memberPw);
		
		int row = memberMapper.updateMemberPw(member); //1이면 성공 0이면 실패]
		System.out.println(row+"<---row");
		
		if(row == 1) {
			System.out.println(memberPw+"<==memberPw");
			//메일로 update 성공한 랜덤 pw 전송
			//메일객체 new JavaMailSender();
			
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			System.out.println(member.getMemberEmail());
			simpleMailMessage.setFrom("alswjdd09@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호는" + memberPw + "입니다");
			
			javaMailSender.send(simpleMailMessage);
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
