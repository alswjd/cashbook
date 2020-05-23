package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.AdminMapper;
import com.gdu.cashbook.vo.Admin;

@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	//admin login
	public Admin adminLogin(Admin admin) {
		return adminMapper.adminLogin(admin);
	}
}
