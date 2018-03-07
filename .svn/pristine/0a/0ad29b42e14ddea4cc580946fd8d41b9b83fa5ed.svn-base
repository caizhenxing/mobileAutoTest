package com.bmtc.scene.service;

import java.util.List;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.Query;
import com.bmtc.scene.domain.Scene;

/**
 * 场景管理的service接口
 * @author Administrator
 *
 */
public interface SceneService {

	// 查询数据库场景数据
	List<Scene> list(Query query);
	// 查询场景总记录数
	int count(Query query);
	// 通过id查询脚本信息
	Scene get(Long sceneId);
	// 查询关联场景数据(树形结构)
	Tree<Scene> getTree();

}
