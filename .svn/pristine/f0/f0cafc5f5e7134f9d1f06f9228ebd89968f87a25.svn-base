package com.bmtc.task.dao;

import org.apache.ibatis.annotations.Mapper;

import com.bmtc.task.domain.TaskSceneDO;

/**
 * task和scene的中间表Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface TaskSceneDao {

	// 保存测试任务关联场景的关联关系
	void save(TaskSceneDO taskScene);
	// 删除测试任务与场景的关联关系
	void remove(Long taskId);
	
}
