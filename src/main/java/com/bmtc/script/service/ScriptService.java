package com.bmtc.script.service;

import java.util.List;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.Query;
import com.bmtc.script.domain.Script;
import com.bmtc.system.domain.DeptDO;

/**
 * 脚本管理的service接口
 * @author Administrator
 *
 */
public interface ScriptService {

	// 查询脚本列表数据
	List<Script> list(Query query);
	// 查询总记录数
	int count(Query query);
	// 更新数据库脚本信息
	boolean updateScriptData(DeptDO DeptDO);
	// 通过id查询脚本信息
	Script get(Long scriptId);
	// 获得脚本的树形结构数据
	Tree<Script> getTree();
	// 获得脚本关联的casename(树形结构)
	Tree<String> getTree(Long scriptId);
	// 查询测试任务关联脚本信息(树形结构)
	Tree<Script> getTaskScriptTreeData(Long scriptIds);
	// 获取测试任务关联脚本Tree信息
	List<Tree<String>> getTreeData(String svnName);
	
}
