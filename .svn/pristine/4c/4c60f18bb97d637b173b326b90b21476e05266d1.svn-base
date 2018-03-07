package com.bmtc.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bmtc.system.domain.UserProductDO;
/**
 * 
 * @author nienannan
 *
 */
@Mapper
public interface UserProductDao {
	
	//批量保存用户id和所对应的产品id
	int batchSave(List<UserProductDO> list);
	
	//通过用户id移除所对应的产品id
	void removeByUserId(Long userId);
	
	//通过用户id获取所对应的产品id
	List<Long> getDeptIdByUserId(Long userId);
	
	//查询用户对应的所有产品id
	Long[] listAllDept();
}
