package com.bmtc.svn.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tmatesoft.svn.core.SVNException;

import com.bmtc.common.annotation.Log;
import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.utils.PageUtils;
import com.bmtc.common.utils.Query;
import com.bmtc.common.utils.R;
import com.bmtc.svn.common.entity.PushMsg;
import com.bmtc.svn.common.utils.SvnRepoTree;
import com.bmtc.svn.common.web.BaseController;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.service.SvnRepoService;

/**
 * svn仓库管理前端接口处理
 * @author lpf7161
 *
 */
@RequestMapping("/svn/svnRepo")
@Controller
public class SvnRepoController extends BaseController {
	private static Logger logger = Logger.getLogger(SvnRepoController.class);
	
	@Autowired
	private SvnRepoService svnRepoService;
	
	@Autowired
	BMTCConfig bmtcConfig;
	
	String prefix = "svn/svnRepo";
	
	@RequiresPermissions("svn:svnRepo:svnRepo")
	@Log("跳转到仓库界面")
	@GetMapping("")
	String svnRepo() {
		return prefix + "/svnRepo";
	}
	
	/**
	 * 参数检查
	 * @param svnRepo
	 * @return PushMsg
	 */
	private PushMsg checkSvnRepoParam(SvnRepo svnRepo) {
		logger.info("SvnRepoController.checkSvnRepoParam() start");
		//校验参数
		if (svnRepo == null) {
			logger.error("svnRepo is null");
			return pushMsg("请求输入参数", false);
		}
		if (StringUtils.isEmpty(svnRepo.getSvnRepoName())) {
			logger.error("svnRepoName is null");
			return pushMsg("请求输入svnRepoName参数", false);
		}
		if (StringUtils.isEmpty(svnRepo.getSvnRepoPath())) {
			logger.error("svnRepoPath is null");
			return pushMsg("请求输入svnRepoPath参数", false);
		}
		if (StringUtils.isEmpty(svnRepo.getSvnRepoUrl())) {
			logger.error("svnRepoUrl is null");
			return pushMsg("请求输入svnRepoUrl参数", false);
		}
		
		logger.info("SvnRepoController.checkSvnRepoParam() end");
		return pushMsg("参数校验成功", true);
	} 
	
	
	@GetMapping("/treeView") 
	@Log("添加SVN用户时访问SVN仓库树形图")
	String svnRepoTreeView() { 
		return prefix + "/svnRepoTree";
	}
	
	@GetMapping("/tree")
	@Log("获取SVN仓库树形图数据")
	@ResponseBody
	public SvnRepoTree<SvnRepo> tree() {
		logger.info("SvnRepoController.tree() start");
		SvnRepoTree<SvnRepo> tree = new SvnRepoTree<SvnRepo>();
		tree = svnRepoService.getTree();
		logger.info("SvnRepoController.tree() end");
		return tree;
	}
	
