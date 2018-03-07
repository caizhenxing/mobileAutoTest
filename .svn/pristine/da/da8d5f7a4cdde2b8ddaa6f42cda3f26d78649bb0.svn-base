package com.bmtc.scene.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.scene.domain.Scene;
import com.bmtc.scene.service.SceneService;

/**
 * 场景管理的controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scene")
public class SceneController {

	// 返回映射路径的前缀
	private String prefix = "scene";
	@Autowired
	SceneService sceneService;
	/**
	 * 前段请求访问场景管理列表页面
	 * @return
	 */
	@RequiresPermissions("test:scene:scene")
	@GetMapping()
	String scene() {
		return prefix + "/scene";
	}
	/**
	 * 查询场景数据
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	@RequiresPermissions("test:scene:list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 分页查询参数数据
		Query query = new Query(params);
		// 查询数据库场景数据
		List<Scene> scenes = sceneService.list(query);
		// 查询总记录数
		int total = sceneService.count(query);
		// 封装到返回对象
		PageUtils pageUtil = new PageUtils(scenes, total);
		// 返回数据
		return pageUtil;
	}
	/**
	 * 前段请求访问场景添加页面
	 * @param 
	 * @return
	 */
	@RequiresPermissions("test:scene:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}
	/**
	 * 前段请求访问场景编辑页面
	 * @param sceneId
	 * @return
	 */
	@RequiresPermissions("test:scene:edit")
	@GetMapping("/edit/{sceneId}")
	String edit(Model model, @PathVariable("sceneId") Long sceneId) {
		Scene scene = sceneService.get(sceneId);
		model.addAttribute("scene", scene);
		return prefix+"/edit";
	}
	/**
	 * 测试任务添加页面打开关联场景页面
	 * @return
	 */
	@GetMapping("/scenes")
	String scenes() {
		return prefix + "/sceneTree";
	}
	/**
	 * 测试任务添加页面查询关联场景数据(树形结构)
	 * @return
	 */
	@GetMapping("/getSceneTree")
	@ResponseBody
	public Tree<Scene> getSceneTree() {
		Tree<Scene> tree = new Tree<Scene>();
		tree = sceneService.getTree();
		return tree;
	}
}
