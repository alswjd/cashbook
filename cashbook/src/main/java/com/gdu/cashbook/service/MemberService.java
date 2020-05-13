package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.Member;

@Service
@Transactional
public class MemberService {
	//객체 자동으로 주입
	@Autowired
	private MemberMapper memberMapper;
	
	//회원가입 - insert(add)
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}