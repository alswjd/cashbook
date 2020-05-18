package com.gdu.cashbook.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.Memberid;

@Service
@Transactional
public class MemberService {
	//객체 자동으로 주입
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberidMapper memberidMapper;
	@Autowired private JavaMailSender javaMailSender; //@Component
	

	//회원정보 수정
	public int updateMember(MemberForm memberForm) {
		//memberForm -> member
		MultipartFile mf = memberForm.getMemberPic();
		
		//확장자
		String originName = mf.getOriginalFilename();
		int lastDot = 
		
		
		
		return memberMapper.updateMember(member);
	}
	
	//회원가입 - insert(add)
	public int addMember(MemberForm memberForm) {
		//memberForm -> member로 바꿔줌
		//파일을 디스크에 물리적으로 저장
		
		MultipartFile mf = memberForm.getMemberPic();
		//확장자 필요함
		String originName = mf.getOriginalFilename();
		/*
			파일 확장자 유효성 검사
			if(mf.getContentType().equals("image/png") || mf.getContentType().equals("image/png")) {
				//업로드를 함
			}else {
				//업로드 하지 않음	
			}
		*/
		System.out.println(originName +"<==originName");
		int lastDot = originName.lastIndexOf(".");
		String extention = originName.substring(lastDot);
		// 새로운 이름 생성 : UUID
		String memberPic = memberForm.getMemberId() + extention;
		
		//1.db에 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		
		System.out.println(member+"<== MemberService.addMEmber:member");
		int row = memberMapper.insertMember(member);
		
		//2.파일 저장 - static에 upload 파일 경로
		String path = "C:\\mj___\\stsSTS\\maven.1589424312961\\cashbook\\src\\main\\resources\\static\\upload\\";
		File file = new File(path + memberPic);
		try {
			mf.transferTo(file);
		} catch (Exception e) { //예외가 발생해야 트렌젝션 처리 가능
			e.printStackTrace();
			throw new RuntimeException();
			//Exception 종류
			//1. 예외를 처리해야만 문법적으로 문제가 없는 예외
			//2. 예외처리를 코드에서 구현하지 않아도 문제가 없는 예외 - RuntimeException() 
		}
		
		return row;
	}
	
	//회원탈퇴 - 트렌젝션 처리
	public int removeMember(LoginMember loginMember) {
		//1. 멤버 이미지 파일 삭제
		//1-1 파일 이름 select member_pic from member
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		//1-2 파일 삭제
		String path = "C:\\mj___\\stsSTS\\maven.1589424312961\\cashbook\\src\\main\\resources\\static\\upload";
		File file = new File(path +"\\"+ memberPic);
		if(file.exists()) {
			file.delete();
		}
		//2
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		this.memberidMapper.insertMemberid(memberid);
		
		//3
		return memberMapper.deleteMember(loginMember);
	}
	
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
	
}
