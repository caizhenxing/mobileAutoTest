package com.bmtc.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.ShiroUtils;
import com.bmtc.device.service.TestCaseService;
import com.bmtc.script.dao.ScriptDao;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.domain.UserDO;
import com.bmtc.system.service.DeptService;
import com.bmtc.system.service.UserService;
import com.bmtc.task.dao.BMTCTaskDao;
import com.bmtc.task.dao.TaskScriptDao;
import com.bmtc.task.domain.BMTCTask;
import com.bmtc.task.service.BMTCTaskService;

/**
 * 测试任务service实现类
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class BMTCTaskServiceImpl implements BMTCTaskService {

	private static Logger logger = Logger.getLogger(BMTCTaskServiceImpl.class);
	@Autowired
	BMTCTaskDao bMTCTaskMapper;
	@Autowired
	TaskScriptDao taskScriptMapper;
	@Autowired
	ScriptDao scriptMapper;
	@Autowired
	DeptService deptService;
	@Autowired
	UserService userService;
	@Autowired
	TestCaseService testCaseService;

	/**
	 * 查询测试任务数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<BMTCTask> list(Map<String, Object> params) {
		logger.info("BMTCTaskServiceImpl.list() start");
		// 获取当前登录用户
		UserDO user = ShiroUtils.getUser();
		// 判断是否为超级管理员
		List<BMTCTask> tasks = new ArrayList<BMTCTask>();
		if(user.getUsername() != null && "admin".equals(user.getUsername())){
			// 如果是，查询所有
			tasks = bMTCTaskMapper.list(params);
			logger.info("BMTCTaskServiceImpl.list() end");
		} else {
			// 获得该用户的关联的产品
			List<DeptDO> depts = userService.getProductsByUserId(user.getUserId());
			for (DeptDO deptDO : depts) {
				params.put("deptId", deptDO.getDeptId());
				// 查询指定产品下的测试任务
				List<BMTCTask> list = bMTCTaskMapper.list(params);
				tasks.addAll(list);
			}
			logger.info("BMTCTaskServiceImpl.list() end");
		}
		return tasks;
	}

	/**
	 * 查询总记录数
	 * 
	 * @param query
	 * @return int
	 */
	@Override
	public int count(Query query) {
		logger.info("BMTCTaskServiceImpl.count() start");
		int count = bMTCTaskMapper.count(query);
		logger.info("BMTCTaskServiceImpl.count() end");
		return count;
	}

	/**
	 * 测试任务批量删除
	 * 
	 * @param taskIds
	 * @return int
	 */
	@Override
	public int batchremove(Long[] taskIds) {
		logger.info("BMTCTaskServiceImpl.batchremove() start");
		int status = bMTCTaskMapper.batchRemove(taskIds);
		logger.info("BMTCTaskServiceImpl.batchremove() end");
		return status;
	}

	/**
	 * 通过ID查询测试任务信息
	 * 
	 * @param taskId
	 * @return BMTCTask
	 */
	@Override
	public BMTCTask get(Long taskId) {
		logger.info("BMTCTaskServiceImpl.get() start");
		BMTCTask bmtcTask = bMTCTaskMapper.get(taskId);
		logger.info("BMTCTaskServiceImpl.get() end");
		return bmtcTask;
	}

	/**
	 * 查询测试任务名称是否存在
	 * 
	 * @param params
	 * @return boolean
	 */
	@Override
	public boolean exist(Map<String, Object> params) {
		logger.info("BMTCTaskServiceImpl.exist() start");
		List<BMTCTask> list = bMTCTaskMapper.list(params);
		if (list.size() == 0) {
			// 不存在
			logger.info("BMTCTaskServiceImpl.exist() end");
			return false;
		} else {
			// 存在
			logger.info("BMTCTaskServiceImpl.exist() end");
			return true;
		}
	}

	/**
	 * 保存测试任务
	 * 
	 * @param bmtcTask
	 * @return int
	 */
	@Override
	public int save(BMTCTask bmtcTask) {
		logger.info("BMTCTaskServiceImpl.save() start");
		// 创建人
		if (bmtcTask.getCreatedId() == null) {
			bmtcTask.setCreatedId(ShiroUtils.getUser().getUserId());
		}
		// 获取现在时间,使用基类获取
		Date date = new Date(System.currentTimeMillis());
		// 创建时间
		if (bmtcTask.getGmtCreate() == null) {
			bmtcTask.setGmtCreate(date);
		}
		// 修改时间
		bmtcTask.setGmtModified(date);
		// 保存测试任务
		int count = bMTCTaskMapper.save(bmtcTask);
		logger.info("BMTCTaskServiceImpl.save() end");
		return count;
	}

	/**
	 * 修改测试任务
	 * 
	 * @param bMTCTask
	 * @return int
	 */
	@Override
	public int update(BMTCTask bMTCTask) {
		logger.info("BMTCTaskServiceImpl.update() start");
		// 获取现在时间
		Date date = new Date(System.currentTimeMillis());
		// 设置修改时间
		bMTCTask.setGmtModified(date);
		// 修改测试任务
		int count = bMTCTaskMapper.update(bMTCTask);
		logger.info("BMTCTaskServiceImpl.update() end");
		return count;
	}

	
	
	/**
	 * 测试任务删除
	 * 
	 * @param taskId
	 * @return int
	 */
	@Override
	public int remove(Long taskId) {
		logger.info("BMTCTaskServiceImpl.remove() start");
		// 删除测试任务
		int count = bMTCTaskMapper.remove(taskId);
		logger.info("BMTCTaskServiceImpl.remove() end");
		return count;
	}

	/**
	 * 查询用户所属产品下的任务列表
	 * 
	 * @return List<BMTCTask>
	 */
	@Override
	public Tree<BMTCTask> getTaskList() {
		logger.info("BMTCTaskServiceImpl.getTaskList() start");
		// 创建一个list集合，存储节点数据
		List<Tree<BMTCTask>> trees = new ArrayList<Tree<BMTCTask>>();
		// 获取测试任务数据
		List<BMTCTask> taskList = list(new HashMap<String, Object>());
		// 遍历测试任务
		for (BMTCTask bmtcTask : taskList) {
			// 创建一个节点对象
			Tree<BMTCTask> tree = new Tree<BMTCTask>();
			// 将测试任务的ID作为节点ID
			tree.setId(bmtcTask.getTaskId().toString());
			// 只显示测试任务列表，所以，所有parentId均为0
			tree.setParentId("0");
			// 将测试任务的名称作为节点名称
			tree.setText(bmtcTask.getTaskName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("opend", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<BMTCTask> t = BuildTree.build(trees);
		logger.info("BMTCTaskServiceImpl.getTaskList() start");
		return t;
	}

	/**
	 * 获取用户所属的产品的树形结构数据
	 * 
	 * @param 
	 * @return Tree<DeptDO>
	 */
	@Override
	public Tree<DeptDO> getDeptTree() {
		logger.info("BMTCTaskServiceImpl.getDeptTree() start");
		// 获得用户所属的产品
		UserDO user = ShiroUtils.getUser();
		List<DeptDO> depts = new ArrayList<DeptDO>();
		if("admin".equals(user.getUsername())){
			Tree<DeptDO> tree = deptService.getTree();
			logger.info("BMTCTaskServiceImpl.getDeptTree() end");
			return tree;
		} else {
			depts = userService.getProductsByUserId(user.getUserId());
		}
		// 创建一个list集合，存储节点数据
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		// 封装树形结构数据
		for (DeptDO deptDO : depts) {
			// 创建一个节点对象
			Tree<DeptDO> tree = new Tree<DeptDO>();
			// 将产品机构的ID作为节点ID
			tree.setId(deptDO.getDeptId().toString());
			// 只显示产品机构列表，所以，所有parentId均为0
			tree.setParentId("0");
			// 将产品机构的名称作为节点名称
			tree.setText(deptDO.getName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("opend", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
		}
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		t.setText("中国银行软件中心");
		logger.info("BMTCTaskServiceImpl.getDeptTree() end");
		return t;
	}

	/**
	 * 获得某产品对应的SVN库路径
	 * 
	 * @param String deptId
	 * @return String
	 */
	@Override
	public String getProductSvnPath(String deptId) {
		// 通过产品ID获得产品对象
		DeptDO deptDO = deptService.get(Long.valueOf(deptId));
		return deptDO.getSvnName();
	}
}