package com.bmtc.system.controller;

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.controller.BaseController;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.R;
import com.bmtc.system.domain.BatchDO;
import com.bmtc.system.service.BatchService;

/**
 * 批次管理
 * 
 * @author nienannan
 *
 */
@RequestMapping("/sys/batch")
@Controller
public class BatchController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(BatchController.class);

	String prefix = "system/batch";

	@Autowired
	BatchService batchService;

	@GetMapping()
	@Log("跳转到批次管理界面")
	String batch() {
		logger.info("BatchController.batch() start");
		logger.info("BatchController.batch() end");
		return prefix + "/batch";
	}

	@GetMapping("/list")
	@Log("查询批次")
	@ResponseBody()
	List<BatchDO> list() {
		logger.info("BatchController.list() start");
		List<BatchDO> batch = batchService.list();
		logger.info("BatchController.list() end");
		return batch;
	}

	@ResponseBody
	@Log("保存批次")
	@PostMapping("/save")
	R save() {
		logger.info("BatchController.save() start");
		try {
			batchService.save();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.info("urllink为空或格式不正确");
			return R.error("服务器异常,请联系管理员");
		}
		logger.info("BatchController.save() end");
		return R.ok();
	}

	@GetMapping("/edit/{id}")
	@Log("编辑批次")
	String edit(@PathVariable("id") Integer id, Model model) {
		logger.info("BatchController.edit() start");
		BatchDO batchDO = batchService.get(id);
		model.addAttribute("batch", batchDO);
		logger.info("BatchController.edit() end");
		return prefix + "/edit";
	}

	@PostMapping("/update")
	@Log("更新批次")
	@ResponseBody()
	R update(BatchDO batch) {
		logger.info("BatchController.update() start");
		if (batchService.update(batch) > 0) {
			logger.info("BatchController.update() end");
			return R.ok();
		} else {
			logger.info("BatchController.update() end");
			return R.error(1, "保存失败");
		}
	}
	
	@GetMapping("/showBatch")
	String showBatch() {
		logger.info("BatchController.showBatch() start");
		logger.info("BatchController.showBatch() end");
		return prefix + "/showBatch";
	}
	
	/**
	 * 获取批次的Tree信息
	 * @param 
	 * @return Tree<BatchDO>
	 */
	@PostMapping("/getBatchTree")
	@ResponseBody
	public Tree<BatchDO> getBatchTree() {
		logger.info("BatchController.getBatchTree() start");
		Tree<BatchDO> tree = batchService.getBatchTree();
		logger.info("BatchController.getBatchTree() end");
		return tree;
	}
}
