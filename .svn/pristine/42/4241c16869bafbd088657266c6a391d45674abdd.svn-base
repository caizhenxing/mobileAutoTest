package com.bmtc.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bmtc.common.utils.ShiroUtils;
import com.bmtc.system.domain.UserDO;
import com.bmtc.system.service.DeptService;
import com.bmtc.system.service.UserService;

@Controller
public class BaseController {
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	UserService userService;
	
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}
	public Long getUserId() {
		return getUser().getUserId();
	}
	public List<Long> getDeptId(){
		return userService.getDeptIdByUserId(getUserId());
	}
	/*public String getDeptName() {
		return deptService.get(ShiroUtils.getUser().getDeptId()).getName();
	}*/
	public String getUsername() {
		return getUser().getUsername();
	}
	/*public String getSvnName() {
		return deptService.get(getDeptId()).getSvnName();
	}*/
}