	@RequiresPermissions("svn:svnRepo:add")
	@Log("添加svn仓库")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}
	
	/**
	 * @param svnRepo
	 * @return PushMsg
	 * @throws Exception 
	 */
	@RequiresPermissions("svn:svnRepo:add")
	@Log("新增svn仓库")
	@PostMapping("/addSvnRepo")
	@ResponseBody
	public Object addSvnRepo(SvnRepo svnRepo) {	
		logger.info("SvnRepoController.addSvnRepo() start");
		//校验参数
		PushMsg pmg = checkSvnRepoParam(svnRepo);
		if (!pmg.getStatus()) {
			logger.error("svnRepo is null");
			return R.error(-1, "svnRepo is null");
		}
		
		int res = 0;
		try {
			//添加SVN仓库
			res = svnRepoService.addSvnRepo(svnRepo, bmtcConfig.getSvnConfFilesLocation());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return R.error(-1, e.getMessage());
		}
		
		logger.info("SvnRepoController.addSvnRepo() end");
		if(res == -2) {
			logger.info("svn_repo表中已存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			return R.error(-1, "svn_repo表中已存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
		} else if (res == -3) {
			logger.info("数据库里已经存在相同的路径或url的仓库项目，请检查路径或url");	
			return R.error(-1, "数据库里已经存在相同的路径或url的仓库项目，请检查路径或url");
		} else if (res == -4) {
			logger.info("数据库里已经存在多个相同的路径或url的仓库项目，请检查路径或url");	
			return R.error(-1, "数据库里已经存在多个相同的路径或url的仓库项目，请检查路径或url");
		} else if (res == -5) {
			logger.info("添加SVN仓库成功");	
			return R.ok();
		} else if (res == -6) {
			logger.info("添加的svn仓库已写入数据库，但是创建远程svn仓库失败，需要手动创建!!!");	
			return R.error(-1, "添加的svn仓库已写入数据库，但是创建远程svn仓库失败，需要手动创建!!!");
		} else {
			logger.info("添加的svn仓库异常");	
			return R.error(-1, "添加的svn仓库异常");
		}
	}
	
	/**
	 * @param svnRepoName
	 * @return R
	 */
	@RequiresPermissions("svn:svnRepo:remove")
	@Log("删除svn仓库")
	@GetMapping("/deleteSvnRepo")
	@ResponseBody
	public Object deleteSvnRepo(@RequestParam String svnRepoName) {
		logger.info("SvnRepoController.deleteSvnRepo() start");
		
		//校验参数
		if (StringUtils.isEmpty(svnRepoName)) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		
		//根据svnRepoName删除SVN仓库
		int res = svnRepoService.deleteSvnRepo(svnRepoName);
		if(res == -1) {
			logger.error("svn_repo表中不存在svn库名为'" + svnRepoName + "'的记录");
			R.error(-1, "svn_repo表中不存在svn库名为'" + svnRepoName + "'的记录");
		} else if(res > 0) {
			logger.info("SvnRepoController.deleteSvnRepo() end");
			return R.ok();
		}
		logger.info("SvnRepoController.deleteSvnRepo() end");
		return R.error();
	}

	@RequiresPermissions("svn:svnRepo:batchRemove")
	@Log("批量删除svn仓库")
	@PostMapping("/batchRemove")
	@ResponseBody
	public R batchRemoveSvnRepo(@RequestParam("svnRepoIds[]") Long[] svnRepoIds) {
		logger.info("SvnRepoController.batchRemoveSvnRepo() start");
		int res = svnRepoService.batchRemoveSvnRepo(svnRepoIds);
		if (res > 0) {
			logger.info("SvnRepoController.batchRemoveSvnRepo() end");
			return R.ok();
		}
		logger.info("SvnRepoController.batchRemoveSvnRepo() end");
		return R.error();
	}
	
	
	@RequiresPermissions("svn:svnRepo:edit")
	@Log("编辑svn仓库")
	@GetMapping("/edit/{svnRepoName}")
	public String edit(Model model, @PathVariable("svnRepoName") String svnRepoName) {
		logger.info("SvnRepoController.edit() start");
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);
		model.addAttribute("svnRepo", svnRepo);
		logger.info("SvnRepoController.edit() end");
		return prefix + "/edit";
	}
	
	
	/**
	 * @param svnRepo
	 * @return R
	 */
	@RequiresPermissions("svn:svnRepo:edit")
	@Log("更新svn仓库信息")
	@PostMapping("/updateSvnRepo")
	@ResponseBody
	public Object updateSvnRepo(SvnRepo svnRepo) {	
		logger.info("SvnRepoController.updateSvnRepo() start");
		//校验参数
		PushMsg pmg = checkSvnRepoParam(svnRepo);
		if (!pmg.getStatus()) {
			logger.error("svnRepo is null");
			return R.error(-1, "svnRepo is null");
		}
		//根据SVN库名修改SVN仓库信息
		int res = svnRepoService.updateSvnRepo(svnRepo);
		if(res == -1) {
			logger.error("svn_repo表中不存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
			R.error(-1, "svn_repo表中不存在svn库名为'" + svnRepo.getSvnRepoName() + "'的记录");
		} else if(res > 0) {
			logger.info("SvnRepoController.updateSvnRepo() end");
			return R.ok();
		}
		logger.info("SvnRepoController.updateSvnRepo() end");
		return R.error();
	}
	

	/**
	 * @param svnRepoName
	 * @return SvnRepo
	 */
	@RequestMapping(value = "/querySvnRepoBySvnRepoName")
	@ResponseBody
	public Object querySvnRepoBySvnRepoName(@RequestParam String svnRepoName) {
		logger.info("SvnRepoController.querySvnRepoBySvnRepoName() start");
		//校验参数
		if (StringUtils.isEmpty(svnRepoName)) {
			logger.error("svnRepoName is null");
			return R.error(-1, "请求输入svnRepoName参数");
		}
		//根据svn仓库名从svn仓库表中查询仓库信息
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRepoName);	
		logger.info("SvnRepoController.querySvnRepoBySvnRepoName() end");
		return svnRepo;
	}
	
	
	/**
	 * @param 
	 * @return List<SvnRepo>
	 * @throws SVNException
	 */
	@GetMapping("/querySvnRepo")
	@Log("查询列表数据")
	@ResponseBody
	public PageUtils querySvnRepo(@RequestParam Map<String, Object> params) {
		logger.info("SvnRepoController.querySvnRepo() start");
		// 分页查询参数数据
		Query query = new Query(params);
		//查询svn仓库信息
		List<SvnRepo> list = svnRepoService.querySvnRepo(query);
		// 查询总记录数
		int total = svnRepoService.countSvnRepo(query);
		// 封装到返回对象
		PageUtils pageUtil = new PageUtils(list, total);
		logger.info("SvnRepoController.querySvnRepo() end");
		// 返回数据
		return pageUtil;
	}
}
