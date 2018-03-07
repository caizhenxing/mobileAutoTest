package com.bmtc.system.service;

import java.net.MalformedURLException;
import java.util.List;

import com.bmtc.common.domain.Tree;
import com.bmtc.system.domain.BatchDO;

public interface BatchService {
	
	//通过批次id获取批次对象
	BatchDO get(Integer batchId);
	
	//查询批次数据
	List<BatchDO> list();
	
	//保存批次数据
	void save() throws MalformedURLException;
	
	//通过批次id移除批次对象
	int remove(Integer  deptId);
	
	//更新批次
	int update(BatchDO batch);
	
	//通过批次名获取批次对象
	BatchDO getBatch(String batchName);

	//获取批次的Tree信息
	Tree<BatchDO> getBatchTree();

}
