package com.gdu.cashbook.vo;

//로그인 관리하기 편하게 따로 생성
public class LoginMember {
	private String memberId;
	private String memberPw;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	
	@Override
	public String toString() {
		return "LoginMember [memberId=" + memberId + ", memberPw=" + memberPw + "]";
	}
}
