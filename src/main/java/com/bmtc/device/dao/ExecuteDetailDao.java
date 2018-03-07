package com.bmtc.device.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bmtc.device.domain.ExecuteStatus;
import com.bmtc.device.domain.TestReport;
import com.bmtc.task.domain.ExecuteDetail;

/**
 * @author: Jason.ma
 * @date: 2018年1月19日上午11:24:18
 *
 */
@Mapper
public interface ExecuteDetailDao {
	// 查询脚本执行状态
	List<String> getAllExecuteStatus(String token);

	// 查询测试结果
	ExecuteDetail getExecuteResult(@Param("token") String token,
			@Param("udid") String udid,
			@Param("testSuitePath") String testSuitePath);

	// 保存脚本执行状态
	int save(ExecuteDetail executeDetail);

	// 修改脚本执行状态
	int update(ExecuteDetail executeDetail);
}
