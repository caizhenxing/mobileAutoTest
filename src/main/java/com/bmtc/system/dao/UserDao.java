package com.bmtc.system.dao;

import com.bmtc.system.domain.UserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author nienannan
 */
@Mapper
public interface UserDao {
	
	//通过用户id获取用户对象
	UserDO get(Long userId);
	
	//获取所有用户信息
	List<UserDO> list(Map<String,Object> map);
	
	//统计用户个数
	int count(Map<String,Object> map);
	
	//保存用户
	int save(UserDO user);
	
	//更新用户
	int update(UserDO user);
	
	//通过用户id移除用户
	int remove(Long userId);
	
	//批量移除用户
	int batchRemove(Long[] userIds);
	
	

}
