package com.bmtc.system.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bmtc.common.domain.Tree;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.domain.UserDO;
import com.bmtc.system.vo.UserVO;

import org.springframework.stereotype.Service;

/**
 * 用户管理
 */
@Service
public interface UserService {
	
	//通过用户id获取用户对象
	UserDO get(Long id);
	
	//查询用户数据
	List<UserDO> list(Map<String, Object> map);
	
	//统计用户总数
	int count(Map<String, Object> map);
	
	//保存用户数据
	int save(UserDO user) throws MalformedURLException;
	
	//更新用户数据
	int update(UserDO user);
	
	//通过用户id移除用户
	int remove(Long userId);
	
	//通过用户id批量移除用户
	int batchremove(Long[] userIds);
	
	//判断用户是否存在
	boolean exist(Map<String, Object> params) throws MalformedURLException;
	
	//重置密码
	int resetPwd(UserDO userDO) throws Exception;
	
	//获取产品机构表数据
	Tree<DeptDO> getTree();

	//更新个人资料
	int updatePersonal(UserDO userDO);
	
	//通过用户id获取所关联的产品对象
	List<DeptDO> getProductsByUserId(Long userId);
	
	//通过用户id获取所关联的产品id
	List<Long> getDeptIdByUserId(Long userId);
	
	//查询用户对应的所有产品id
	Long[] listAllDept();
}
