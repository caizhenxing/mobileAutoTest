package com.bmtc.task.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.utils.Query;
import com.bmtc.device.domain.Device;
import com.bmtc.device.domain.TestCase;
import com.bmtc.device.utils.HttpRequestUtils;
import com.bmtc.script.dao.ScriptDao;
import com.bmtc.script.domain.Script;
import com.bmtc.system.dao.DeptDao;
import com.bmtc.system.service.UserService;
import com.bmtc.task.dao.BMTCTaskDao;
import com.bmtc.task.dao.ExecutePlanDao;
import com.bmtc.task.dao.ExecutePlanScriptDao;
import com.bmtc.task.domain.BMTCTask;
import com.bmtc.task.domain.ExecuteDetail;
import com.bmtc.task.domain.ExecutePlan;
import com.bmtc.task.domain.ExecutePlanScriptDO;
import com.bmtc.task.domain.ExecutePlanVo;
import com.bmtc.task.domain.ProductSvn;
import com.bmtc.task.service.BMTCTaskService;
import com.bmtc.task.service.ExecutePlanService;
import com.bmtc.task.utils.TestCaseUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * 测试任务执行计划的service接口实现类
 * @author Administrator
 *
 */
@Service
@Transactional
public class ExecutePlanServiceImpl implements ExecutePlanService{

	private static Logger logger = Logger.getLogger(ExecutePlanServiceImpl.class);
	
	@Autowired
	ExecutePlanDao executePlanMapper;
	@Autowired
	BMTCConfig bmtcConfig;
	@Autowired
	ExecutePlanScriptDao executePlanScriptMapper;
	@Autowired
	BMTCTaskDao bmtcTaskMapper;
	@Autowired
	BMTCTaskService bmtcTaskService;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	UserService userService;
	@Autowired
	ScriptDao scriptMapper;
	
