package com.bmtc.system.dao;

import com.bmtc.system.domain.UserRoleDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户与角色对应关系
 * 
 * @author nienannan
 */
@Mapper
public interface UserRoleDao {
	
	//通过角色id获取角色
	UserRoleDO get(Long id);
	
	//通过条件查询用户与角色对应关系信息
	List<UserRoleDO> list(Map<String, Object> map);
	
	//通过条件查询用户与角色对应关系信息数量
	int count(Map<String, Object> map);
	
	//保存用户与角色对应关系信息
	int save(UserRoleDO userRole);
	
	//修改用户与角色对应关系信息
	int update(UserRoleDO userRole);
	
	//通过id删除对应表信息
	int remove(Long id);
	
	//批量删除表信息
	int batchRemove(Long[] ids);
	
	//通过用户id查询所对应的角色信息
	List<Long> listRoleId(Long userId);
	
	//通过用户id移除角色信息
	int removeByUserId(Long userId);
	
	//批量保存用户与角色对应关系信息
	int batchSave(List<UserRoleDO> list);
	
	//批量移除用户所对应的角色信息
	int batchRemoveByUserId(Long[] ids);
}
