package com.bmtc.system.dao;

import com.bmtc.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理
 * @author nienannan
 */
@Mapper
public interface DeptDao {
	
	//通过产品机构id获取产品机构对象
	DeptDO get(Long deptId);
	
	//查询产品机构数据
	List<DeptDO> list(Map<String,Object> map);
	
	//统计产品机构总数
	int count(Map<String,Object> map);
	
	//保存产品机构
	int save(DeptDO dept);
	
	//更新产品机构
	int update(DeptDO dept);
	
	//查询父id
	Long[] listParentDept();
	
	//通过产品id获取关联的用个数
	int getDeptUserNumber(Integer deptId);
	
	//通过产品名获取产品对象
	DeptDO getName(String name);
	
	//清除产品数据库表
	void remove();
	
	//清除产品表临时数据
	void removeTemp();
	
	//保存产品数据到临时表
	void saveTemp(DeptDO sysDept);
	
	//将临时表数据复制到产品表
	void copyDate();

}