	/**
	 * 查询执行计划列表数据
	 * @param query
	 * @return List<ExecutePlan>
	 */
	@Override
	public List<ExecutePlan> list(Query query) {
		logger.info("ExecutePlanServiceImpl.list() start");
		// 获得该用户所属的产品ID
		List<BMTCTask> tasks = bmtcTaskService.list(new HashMap<String, Object>());
		List<ExecutePlan> executePlans = new ArrayList<ExecutePlan>();
		for (BMTCTask bmtcTask : tasks) {
			List<ExecutePlan> list = executePlanMapper.getExecutePlanByTaskId(bmtcTask.getTaskId());
			executePlans.addAll(list);
		}
		List<ExecutePlan> results = new ArrayList<ExecutePlan>();
		// 遍历，添加deptName、batchName信息
		for (ExecutePlan executePlan : executePlans) {
			BMTCTask bmtcTask = bmtcTaskMapper.get(executePlan.getTaskId());
			executePlan.setBatchId(bmtcTask.getBatchId());
			executePlan.setBatchName(bmtcTask.getBatchName());
			executePlan.setDeptId(bmtcTask.getDeptId());
			executePlan.setDeptName(bmtcTask.getDeptName());
			results.add(executePlan);
		}
		logger.info("ExecutePlanServiceImpl.list() end");
		return results;
	}
	/**
	 * 查询执行计划总记录数
	 * @param query
	 * @return int
	 */
	@Override
	public int count(Query query) {
		logger.info("ExecutePlanServiceImpl.count() start");
		int count = executePlanMapper.count(query);
		logger.info("ExecutePlanServiceImpl.count() end");
		return count;
	}
	/**
	 * 通过id查询执行计划
	 * @param id
	 * @return ExecutePlan
	 */
	@Override
	public ExecutePlan get(Long id) {
		logger.info("ExecutePlanServiceImpl.get() start");
		ExecutePlan executePlan = executePlanMapper.get(id);
		BMTCTask bmtcTask = bmtcTaskService.get(executePlan.getTaskId());
		executePlan.setDeptId(bmtcTask.getDeptId());
		executePlan.setDeptName(bmtcTask.getDeptName());
		executePlan.setBatchId(bmtcTask.getBatchId());
		executePlan.setBatchName(bmtcTask.getBatchName());
		executePlan.setSvnPath(bmtcTask.getSvnPath());
		logger.info("ExecutePlanServiceImpl.get() end");
		return executePlan;
	}
	/**
	 * 保存执行计划
	 * @param ExecutePlan executePlan
	 * @return int
	 */
	@Override
	public int save(ExecutePlan executePlan) {
		logger.info("ExecutePlanServiceImpl.save() start");
		int count = executePlanMapper.save(executePlan); 
		/**
		 * 维护测试任务与脚本的关联关系
		 */
		// 获得关联测试套路径集
		String testSuiteCaseNames = executePlan.getTestSuiteCaseNames();
		// 如果未关联脚本，直接返回
		if (testSuiteCaseNames == null) {
			logger.info("ExecutePlanServiceImpl.save() end");
			return count;
		}
		List<TestCase> testCases = TestCaseUtils.getTestCases(testSuiteCaseNames);
		// 如果关联的均为无效测试套，直接返回
		if (testCases == null) {
			logger.info("ExecutePlanServiceImpl.save() end");
			return count;
		}
		// 遍历testCases,维护测试任务和脚本的关联关系
		for (TestCase testCase : testCases) {
			// 获取每个测试套的id
			Script script = scriptMapper.getScriptByTestSuitPath(testCase
					.getTestSuite());
			ExecutePlanScriptDO executePlanScriptDO = new ExecutePlanScriptDO(executePlan.getId(), script.getScriptId(), JSON.toJSONString(testCase.getCaseName()));
			// 保存信息到中间表
			executePlanScriptMapper.save(executePlanScriptDO);
		}
		logger.info("ExecutePlanServiceImpl.save() end");
		return count;
	}
	/**
	 * 执行计划修改
	 * @param ExecutePlan executePlan
	 * @return R
	 */
	@Override
	public int update(ExecutePlan executePlan) {
		logger.info("ExecutePlanServiceImpl.update() start");
		if("0".equals(executePlan.getTriggerMode())){
			executePlan.setCondition("");
		}
		int update = executePlanMapper.update(executePlan);
		/**
		 * 维护测试任务与脚本的关联关系
		 */
		// 首先删除修改之前的中间表关联关系
		executePlanScriptMapper.remove(executePlan.getId());
		// 获得关联测试套路径集
		String testSuiteCaseNames = executePlan.getTestSuiteCaseNames();
		// 如果未关联脚本，直接返回
		if (testSuiteCaseNames == null) {
			logger.info("ExecutePlanServiceImpl.update() end");
			return update;
		}
		List<TestCase> testCases = TestCaseUtils.getTestCases(testSuiteCaseNames);
		// 如果关联的均为无效测试套，直接返回
		if (testCases == null) {
			logger.info("ExecutePlanServiceImpl.update() end");
			return update;
		}
		// 遍历testCases,维护测试任务和脚本的关联关系
		for (TestCase testCase : testCases) {
			// 获取每个测试套的id
			Script script = scriptMapper.getScriptByTestSuitPath(testCase
					.getTestSuite());
			ExecutePlanScriptDO executePlanScriptDO = new ExecutePlanScriptDO(executePlan.getId(), script.getScriptId(), JSON.toJSONString(testCase.getCaseName()));
			// 保存信息到中间表
			executePlanScriptMapper.save(executePlanScriptDO);
		}
		logger.info("ExecutePlanServiceImpl.update() end");
		return update;
	}
	/**
	 * 执行计划批量删除
	 * @param Long[] ids
	 * @return int
	 */
	@Override
	public int batchremove(Long[] ids) {
		// 删除执行计划与脚本的关联关系
		for (Long id : ids) {
			executePlanScriptMapper.remove(id);
		}
		int status = executePlanMapper.batchRemove(ids);
		return status;
	}
	/**
	 * 执行计划删除
	 * @param Long id
	 * @return int
	 */
	@Override
	public int remove(Long id) {
		// 删除执行计划
		int remove = executePlanMapper.remove(id);
		// 删除执行计划和脚本的关联关系
		executePlanScriptMapper.remove(id);
		return remove;
	}
	
