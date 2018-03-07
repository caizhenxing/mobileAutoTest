package com.bmtc.scene.service.impl;

import java.util.ArrayList;
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
import com.bmtc.scene.dao.SceneDao;
import com.bmtc.scene.domain.Scene;
import com.bmtc.scene.service.SceneService;
/**
 * 场景管理的service实现类
 * @author Administrator
 *
 */
@Service
@Transactional
public class SceneServiceImpl implements SceneService {

	private static Logger logger = Logger.getLogger(SceneServiceImpl.class);
	@Autowired
	SceneDao sceneMapper;
	/**
	 * 查询场景数据
	 * @param query
	 * @return
	 */
	@Override
	public List<Scene> list(Query query) {
		logger.info("SceneServiceImpl.list() start");
		List<Scene> scenes = sceneMapper.list(query);
		logger.info("SceneServiceImpl.list() end");
		return scenes;
	}
	/**
	 * 查询场景记录数
	 * @param query
	 * @return
	 */
	@Override
	public int count(Query query) {
		logger.info("SceneServiceImpl.count() start");
		int count = sceneMapper.count(query);
		logger.info("SceneServiceImpl.count() end");
		return count;
	}
	/**
	 * 通过id查询脚本信息
	 * @param sceneId
	 * @return
	 */
	@Override
	public Scene get(Long sceneId) {
		logger.info("SceneServiceImpl.get() start");
		Scene scene = sceneMapper.get(sceneId);
		logger.info("SceneServiceImpl.get() end");
		return scene;
	}
	/**
	 * 测试任务添加页面查询关联场景数据(树形结构)
	 * @param 
	 * @return
	 */
	@Override
	public Tree<Scene> getTree() {
		logger.info("SceneServiceImpl.getTree() start");
		// 创建一个list集合，存储节点数据
		List<Tree<Scene>> trees = new ArrayList<Tree<Scene>>();
		// 数据库查询场景信息
		List<Scene> scenes = sceneMapper.list(new HashMap<String,Object>(16));
		// 遍历场景信息
		for (Scene scene : scenes) {
			// 创建一个节点对象
			Tree<Scene> tree = new Tree<Scene>();
			// 将场景的ID作为
			tree.setId(scene.getSceneId().toString());
			// 只显示场景列表，所以，所有parentId均为0
			tree.setParentId("0");
			// 将场景的名称作为节点名称
			tree.setText(scene.getSceneName());
			// 创建Map存储节点状态：(opened/closed)
			Map<String, Object> state = new HashMap<>(16);
			state.put("closed", true);
			tree.setState(state);
			// 将这个节点对象存储到trees集合中
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Scene> t = BuildTree.build(trees);
		logger.info("SceneServiceImpl.getTree() end");
		return t;
	}
	
}
