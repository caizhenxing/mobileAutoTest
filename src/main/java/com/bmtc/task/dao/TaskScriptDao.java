package com.bmtc.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bmtc.script.domain.Script;
import com.bmtc.task.domain.TaskScriptDO;

/**
 * 测试任务和脚本关联的Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface TaskScriptDao {

	// 保存测试任务关联场景的关联关系
	int save(TaskScriptDO taskScript);
	// 修改测试任务关联场景的关联关系
	int update(TaskScriptDO taskScript);
	// 删除测试任务与场景的关联关系
	int remove(Long taskId);
	// 通过测试任务id获得相关联脚本信息
	List<Script> getScript(Long taskId);
	// 通过多个字段查询记录
	List<TaskScriptDO> getTaskScript(@Param("taskId") Long taskId,@Param("scriptId") Long scriptId);
	// 获取任务关联的中间表对象
	List<TaskScriptDO> getTaskScriptByTaskId(Long taskId);
	
}
