package com.bmtc.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bmtc.script.domain.Script;
import com.bmtc.task.domain.ExecutePlanScriptDO;

/**
 * 执行计划和脚本关联的Dao接口
 * @author Administrator
 *
 */
@Mapper
public interface ExecutePlanScriptDao {

	// 保存执行计划关联脚本的关联关系
	int save(ExecutePlanScriptDO executePlanScriptDO);
	// 修改执行计划关联脚本的关联关系
	int update(ExecutePlanScriptDO executePlanScriptDO);
	// 删除执行计划与脚本的关联关系
	int remove(Long executePlanId);
	// 通过执行计划id获得相关联脚本信息
	List<Script> getScript(Long executePlanId);
	// 通过多个字段查询记录
	List<ExecutePlanScriptDO> getExecutePlanScript(@Param("executePlanId") Long executePlanId,@Param("scriptId") Long scriptId);
	// 获取执行计划与脚本的中间表对象
	List<ExecutePlanScriptDO> getExecutePlanScriptByExecutePlanId(Long executePlanId);
	
}
