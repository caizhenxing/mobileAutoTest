package com.bmtc.system.controller;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.system.domain.ConfigDO;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/sys/config")
@Controller
public class ConfigController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigController.class);
	
	private String prefix="system/config";
	
	@Autowired
	ConfigService configService;
	
	@RequiresPermissions("sys:config:config")
	@Log("跳转到系统配置界面")
	@GetMapping("")
	String config(Model model) {
		return prefix + "/config";
	}

	@GetMapping("/list")
	@Log("查询列表数据")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		logger.info("ConfigController.list() start");
		Query query = new Query(params);
		List<ConfigInfoDO> configInfoList = configService.list(query);
		int total = configService.count(query);
		PageUtils pageUtil = new PageUtils(configInfoList, total);
		logger.info("ConfigController.list() end");
		return pageUtil;
	}

	@RequiresPermissions("sys:config:edit")
	@Log("编辑系统配置信息")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		logger.info("ConfigController.edit() start");
		ConfigInfoDO configInfoDO = configService.get(id);
		model.addAttribute("config", configInfoDO);
		logger.info("ConfigController.edit() end");
		return prefix + "/edit";
	}
	
	@RequiresPermissions("sys:config:add")
	@Log("保存系统配置信息")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}
	
	@RequiresPermissions("sys:config:add")
	@Log("保存系统配置信息")
	@PostMapping("/save")
	@ResponseBody
	R save(ConfigDO config) {
		logger.info("ConfigController.save() start");
		// 对svn服务器登录口令进行加密存储
		config.setSvnServerPassword(EncryptUtil.encrypt(config.getSvnServerPassword()));
		if (configService.save(config) > 0) {
			logger.info("ConfigController.save() end");
			return R.ok();
		}
		logger.info("ConfigController.save() end");
		return R.error();
	}

	@RequiresPermissions("sys:config:edit")
	@Log("更新系统配置信息")
	@PostMapping("/update")
	@ResponseBody
	R update(ConfigDO config) {
		logger.info("ConfigController.update() start");
		// 对svn服务器登录口令进行加密存储
		config.setSvnServerPassword(EncryptUtil.encrypt(config.getSvnServerPassword()));
		if (configService.update(config) > 0) {
			logger.info("ConfigController.update() end");
			return R.ok();
		}
		logger.info("ConfigController.update() end");
		return R.error();
	}

	@RequiresPermissions("sys:config:remove")
	@Log("删除系统配置信息")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		logger.info("ConfigController.remove() start");
		if (configService.remove(id) > 0) {
			logger.info("ConfigController.remove() end");
			return R.ok();
		}
		logger.info("ConfigController.remove() end");
		return R.error();
	}

	@RequiresPermissions("sys:config:batchRemove")
	@Log("批量删除系统配置信息")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("configIds[]") Long[] configIds) {
		logger.info("ConfigController.batchRemove() start");
		int count = configService.batchRemove(configIds);
		if (count > 0) {
			logger.info("ConfigController.batchRemove() end");
			return R.ok();
		}
		logger.info("ConfigController.batchRemove() end");
		return R.error();
	}

}
