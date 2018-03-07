package com.bmtc.system.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.system.dao.BatchDao;
import com.bmtc.system.domain.BatchDO;
import com.bmtc.system.service.BatchService;
import com.bmtc.system.utils.GetDataByATP;
import com.bmtc.system.utils.BMTC.ArrayOfString;
import com.bmtc.system.utils.BMTC.BMTCSoap;

/**
 * 批次service的实现类
 * @author nienannan
 *
 */
@Service
@Component
public class BatchServiceImpl implements BatchService{
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(BatchServiceImpl.class);
	
	@Autowired
	BatchDao batchMapper;
	
	/**
	 * 查询批次数据
	 */
	@Override
	public List<BatchDO> list() {
		logger.info("BatchServiceImpl.list() start");
		List<BatchDO> batch = batchMapper.list(new HashMap<String, Object>());
		logger.info("BatchServiceImpl.list() end");
		return batch;
	}
	
	/**
	 * 清除批次数据库表
	 * @param 
	 * @return
	 */

	private void remove() {
		logger.info("BatchServiceImpl.remove() start");
		batchMapper.remove();
		logger.info("BatchServiceImpl.remove() end");
	}
	
	/**
	 * 保存批次数据
	 * @throws MalformedURLException 
	 */
	@Transactional
	@Override
	@Scheduled(cron="0 0 0,13 * * ?")
	public void save() throws MalformedURLException {
		logger.info("BatchServiceImpl.save() start");
		//清除批次临时表数据
		batchMapper.removeTemp();
		//从ATP获取批次信息
		BMTCSoap soap = GetDataByATP.getData();
		ArrayOfString batchList = soap.getBatchList();
		List<String> batchData = batchList.getString();
		for (String obj : batchData) {
			String[] batchs = obj.split("\\|");
			BatchDO batch = new BatchDO();
			int id = Integer.parseInt(batchs[0]);
			BatchDO batchDO = get(id);
			//判断批次是否存在本地数据库
			if (batchDO ==null || batchDO.getBatchId() != id 
					|| !batchs[1].equals(batchDO.getBatchName())) {
				batch.setBatchId(id);
				batch.setBatchName(batchs[1]);
				batchMapper.saveTemp(batch);
			}else{
				batchMapper.saveTemp(batchDO);
			}
			
		}
		//清除批次数据表
		synchronized (this) {
			remove();
			}
		//将批次临时表信息copy到批次数据表
		batchMapper.copyDate();
		logger.info("BatchServiceImpl.save() end");
	}
	
	/**
	 * 通过批次id获取批次对象
	 */
	@Override
	public BatchDO get(Integer batchId) {
		logger.info("BatchServiceImpl.get() start");
		logger.info("BatchServiceImpl.get() end");
		return batchMapper.get(batchId);
	}
	
	/**
	 * 通过批次id移除批次对象
	 */
	@Override
	public int remove(Integer batchId) {
		logger.info("BatchServiceImpl.remove() start");
		logger.info("BatchServiceImpl.remove() end");
		return batchMapper.remove(batchId);
	}
	
	/**
	 * 更新批次
	 */
	@Override
	public int update(BatchDO batch) {
		logger.info("BatchServiceImpl.update() start");
		int r = batchMapper.update(batch);
		logger.info("BatchServiceImpl.update() end");
		return r;
	}
	/**
	 * 通过批次名获取批次对象
	 */
	@Override
	public BatchDO getBatch(String batchName) {
		logger.info("BatchServiceImpl.getBatch() start");
		logger.info("BatchServiceImpl.getBatch() end");
		return batchMapper.getBatch(batchName);
	}

	/**
	 * 获取批次的Tree信息
	 * @param 
	 * @return Tree<BatchDO>
	 */
	@Override
	public Tree<BatchDO> getBatchTree() {
		logger.info("BatchServiceImpl.getBatchTree() start");
		// 创建一个list集合，存储节点数据
		List<Tree<BatchDO>> trees = new ArrayList<Tree<BatchDO>>();
		// 获取批次数据
		List<BatchDO> batchs = batchMapper.list(new HashMap<String, Object>());
		// 遍历批次
		for (BatchDO batch : batchs) {
			// 创建一个节点对象
			Tree<BatchDO> tree = new Tree<BatchDO>();
			// 将批次的ID作为节点ID
			tree.setId(batch.getBatchId().toString());
			// 只显示批次列表，所以，所有parentId均为0
			tree.setParentId("0");
			// 将测试任务的名称作为节点名称
			tree.setText(batch.getBatchName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("opend", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<BatchDO> t = BuildTree.build(trees);
		t.setText("批次信息");
		logger.info("BatchServiceImpl.getBatchTree() end");
		return t;
	}

}
