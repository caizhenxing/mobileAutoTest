package com.bmtc.script.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bmtc.common.utils.Query;
import com.bmtc.script.domain.Script;

/**
 * 脚本列表的DAO接口
 * @author Administrator
 *
 */
@Mapper
public interface ScriptDao {

	// 查询脚本数据
	List<Script> list(Map<String,Object> map);
	// 查询脚本总记录数
	int count(Query query);
	// 通过ID查询脚本信息
	Script get(Long scriptId);
	// 查询指定条件的脚本总记录数
	int count(Map<String,Object> map);
	// 保存脚本
	int save(Script script);
	// 修改脚本
	int update(Script script);
	// 删除脚本
	int remove(Long scriptId);
	// 批量删除脚本
	int batchRemove(Long[] scriptIds);
	// 通过测试套路径查询脚本信息
	Script getScriptByTestSuitPath(String testSuitPath);
	
}
