package com.bmtc.svn.controller;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.svn.domain.SvnManager;
import com.bmtc.svn.service.UpdateLocalCodeBySvnRepoService;
import com.bmtc.svn.common.entity.PushMsg;
import com.bmtc.svn.common.web.BaseController;

import org.tmatesoft.svn.core.SVNException;


/**
 * svn仓库管理前端接口处理
 * @author lpf7161
 *
 */
@Controller
public class UpdateLocalCodeBySvnRepoController extends BaseController {
	private static Logger logger = Logger.getLogger(UpdateLocalCodeBySvnRepoController.class);
	
	@Autowired
	private UpdateLocalCodeBySvnRepoService updateLocalCodeBySvnRepoService;
	
	/**
	 * 参数检查
	 * @param svnManager
	 * @return PushMsg
	 */
	private PushMsg checkSvnManagerParam(SvnManager svnManager) {
		logger.info("UpdateLocalCodeBySvnRepoController.checkSvnManagerParam() start");
		//校验参数
		if (svnManager == null) {
			logger.error("svnManager is null");
			return pushMsg("请求输入参数", false);
		}
		if (svnManager.getSvnUrl_localCodePath() == null || svnManager.getSvnUrl_localCodePath().isEmpty()) {
			logger.error("svnUrl_localCodePath is null");
			return pushMsg("请求输入svnUrl_localCodePath参数", false);
		}
		if (StringUtils.isEmpty(svnManager.getSvnUserName())) {
			logger.error("svnUserName is null");
			return pushMsg("请求输入svnUserName参数", false);
		}
		if (StringUtils.isEmpty(svnManager.getSvnPassword())) {
			logger.error("svnPassword is null");
			return pushMsg("请求输入svnPassword参数", false);
		}
		
		logger.info("UpdateLocalCodeBySvnRepoController.checkSvnManagerParam() end");
		return pushMsg("参数校验成功", true);
	} 
	
	/**
	 * @param svnRepo
	 * @return PushMsg
	 */
	@RequestMapping(value = "/updateLocalCodeBySvnRepo")
	@ResponseBody
	public Object updateLocalCodeBySvnRepo(@RequestBody SvnManager svnManager) {
		
		logger.info("UpdateLocalCodeBySvnRepoController.updateLocalCodeBySvnRepo() start");
		//校验参数
		PushMsg pmg = checkSvnManagerParam(svnManager);
		if (!pmg.getStatus()) {
			logger.error("svnManager is null");
			return pmg;
		}
		
/*		// 创建参数SvnManger
		SvnManager svnManager = new SvnManager();
		svnManager.setSvnUserName("wy_wangq");
		svnManager.setSvnPassword("123456");
		Map<String,String> map = new HashMap<String,String>();
		map.put("svn://22.11.31.40/BMTC", "D:/var1");
		svnManager.setSvnUrl_localCodePath(map);*/
		
		Long[] workingVersion = new Long[svnManager.getSvnUrl_localCodePath().size()];
		int i = 0;
		for(Map.Entry<String, String> entry : svnManager.getSvnUrl_localCodePath().entrySet()) {
			
			//svn的url
			String url = entry.getKey();
			
			//本地代码存放路径
			String localCodePath = entry.getValue();
			
			//使用"/"代替"\"
			localCodePath = StringUtils.replace(localCodePath, "\\", "/");
			
			//svn代码下载到本地的存储位置
			File wcDir = new File(localCodePath);
			
			try {
				//下载或更新代码
				workingVersion[i] = updateLocalCodeBySvnRepoService.updateLocalCodeBySvnRepo(url, 
						svnManager.getSvnUserName(), svnManager.getSvnPassword(), wcDir, null);		
			} catch (SVNException e) {
				logger.error(e.getMessage());
				logger.error("svn下载脚本异常，请检查svn参数是否正确");
				e.printStackTrace();
				return e.getMessage();
			}
			
			logger.info("把版本号：" + workingVersion[i] + "checkout 或者 update到目录：" + wcDir + "中。");
			i++;
		}
		
		logger.info("UpdateLocalCodeBySvnRepoController.updateLocalCodeBySvnRepo() end");
		return workingVersion;
	}
	
}
