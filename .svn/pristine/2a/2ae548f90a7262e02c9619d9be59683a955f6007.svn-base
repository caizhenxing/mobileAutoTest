package com.bmtc.script.service.Impl;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.SVNException;

import com.alibaba.fastjson.JSON;
import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.common.utils.Query;
import com.bmtc.device.domain.TestCaseTable;
import com.bmtc.device.service.TestCaseService;
import com.bmtc.script.dao.ScriptDao;
import com.bmtc.script.domain.Script;
import com.bmtc.script.service.ScriptService;
import com.bmtc.svn.service.UpdateLocalCodeBySvnRepoService;
import com.bmtc.system.domain.BatchDO;
import com.bmtc.system.domain.DeptDO;
import com.bmtc.system.service.BatchService;
import com.bmtc.system.service.DeptService;
import com.bmtc.task.dao.ExecutePlanScriptDao;
import com.bmtc.task.domain.BMTCTask;
import com.bmtc.task.domain.ExecutePlanScriptDO;
import com.bmtc.task.service.BMTCTaskService;
import com.bmtc.task.service.ExecutePlanService;

/**
 * 脚本管理的service实现类
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ScriptServiceImpl implements ScriptService {

	private static Logger logger = Logger.getLogger(ScriptServiceImpl.class);
	@Autowired
	ScriptDao scriptMapper;
	@Autowired
	ExecutePlanScriptDao executePlanScriptMapper;
	@Autowired
	BMTCTaskService bmtcTaskService;
	@Autowired
	DeptService deptService;
	@Autowired
	BatchService batchService;
	@Autowired
	ExecutePlanService executePlanService;
	@Autowired
	BMTCConfig bmtcConfig;
	@Autowired
	TestCaseService testCaseService;
	@Autowired
	private UpdateLocalCodeBySvnRepoService updateLocalCodeBySvnRepoService;

	/**
	 * 查询脚本列表数据
	 * @param Query query
	 * @return List<Script>
	 */
	@Override
	public List<Script> list(Query query) {
		logger.info("ScriptServiceImpl.list() start");
		List<Script> scripts = scriptMapper.list(query);
		logger.info("ScriptServiceImpl.list() end");
		return scripts;
	}

	/**
	 * 查询总记录数
	 * @param Query query
	 * @return int
	 */
	@Override
	public int count(Query query) {
		logger.info("ScriptServiceImpl.count() start");
		int count = scriptMapper.count(query);
		logger.info("ScriptServiceImpl.count() end");
		return count;
	}

	/**
	 * 更新数据库脚本信息
	 * 
	 * @param
	 * @return boolean
	 */
	@Override
	public boolean updateScriptData(DeptDO deptDO) {
		logger.info("ScriptServiceImpl.updateScriptData() start");
		// 调用svn接口，扫描svn库，下载库中测试套到本地
		long scriptVersion = 0;
		// 下载的Url
		deptDO = deptService.get(deptDO.getDeptId());
		String url = deptDO.getSvnName();
		// 下载到本地的地址
		String downLoadPath = bmtcConfig.getLocalPath() + "/"
				+ deptDO.getName();
		File file = new File(downLoadPath);
		// 如果文件夹不存在，创建文件夹
		if (!file.exists()) {
			file.mkdir();
		}
		try {
			// 下载脚本
			scriptVersion = updateLocalCodeBySvnRepoService
					.updateLocalCodeBySvnRepo(url, bmtcConfig.getUsername(),
							bmtcConfig.getPassword(), file, null);
			logger.info("测试脚本版本 {" + scriptVersion + "}");
			// 获取库里所有的脚本
			List<Script> scriptList = scriptMapper
					.list(new HashMap<String, Object>());
			List<Script> scripts = new ArrayList<Script>();
			// 全量直接解析入库，增量根据操作类型，调整数据库数据，然后同步
			if (!parseScript(file, scripts)) {
				logger.error("脚本信息同步数据库异常，请检查更新数据库脚本信息逻辑");
				// 返回同步跟新失败
				return false;
			}
			// 遍历list,删除库中多余元素
			for (Script script : scriptList) {
				boolean flag = false;
				for (Script s : scripts) {
					if (script.getTestSuitPath().equals(s.getTestSuitPath())) {
						flag = true;
					}
				}
				if (!flag) {
					scriptMapper.remove(script.getScriptId());
				}
			}
			logger.info("ScriptServiceImpl.updateScriptData() end");
			// 返回同步跟新成功
			return true;
		} catch (SVNException e) {
			logger.error("svn下载脚本异常，请检查svn参数是否正确");
			e.printStackTrace();
			// 返回同步跟新失败
			return false;
		}
	}
	/**
	 * 遍历下载的脚本，更新数据库脚本信息
	 * @param File file,List<Script> list
	 * @return boolean
	 */
	private boolean parseScript(File file, List<Script> list) {
		// 获取该路径下的文件及文件夹
		File[] files = file.listFiles();
		// 遍历
		for (File f : files) {
			// 如果是文件
			if (f.isFile()) {
				String absolutePath = f.getAbsolutePath().replace("\\", "/");
				// 如果以".txt"结尾
				if (absolutePath.endsWith(".txt")) {
					// 查询数据库是否存在这条数据
					Script script = scriptMapper
							.getScriptByTestSuitPath(absolutePath);
					// 如果script不为空,修改
					if (script != null) {
						// 解析测试套
						List<TestCaseTable> caseNames = testCaseService
								.getTestCaseName(absolutePath);
						// 将List<TestCaseTable>转换为JSON字符串
						String testCaseInfo = JSON.toJSONString(caseNames);
						String scriptName = absolutePath.substring(absolutePath
								.lastIndexOf("/") + 1);
						// 封装脚本实例
						script.setTestCaseInfo(testCaseInfo);
						script.setGmtModified(new Date(System
								.currentTimeMillis()));
						script.setScriptName(scriptName);
						script.setTestSuitPath(absolutePath);
						scriptMapper.update(script);
						list.add(script);
					} else {// 如果script为空，添加
						script = new Script();
						// 解析测试套
						List<TestCaseTable> caseNames = testCaseService
								.getTestCaseName(absolutePath);
						if (caseNames.size() == 0) {
							// 如果脚本中没有case，不存如脚本库
							continue;
						} else {
							// 将List<TestCaseTable>转换为JSON字符串
							String testCaseInfo = JSON.toJSONString(caseNames);
							String scriptName = absolutePath
									.substring(absolutePath.lastIndexOf("/") + 1);
							// 封装脚本实例
							script.setTestCaseInfo(testCaseInfo);
							script.setScriptName(scriptName);
							script.setGmtModified(new Date(System
									.currentTimeMillis()));
							script.setTestSuitPath(absolutePath);
							scriptMapper.save(script);
						}
					}
				}
			} else {// 如果是文件夹
				parseScript(f, list);
			}
		}
		return true;
	}
	// 通过id查询脚本信息
	/**
	 * 
	 * @param Long scriptId
	 * @return Script
	 */
	@Override
	public Script get(Long scriptId) {
		logger.info("ScriptServiceImpl.get() start");
		Script script = scriptMapper.get(scriptId);
		logger.info("ScriptServiceImpl.get() end");
		return script;
	}

	/**
	 * 测试任务添加页面查询关联脚本数据(树形结构)(add.html)
	 * @param 
	 * @return Tree<Script>
	 */
	@Override
	public Tree<Script> getTree() {
		logger.info("ScriptServiceImpl.getTree() start");
		// 创建一个list集合，存储节点数据
		List<Tree<Script>> trees = new ArrayList<Tree<Script>>();
		// 数据库查询脚本信息
		List<Script> scripts = scriptMapper
				.list(new HashMap<String, Object>(16));
		// 遍历脚本信息
		for (Script script : scripts) {
			// 创建一个节点对象
			Tree<Script> tree = new Tree<Script>();
			// 将脚本的ID作为
			tree.setId(script.getScriptId().toString());
			// 只显示脚本列表，所以，所有parentId均为0
			tree.setParentId("0");
			// 将脚本的名称作为节点名称
			tree.setText(script.getScriptName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("closed", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Script> t = BuildTree.build(trees);
		logger.info("ScriptServiceImpl.getTree() end");
		return t;
	}
	/**
	 * 查询脚本关联的casename(树形结构)(script.html)
	 * @param Long scriptId
	 * @return Tree<String>
	 */
	@Override
	public Tree<String> getTree(Long scriptId) {
		logger.info("ScriptServiceImpl.getTree(Long scriptId) start");
		// 创建一个list集合，存储节点数据
		List<Tree<String>> trees = new ArrayList<Tree<String>>();
		// 数据库查询脚本关联案例信息
		Script script = scriptMapper.get(scriptId);
		String testCaseInfo = script.getTestCaseInfo();
		// 将JSON字符串转换为List<TestCase>
		List<TestCaseTable> testCaseTables = JSON.parseArray(testCaseInfo,
				TestCaseTable.class);
		// 遍历脚本信息
		for (TestCaseTable testCaseTable : testCaseTables) {
			// 创建一个节点对象
			Tree<String> tree = new Tree<String>();
			tree.setId(script.getTestSuitPath() + "/"
					+ testCaseTable.getCaseName());
			// 所有一级parentId均为0
			tree.setParentId("0");
			// 将案例的名称作为节点名称
			tree.setText(testCaseTable.getCaseName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
			// 获取testCaseNums
			List<String> testCaseNums = testCaseTable.getTestCaseNum();
			// 判断testCaseNums是否为空
			if (testCaseNums == null || testCaseNums.size() == 0) {
				continue;
			}
			// 遍历testCaseNums
			for (String testCaseNum : testCaseNums) {
				// 创建一个节点对象
				Tree<String> tree1 = new Tree<String>();
				tree1.setId(script.getTestSuitPath() + "/"
						+ testCaseTable.getCaseName() + "/" + testCaseNum);
				// parentId
				tree1.setParentId(script.getTestSuitPath() + "/"
						+ testCaseTable.getCaseName());
				// 将案例的名称作为节点名称
				tree1.setText(testCaseNum);
				// 创建Map存储节点状态：(opened/closed)
				Map<String, Object> state1 = new HashMap<>(16);
				state1.put("opened", true);
				tree1.setState(state1);
				// 将这个节点对象存储到trees集合中
				trees.add(tree1);
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<String> t = BuildTree.build(trees);
		logger.info("ScriptServiceImpl.getTree(Long scriptId) end");
		return t;
	}
	/**
	 * 查询测试任务列表页面关联脚本的展示信息(树形结构)(task.html)
	 * @param Long taskId
	 * @return Tree<Script>
	 */
	@Override
	public Tree<Script> getTaskScriptTreeData(Long id) {
		logger.info("ScriptServiceImpl.getTaskScriptTreeData() start");
		// 通过执行计划ID获取执行计划对象
		// ExecutePlan executePlan = executePlanService.get(id);
		// 获取该测试相关的脚本数据
		List<ExecutePlanScriptDO> executePlanScripts = executePlanScriptMapper
				.getExecutePlanScriptByExecutePlanId(id);
		// 创建一个list集合，存储节点数据
		if (executePlanScripts == null || executePlanScripts.size() == 0) {
			logger.info("ScriptServiceImpl.getTaskScriptTreeData() end");
			return null;
		}
		List<Tree<Script>> trees = new ArrayList<Tree<Script>>();
		// 遍历脚本信息
		for (ExecutePlanScriptDO executePlanScript : executePlanScripts) {
			// 创建一个节点对象
			Tree<Script> tree = new Tree<Script>();
			// 将脚本的ID作为
			tree.setId(executePlanScript.getScriptId().toString());
			// 父ID
			tree.setParentId("0");
			// 将脚本的名称作为节点名称
			Script script = scriptMapper.get(executePlanScript.getScriptId());
			tree.setText(script.getScriptName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("closed", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
			// 获取关联的caseNames
			String caseName = executePlanScript.getCheckedCaseName();
			// 如果未查到数据，继续下次循环
			if (caseName == null || "".equals(caseName)) {
				continue;
			}
			List<TestCaseTable> testCaseTables = JSON.parseArray(caseName,
					TestCaseTable.class);
			// 遍历caseNames
			for (TestCaseTable testCaseTable : testCaseTables) {
				// 创建一个节点对象
				Tree<Script> caseNameTree = new Tree<Script>();
				// 将"脚本+caseName"的caseNameID作为
				caseNameTree.setId(script.getScriptId().toString()
						+ testCaseTable.getCaseName());
				// 父ID为对应脚本ID
				caseNameTree.setParentId(script.getScriptId().toString());
				// 将caseName的名称作为节点名称
				caseNameTree.setText(testCaseTable.getCaseName());
				// 将这个节点对象存储到trees集合中
				trees.add(caseNameTree);
				// 遍历此caseName下的caseNum
				List<String> caseNums = testCaseTable.getTestCaseNum();
				for (String caseNum : caseNums) {
					// 创建一个节点对象
					Tree<Script> caseNumTree = new Tree<Script>();
					// 将"脚本+caseName"的caseNameID作为
					caseNumTree.setId(script.getScriptId().toString()
							+ testCaseTable.getCaseName() + caseNum);
					// 父ID为对应脚本ID
					caseNumTree.setParentId(script.getScriptId().toString()
							+ testCaseTable.getCaseName());
					// 将caseName的名称作为节点名称
					caseNumTree.setText(caseNum);
					// 将这个节点对象存储到trees集合中
					trees.add(caseNumTree);
				}
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Script> t = BuildTree.build(trees);
		logger.info("ScriptServiceImpl.getTaskScriptTreeData() end");
		return t;
	}
	/**
	 * 获取测试任务关联脚本Tree信息(task/add.js)
	 * @param String svnName
	 * @return List<Tree<String>>
	 */
	@Override
	public List<Tree<String>> getTreeData(String id) {
		logger.info("ScriptServiceImpl.getTreeData() start");
		// 通过taskId获得对应的task对象
		Long taskId = Long.valueOf(id);
		BMTCTask bmtcTask = bmtcTaskService.get(taskId);
		BatchDO batchDO = batchService.get(Integer.valueOf(bmtcTask
				.getBatchId().toString()));
		String svnPath = bmtcTask.getSvnPath() + "/"
				+ batchDO.getBatchSvnPath();
		// 创建List<Tree>存储测试任务相关的脚本数据
		List<Tree<String>> trees = new ArrayList<Tree<String>>();
		// svn路径和本地路径转换
		String filePath = svnPath.replace(bmtcConfig.getUrl(),
				(bmtcConfig.getLocalPath() + "/" + bmtcTask.getDeptName()))
				.replace("\\", "/");
		// 从配置文件常量中获取SvnRpoPath
		File file = new File(filePath);
		// 解析此路径下所有文件为树形结构
		List<Tree<String>> tree = pathToTree(file, trees);
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<String>> t = BuildTree
				.buildList(tree, file.getAbsolutePath());
		logger.info("ScriptServiceImpl.getTreeData() end");
		return t;
	}

	/**
	 * 将路径拆分转换为存在父子关系的List<PathTree>
	 * @param File file,List<Tree<String>> trees
	 * @return List<Tree<String>>
	 */
	public List<Tree<String>> pathToTree(File file, List<Tree<String>> trees) {
		logger.info("ScriptServiceImpl.pathToTree() start");
		Tree<String> root = new Tree<String>();
		root.setId(file.getAbsolutePath());
		root.setParentId(file.getParentFile().getAbsolutePath());
		root.setText(file.getName());
		// 获取指定SVN路径下的所有子文件
		File[] files = file.listFiles();
		// 遍历子文件集
		for (int i = 0; i < files.length; i++) {
			// 跳过".svn"文件夹、
			if (".svn".equals(files[i].getName())) {
				continue;
			}
			// 判断此元素是文件还是文件夹
			if (files[i].isFile()) { // 如果是文件，存储到List<PathTree>
				// 解析此文件下的caseName
				List<TestCaseTable> testCaseInfo = testCaseService
						.getTestCaseName(files[i].getAbsolutePath());
				if (testCaseInfo == null || testCaseInfo.size() == 0) {
					continue;
				}
				// 转换数据为PathTree
				Tree<String> pathTree = getPathTree(files[i]);
				// 将根节点存到集合中
				trees.add(pathTree);
				// 遍历存储caseName
				for (TestCaseTable testCaseTable : testCaseInfo) {
					Tree<String> tree = new Tree<String>();
					tree.setId(files[i].getAbsolutePath() + "/"
							+ testCaseTable.getCaseName());
					tree.setParentId(files[i].getAbsolutePath());
					tree.setText(testCaseTable.getCaseName());
					trees.add(tree);
					List<String> testCaseNums = testCaseTable.getTestCaseNum();
					// 判断testCaseNums是否为空
					if (testCaseNums == null || testCaseNums.size() == 0) {
						continue;
					}
					// 遍历存储CaseNum
					for (String testCaseNum : testCaseNums) {
						Tree<String> t = new Tree<String>();
						t.setId(files[i].getAbsolutePath() + "/"
								+ testCaseTable.getCaseName() + "/"
								+ testCaseNum);
						t.setParentId(files[i].getAbsolutePath() + "/"
								+ testCaseTable.getCaseName());
						t.setText(testCaseNum);
						trees.add(t);
					}
				}
			} else { // 如果是文件夹，存储到List<PathTree>并递归
				// 转换数据为PathTree
				Tree<String> pathTree = getPathTree(files[i]);
				// 将根节点存到集合中
				trees.add(pathTree);
				// 递归调用自身，解析路径
				pathToTree(files[i], trees);
			}
		}
		logger.info("ScriptServiceImpl.pathToTree() end");
		return trees;
	}

	/**
	 * 转换数据为PathTree
	 * @param File file
	 * @return Tree<String>
	 */
	private static Tree<String> getPathTree(File file) {
		// 创建pathTree对象
		Tree<String> tree = new Tree<String>();
		// 主键为其绝对路径
		tree.setId(file.getAbsolutePath());
		// 父id为其父目录的绝对路径
		tree.setParentId(file.getParentFile().getAbsolutePath());
		// 展示文本为目录名称
		tree.setText(file.getName());
		// 返回结果
		return tree;
	}
}
