package com.bmtc.device.service;

import java.util.List;

import com.bmtc.task.domain.ExecuteDetail;

/**
 * @author: Jason.ma
 * @date: 2018年1月19日下午2:37:40
 *
 */
public interface ExecuteDetailService {
	/**
	 * 根据该批次下所有测试状态
	 * @param token 测试执行唯一标识
	 * @return 测试执行明细
	 */
	List<String> getAllExecuteStatus(String token);
	
	/**
	 * 根据测试执行token查询测试结果
	 * @param token
	 * @return 测试结果，测试报告，执行日志
	 */
	ExecuteDetail getExecuteResult(String token, String udid, String testSuitePath);
	
	/**
	 * 保存脚本执行状态
	 * @param executeDetail 测试执行明细
	 * @return 
	 */
	int save(ExecuteDetail executeDetail);
	
	/**
	 * 更新脚本执行状态
	 * @param executeDetail 测试执行明细
	 * @return 
	 */
	int update(ExecuteDetail executeDetail);
}
