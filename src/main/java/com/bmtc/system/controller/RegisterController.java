package com.bmtc.system.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.system.service.UserService;

@Controller
public class RegisterController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(RegisterController.class);
	
	@Autowired
	UserService userService;
	
	@Log("访问注册页")
	@GetMapping("/register")
	String register() {
		logger.info("RegisterController.register() start");
		logger.info("RegisterController.register() end");
		return "register";
	}
	
}
