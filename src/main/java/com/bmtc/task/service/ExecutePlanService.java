package com.bmtc.task.service;

import java.util.List;

import com.bmtc.common.utils.Query;
import com.bmtc.task.domain.ExecutePlan;

/**
 * 测试任务执行计划的service接口
 * @author Administrator
 *
 */
public interface ExecutePlanService {

	// 查询执行计划列表数据
	List<ExecutePlan> list(Query query);
	// 查询执行计划总记录数
	int count(Query query);
	// 通过id查询执行计划
	ExecutePlan get(Long id);
	// 保存执行计划
	int save(ExecutePlan executePlan);
	// 执行计划修改
	int update(ExecutePlan executePlan);
	// 执行测试计划(本地)
	String active(Long id);
	// 执行测试计划(ATP)
	String sendToATP(Long id);
	// 执行计划删除
	int remove(Long id);
	// 执行计划批量删除
	int batchremove(Long[] ids);

}