	/**
	 * 执行计划执行测试任务(本地)
	 * @param Long id
	 * @return String
	 */
	@Override
	public String active(Long id) {
		logger.info("ExecutePlanServiceImpl.active() start");
		// 通过id查询执行计划
		ExecutePlan executePlan = executePlanMapper.get(id);
		// 获取设备信息:"http://22.11.27.29/bmtc/android/info"
		JSONObject httpget = HttpRequestUtils.httpget(bmtcConfig.getDeviceInfoAdd());
		// 判断
		if(httpget == null) {
			return "获取设备信息异常！";
		}
		// 将获得的JSON格式的设备信息封装到JSONObject对象
		JSONObject object = new JSONObject(httpget);
		// 获取其中的data信息
		String data = object.getString("data");
		// 创建gson对象
		Gson gson = new Gson();
		// 将data使用gson转化为Device对象
		@SuppressWarnings("serial")
		List<Device> devices = gson.fromJson(data, new TypeToken<List<Device>>(){}.getType());
		// 判断devices是否为空
		if(devices == null){
			logger.info("ExecutePlanServiceImpl.active() end");
			// 获取的设备信息为空，即未检测到可执行设备
			return "未检测到可执行设备！";
		}
		// 如果不为空，遍历设备信息
		for (Device device : devices) {
			// 判断设备状态，是否繁忙
			if(device.getStatus() == "1"){// 繁忙
				continue;
			}
			List<ExecuteDetail> executeDetails = new ArrayList<ExecuteDetail>();
			// 获取执行任务关联的脚本集
			List<Script> scripts = executePlanScriptMapper.getScript(id);
			// 创建ProductSvn
			ProductSvn productSvn = new ProductSvn();
			/*productSvn.setProductName(deptMapper.get(ShiroUtils.getUser().getDeptId()).getName());*/
			productSvn.setUsername(bmtcConfig.getUsername());
			productSvn.setPassword(bmtcConfig.getPassword());
			productSvn.setRepository(bmtcConfig.getUrl());
			// 遍历脚本集
			for (Script script : scripts) {
				// 创建ExecuteDetail对象
				ExecuteDetail executeDetail = new ExecuteDetail();
				// token
				executeDetail.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
				// taskId
				executeDetail.setTaskId(executePlan.getTaskId());
				// 设备版本
				executeDetail.setVersion(device.getVerison());
				// 设备的udid
				executeDetail.setUdid(device.getUdid());
				// 执行明细id
				executeDetail.setExecutePlanId(executePlan.getId());
				executeDetail.setProductSvn(productSvn);
				// 测试套路径
				String testSuitPath = script.getTestSuitPath();
				String test = testSuitPath.replace(bmtcConfig.getLocalPath(),bmtcConfig.getUrl());
				executeDetail.setTestSuitePath(test);
				executeDetails.add(executeDetail);
			}
			// 此值原为token,现有所更改，后维护
			HttpRequestUtils.httpPost(bmtcConfig.getExecuteAdd(), JSON.toJSON(executeDetails).toString(), false);
			// 修改执行计划状态为执行中,0，空闲；1，执行中；
			executePlan.setStatus(1l);
			executePlanMapper.update(executePlan);
		}
		logger.info("ExecutePlanServiceImpl.active() end");
		return "开始执行！";
	}
	/**
	 * 发执行计划给ATP
	 * @param Long id
	 * @return String
	 */
	@Override
	public String sendToATP(Long id) {
		logger.info("ExecutePlanServiceImpl.sendToATP() start");
		// TODO
		// 通过id查询执行计划
		@SuppressWarnings("unused")
		ExecutePlan executePlan = executePlanMapper.get(id);
		@SuppressWarnings("unused")
		ExecutePlanVo executePlanVo = new ExecutePlanVo();
		logger.info("ExecutePlanServiceImpl.sendToATP() end");
		return null;
	}
}