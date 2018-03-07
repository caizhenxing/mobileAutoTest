package com.bmtc.task.dao;

import java.util.List;
import java.util.Map;

import com.bmtc.common.utils.Query;
import com.bmtc.task.domain.BMTCTask;

/**
 * 测试任务Dao接口
 * @author Administrator
 *
 */
public interface BMTCTaskDao {
	
	// 查询测试任务数据
	List<BMTCTask> list(Map<String,Object> map);
	// 查询总记录数
	int count(Query query);
	// 通过ID查询测试任务
	BMTCTask get(Long taskId);
	// 查询指定条件的总记录数
	int count(Map<String,Object> map);
	// 保存测试任务
	int save(BMTCTask task);
	// 修改测试任务
	int update(BMTCTask task);
	// 删除测试任务
	int remove(Long taskId);
	// 批量删除测试任务
	int batchRemove(Long[] taskIds);
	
}
