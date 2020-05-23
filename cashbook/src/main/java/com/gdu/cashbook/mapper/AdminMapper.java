package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Admin;

@Mapper
public interface AdminMapper {
	//admin login
	public Admin adminLogin(Admin admin);
}
