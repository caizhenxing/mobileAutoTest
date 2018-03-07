package com.bmtc.device.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmtc.device.dao.ExecuteDetailDao;
import com.bmtc.device.domain.ExecuteStatus;
import com.bmtc.device.domain.TestReport;
import com.bmtc.device.service.ExecuteDetailService;
import com.bmtc.task.domain.ExecuteDetail;

/**
 *@author: Jason.ma
 *@date: 2018年1月19日下午2:46:24
 *
 */
@Service
public class ExecuteDetailServiceImpl implements ExecuteDetailService {
	@Autowired
	private ExecuteDetailDao executeDetailDao;
	
	@Override
	public List<String> getAllExecuteStatus(String token) {
		return executeDetailDao.getAllExecuteStatus(token);
	}
	
	@Override
	public ExecuteDetail getExecuteResult(String token, String udid, String testSuitePath){
		return executeDetailDao.getExecuteResult(token, udid, testSuitePath);
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int save(ExecuteDetail executeDetail) {
		Date gmtCreate = new Date();
		Date gmtModified = gmtCreate;
		executeDetail.setGmtCreate(gmtCreate);
		executeDetail.setGmtModified(gmtModified);
		return executeDetailDao.save(executeDetail);
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int update(ExecuteDetail executeDetail) {
		Date gmtModified = new Date();
		executeDetail.setGmtModified(gmtModified);
		return executeDetailDao.update(executeDetail);
	}
}

