package com.bmtc.system.service;

import com.bmtc.common.domain.Tree;
import com.bmtc.system.domain.DeptDO;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	//通过产品机构id获取产品机构对象
	DeptDO get(Long deptId);
	
	//查询产品机构数据
	List<DeptDO> list(Map<String, Object> map);
	
	//统计产品机构总数
	int count(Map<String, Object> map);
	
	//保存产品机构
	void save() throws MalformedURLException;
	
	//更新产品机构
	int update(DeptDO sysDept);
	
	//封装产品机构树形图数据(包含产品)
	Tree<DeptDO> getTree();
	
	//查询部门以及此部门的下级部门
	boolean checkDeptHasUser(Integer  deptId);
	
	//封装产品机构树形图数据(不包含产品)
	Tree<DeptDO> getTrees();
	
	//通过产品机构名称获取产品机构对象
	DeptDO getName(String name);
	

}
