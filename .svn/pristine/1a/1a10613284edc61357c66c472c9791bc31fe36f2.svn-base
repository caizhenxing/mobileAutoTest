package com.bmtc.task.service;

import java.util.List;
import java.util.Map;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.Query;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.task.domain.BMTCTask;

/**
 * 测试任务的service接口
 * @author Administrator
 *
 */
public interface BMTCTaskService {
	
	// 查询测试任务数据
	List<BMTCTask> list(Map<String, Object> params);
	// 查询总记录数
	int count(Query query);
	// 批量删除测试任务
	int batchremove(Long[] taskIds);
	// 通过ID查询测试任务信息
	BMTCTask get(Long taskId);
	// 查询测试任务名称是否存在
	boolean exist(Map<String, Object> params);
	// 保存测试任务
	int save(BMTCTask bmtcTask);
	// 修改测试任务
	int update(BMTCTask bMTCTask);
	// 删除测试任务
	int remove(Long taskId);
	// 查询用户所属产品下的任务列表
	Tree<BMTCTask> getTaskList();
	// 获取用户所属的产品的树形结构数据
	Tree<DeptDO> getDeptTree();
	// 获得某产品对应的SVN库路径
	String getProductSvnPath(String deptId);

}
