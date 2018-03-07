package com.bmtc.svn.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.bmtc.common.config.BMTCConfig;
import com.bmtc.common.domain.Tree;
import com.bmtc.common.utils.BuildTree;
import com.bmtc.svn.common.utils.EncryptUtil;
import com.bmtc.svn.dao.SvnRepoDao;
import com.bmtc.svn.dao.SvnUserRightDao;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnRootUser;
import com.bmtc.svn.domain.SvnUserAuthz;
import com.bmtc.svn.domain.SvnUserAuthzInfo;
import com.bmtc.svn.domain.SvnUserRepoInfo;
import com.bmtc.svn.service.RepTreeNodeService;
import com.bmtc.svn.service.RepositoryService;
import com.bmtc.svn.service.SvnRepoService;
import com.bmtc.svn.service.SvnService;
import com.bmtc.svn.service.SvnUserRightService;
import com.bmtc.svn.service.SvnUserService;
import com.bmtc.system.domain.ConfigInfoDO;
import com.bmtc.system.service.ConfigService;

/**
 * SVN用户权限管理业务逻辑实现
 * @author lpf7161
 *
 */
@Service("SvnUserRightService")
public class SvnUserRightServiceImpl implements SvnUserRightService 
{
	private static Logger logger = Logger.getLogger(SvnUserRightServiceImpl.class);
	
	@Autowired
	private SvnUserRightDao svnUserRightDao;
	
	@Autowired
	private SvnRepoDao svnRepoDao;
	
	/**
	 * SVN仓库服务层
	 */
	@Resource(name = "SvnRepoService")
	private SvnRepoService svnRepoService;
	
	/**
	 * SVN用户服务层
	 */
	@Autowired
	private SvnUserService svnUserService;
	
	/**
	 * 系统配置信息服务层
	 */
	@Autowired
	private ConfigService configService;
	
	/**
	 * SVN服务层
	 */
	@Resource(name = "SvnService")
	private SvnService svnService;
	
	/**
	 * SVN仓库服务层
	 */
	@Autowired
	private RepositoryService repositoryService;
	
	/**
	 * 仓库目录结构树节点服务层
	 */
	@Autowired
	private RepTreeNodeService repTreeNodeService;
	
	@Autowired
	BMTCConfig bmtcConfig;
	
	/**
	 * 增加用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录为0时将svnUserAuthz写入svn_user_authz表；
	 * 记录非0时，说明表中已经有该用户在该SVN仓库下svnPath的权限，则不用插入记录
	 * @param svnUserAuthz
	 * @throws Exception
	 */
	@Transactional
	@Override
	public int addSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception {	
		logger.info("SvnUserRightServiceImpl.addSvnUserRight() start");
		
		//如果资源没有[],自动加上
		String svnPath = this.formatRes(svnUserAuthz.getSvnRepoId(), svnUserAuthz.getSvnPath());
		svnUserAuthz.setSvnPath(svnPath);
		
		//将svn用户权限信息写入数据库
		int res = -1;
		//根据svnUserId和svnPath查询记录个数
		int cnt = svnUserRightDao.countBySvnUserIdAndSvnPath(svnUserAuthz.getSvnUserId(),
				svnUserAuthz.getSvnPath());
		if(cnt != 0) {
			logger.error("svn_user_authz表中已存在svnUserId=" + svnUserAuthz.getSvnUserId() + 
					", svnPath=" + svnUserAuthz.getSvnPath() + "的权限记录");		
		} else {
			res = 0;
			//cnt为0时将svnUserAuthz写入svn_user_authz表
			res = svnUserRightDao.addSvnUserRight(svnUserAuthz);
			logger.info("添加" + res + "条SVN用户权限完成");
		}
		
/*		// 根据svn仓库名查询仓库信息
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnUserAuthzInfo.getSvnRepoName());
			
		// 根据svn url截取svn服务器的IP地址
		String svnServerIp = svnRepo.getSvnRepoUrl().split("//")[1].split("/")[0];
		
		// 根据svn的IP地址查询超级用户的svn用户名
		ConfigInfoDO configInfoDO = configService.getConfigInfo(svnServerIp);
		
		String svnConfFilesLocation = configInfoDO.getSvnConfFilesLocation(); // 配置文件中的bmtcConfig.getSvnConfFilesLocation()
*/
		
		// 将数据库中的svn用户和权限信息写入passwd和authz文件中，并上传到远程svn服务器对应仓库的conf目录下
		svnService.exportConfigToSvnServer(svnUserAuthz.getSvnRepoName(), bmtcConfig.getSvnConfFilesLocation());

		logger.info("SvnUserRightServiceImpl.addSvnUserRight() end");
		return res;
	}

	
	/**
	 * 根据SVN用户名、库名和SVN路径修改用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 然后根据svnUserId和svnPath查询记录个数，记录非0时修改svnUserAuthz信息.
	 * @param svnUserAuthz
	 * @throws Exception
	 */
	@Transactional
	@Override
	public int updateSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception {
		logger.info("SvnUserRightServiceImpl.updateSvnUserRight() start");
		
		//如果资源没有[],自动加上
		String svnPath = this.formatRes(svnUserAuthz.getSvnRepoId(), svnUserAuthz.getSvnPath());
		svnUserAuthz.setSvnPath(svnPath);
		
		int res = 0;
		//根据svnUserId和svnPath查询记录个数
		int cnt = svnUserRightDao.countBySvnUserIdAndSvnPath(svnUserAuthz.getSvnUserId(),
				svnUserAuthz.getSvnPath());
		if(cnt == 0) {
			logger.error("svn_user_authz表中不存在svnUserId=" + svnUserAuthz.getSvnUserId() + 
					", svnPath=" + svnUserAuthz.getSvnPath() + "的权限记录");
		} else {
			//cnt非0时将修改SVN用户权限信息写入数据库
			res = svnUserRightDao.updateSvnUserRight(svnUserAuthz);
			logger.info("修改SVN用户权限信息写入数据库成功");
		}
		
		// 将数据库中的svn用户和权限信息写入passwd和authz文件中，并上传到远程svn服务器对应仓库的conf目录下
		svnService.exportConfigToSvnServer(svnUserAuthz.getSvnRepoName(), bmtcConfig.getSvnConfFilesLocation());

		logger.info("SvnUserRightServiceImpl.updateSvnUserRight() end");
		return res;
	}

	/**
	 * 保存SVN用户权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 然后根据svnUserId和svnPath查询记录个数，记录非0时修改svnUserAuthz信息，为0时新增svnUserAuthz信息
	 * @param svnUserAuthz
	 * @return 保存记录条数
	 * @throws Exception  
	 */
	@Transactional
	@Override
	public int saveSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception {
		logger.info("SvnUserRightServiceImpl.saveSvnUserRight() start");
		int res = 0;
		//根据svnUserId和svnPath查询记录个数
		int cnt = svnUserRightDao.countBySvnUserIdAndSvnPath(svnUserAuthz.getSvnUserId(),
				svnUserAuthz.getSvnPath());
		if(cnt == 0) {
			//cnt为0时将svnUserAuthz写入svn_user_authz表
			res = svnUserRightDao.addSvnUserRight(svnUserAuthz);
			logger.info("添加" + res + "条SVN用户权限完成");
		} else {
			//cnt非0时将修改SVN用户权限信息写入数据库
			res = svnUserRightDao.updateSvnUserRight(svnUserAuthz);
			logger.info("修改SVN用户权限信息写入数据库成功");
		}
		
		// 将数据库中的svn用户和权限信息写入passwd和authz文件中，并上传到远程svn服务器对应仓库的conf目录下
		svnService.exportConfigToSvnServer(svnUserAuthz.getSvnRepoName(), bmtcConfig.getSvnConfFilesLocation());
		
		logger.info("SvnUserRightServiceImpl.saveSvnUserRight() end");
		return res;
	}
	
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和svnRepoId执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询该用户在该仓库下的svn权限信息集
	 * @param svnUserId
	 */
	@Override
	public List<SvnUserAuthzInfo> querySvnUserRight(Map<String, Object> params) {
		logger.info("SvnUserRightServiceImpl.querySvnUserRight() start");
		//从数据库查询SVN用户权限信息
		List<SvnUserAuthzInfo> list = svnUserRightDao.querySvnUserRight(params);
		logger.info("从数据库查询SVN用户权限信息成功");
		logger.info("SvnUserRightServiceImpl.querySvnUserRight() end");
		return list;
	}

	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和svnRepoId执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询该用户在该仓库下的svn权限信息集
	 * @param svnUserId
	 */
	@Override
	public List<SvnUserAuthzInfo> querySvnUserRightBySvnUserId(Map<String, Object> params) {
		logger.info("SvnUserRightServiceImpl.querySvnUserRightBySvnUserId() start");
		//从数据库查询SVN用户权限信息
		List<SvnUserAuthzInfo> list = svnUserRightDao.querySvnUserRightBySvnUserId(params);
		logger.info("从数据库查询SVN用户权限信息成功");
		logger.info("SvnUserRightServiceImpl.querySvnUserRightBySvnUserId() end");
		return list;
	}
	
	/**
	 * 删除用户SVN权限
	 * 根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录非0时删除用户SVN权限
	 * @param svnUserId, svnRepoName, svnPath
	 * @throws Exception
	 */
	@Transactional
	@Override
	public int deleteSvnUserRight(String svnUserName, long svnUserId, String svnRepoName, String svnPath) throws Exception {
		logger.info("SvnUserRightServiceImpl.deleteSvnUserRight() start");	
		//根据svn仓库名查询svn仓库id
		String svnRepoIdStr = svnRepoDao.querySvnRepoIdBySvnRepoName(svnRepoName);
		long svnRepoId = 0;
		if("".equals(svnRepoIdStr) || svnRepoIdStr == null) {
			logger.error("svn_repo数据表中不存在svn仓库名为'" + svnRepoName + "'的记录");
			return -1;
		} else {
			svnRepoId = Long.parseLong(svnRepoIdStr);		
		}
		
		// 如果资源没有[],自动加上
		svnPath = this.formatRes(svnRepoId, svnPath);
		
		int res = -2;
		int cnt = 0;
		// 根据svnUserId和svnPath查询记录个数
		cnt = svnUserRightDao.countBySvnUserIdAndSvnPath(svnUserId,	svnPath);
		if(cnt == 0) {
			logger.error("svn_user_authz数据表中不存在svn用户名为'" + svnUserName + "',且svn仓库名为'" 
					+ svnRepoName + "',且svn路径为'" + svnPath +"'的记录");
		} else {
			res = 0;
			// cnt不为0时，将删除SVN用户权限写入数据库
			res = svnUserRightDao.deleteSvnUserRight(svnUserId, svnPath);
			if(res == 0) {
				logger.info("删除操作异常");
			} else {
				logger.info("删除SVN用户权限写入数据库成功");
			}	
		}
		
		// 将数据库中的svn用户和权限信息写入passwd和authz文件中，并上传到远程svn服务器对应仓库的conf目录下
		svnService.exportConfigToSvnServer(svnRepoName, bmtcConfig.getSvnConfFilesLocation());
		
		logger.info("SvnUserRightServiceImpl.deleteSvnUserRight() end");
		return res;
	}

	/**
	 * 删除用户SVN权限
	 * 首先根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询记录个数，记录非0时删除用户SVN权限，
	 * 即删除与用户名为svnUserName和仓库名为svnRepoName相关的所有权限信息
	 * @param svnUserId
	 */
	@Transactional
	@Override
	public int deleteSvnUserRightBySvnUserId(long svnUserId) {
		logger.info("SvnUserRightServiceImpl.deleteSvnUserRightBySvnUserId() start");
		int res = -1;
		int cnt = 0;
		// 根据svnUserId查询记录个数
		cnt = svnUserRightDao.countBySvnUserId(svnUserId);
		if(cnt == 0) {
			logger.error("svn_user_authz表中不存在svnUserId=" + svnUserId + "的记录");
		} else {
			res = 0;
			// cnt不为0时，将删除SVN用户权限写入数据库
			res = svnUserRightDao.deleteSvnUserRightBySvnUserId(svnUserId);
			if(res == 0) {
				logger.info("删除操作异常");
			} else {
				logger.info("删除SVN用户权限写入数据库成功");
			}
		}

		logger.info("SvnUserRightServiceImpl.deleteSvnUserRightBySvnUserId() end");
		return res;
	}

	/**
	 * 根据SVN用户名查询svn用户id集
	 * @param svnUserName
	 */
	@Override
	public List<String> querySvnUserIdBySvnUserName(String svnUserName) {
		logger.info("SvnUserRightServiceImpl.querySvnUserIdBySvnUserName() start");
		List<String> list = svnUserRightDao.querySvnUserIdBySvnUserName(svnUserName);
		logger.info("根据SVN用户名查询svn用户id集成功");
		logger.info("SvnUserRightServiceImpl.querySvnUserIdBySvnUserName() end");
		return list;
	}

	/**
	 * 根据SVN用户id查询svn_user_authz表中记录个数
	 * @param svnUserId
	 */
	@Transactional
	@Override
	public int countBySvnUserId(long svnUserId) {
		logger.info("SvnUserRightServiceImpl.countBySvnUserId() start");
		int res = 0;
		res = svnUserRightDao.countBySvnUserId(svnUserId);
		if(res == 0) {
			logger.info("svn_user_authz表中不存在id为'" + svnUserId + "'的SVN用户记录");
		}
		logger.info("SvnUserRightServiceImpl.countBySvnUserId() end");
		return res;
	}

	/**
	 * 根据SVN用户id和SVN路径查询svn_user_authz表中记录个数
	 * @param svnUserId,svnPath
	 */
	@Transactional
	@Override
	public int countBySvnUserIdAndSvnPath(long svnUserId, String svnPath) {
		logger.info("SvnUserRightServiceImpl.countBySvnUserIdAndSvnPath() start");
		int res = 0;
		res = svnUserRightDao.countBySvnUserIdAndSvnPath(svnUserId, svnPath);
		if(res == 0) {
			logger.info("svn_user_authz表中不存在id为'" + svnUserId + "', svn路径为'" + svnPath + "'的记录");
		}
		logger.info("SvnUserRightServiceImpl.countBySvnUserIdAndSvnPath() end");
		return res;
	}
	
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param svnRepoId 项目id
	 * @param res 资源
	 * @return 格式化后的资源
	 */
	@Override
	public String formatRes(long svnRepoId, String res) {
		//如果资源没有[],自动加上
//		if(!res.startsWith("[") && !res.endsWith("]")){
			SvnRepo svnRepo = svnRepoDao.querySvnRepoBySvnRepoId(svnRepoId);
			return this.formatRes(svnRepo, res);
//		}
//		return res;
	}
	
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param svnRepo 项目
	 * @param res 资源
	 * @return 格式化后的资源
	 */
	@Override
	public String formatRes(SvnRepo svnRepo, String res) {
		//去除[xxx:]，重新加上[relateRoot:/]，防止跨项目授权
		res = StringUtils.replaceEach(res, new String[]{"[","]"}, new String[]{"",""});
		if (res.indexOf(":")!=-1) {
			res = StringUtils.substringAfter(res, ":");
		}
		
		//如果资源没有[],自动加上
		String relateRoot = svnRepoService.getRelateRootPath(svnRepo);
		if(!res.startsWith("[") && !res.endsWith("]")){
			if(res.startsWith("/")){
				return "["+relateRoot+":"+res+"]";
			}else{
				return  "["+relateRoot+":/"+res+"]";
			}
		}
		return res;
	}


	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限
	 * @param params
	 * @return
	 */
	@Override
	public List<SvnUserAuthzInfo> querySvnRight(Map<String, Object> params) {
		logger.info("SvnUserRightServiceImpl.querySvnRight() start");
		// 从数据库查询SVN用户权限信息
		List<SvnUserAuthzInfo> list = svnUserRightDao.querySvnRight(params);
		logger.info("从数据库查询SVN用户权限信息成功");
		logger.info("SvnUserRightServiceImpl.querySvnRight() end");
		return list;
	}


	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限记录数
	 * @param params
	 * @return
	 */
	@Transactional
	@Override
	public int countSvnRight(Map<String, Object> params) {
		logger.info("SvnUserRightServiceImpl.countSvnRight() start");
		int res = 0;
		res = svnUserRightDao.countSvnRight(params);
		logger.info("SvnUserRightServiceImpl.countSvnRight() end");
		return res;
	}

	
	/**
	 * 根据svn url获取svn仓库的所有路径Tree信息
	 * @param SvnRootUser svnRootUser
	 * @return List<Tree<String>>
	 * @throws SVNException 
	 */
	@Override
	public List<Tree<String>> getTreeData(SvnRootUser svnRootUser) throws SVNException {
		logger.info("SvnUserRightServiceImpl.getTreeData() start");
		// 创建List<Tree>存储svn仓库的所有路径
		List<Tree<String>> trees = new ArrayList<Tree<String>>();

		// svnRepoName svn仓库
		String svnRepoName = svnRootUser.getSvnRepoName();
		// svn超级用户的用户名
		String svnUserName = svnRootUser.getSvnUserName();
		// svn超级用户的密码
		String svnPassword = svnRootUser.getSvnPassword();
		
		Collection<SVNDirEntry> collection = new ArrayList<SVNDirEntry>();
		SVNRepository sVNRepository = null;
		// 获取svn仓库信息
		try {
			sVNRepository = repositoryService.getRepository(svnRepoName, svnUserName, svnPassword);
			collection = repositoryService.getDir(svnRepoName, "/", svnUserName, svnPassword, sVNRepository);
		} catch (SVNException e) {
    		e.printStackTrace();
    		throw new RuntimeException(e.getMessage());
		}
		
		//遍历SVN目录或文件系统集合
		Iterator<SVNDirEntry> iterator = collection.iterator();
		while(iterator.hasNext()) {
			logger.info(iterator.next() + "");
		}
		
		logger.info("SvnUserRightServiceImpl.getTreeData() end");
		return trees;
	}


	/**
	 * 获取svn目录的Tree信息
	 * @param SvnRootUser svnRootUser
	 * @return Tree<String>
	 * @throws Exception  
	 */
	@Override
	public Tree<String> getTree(SvnRootUser svnRootUser) throws Exception {
		logger.info("SvnUserRightServiceImpl.getTree() start");
		
		// 用于保存svn目录树形结构
		List<Tree<String>> trees = new ArrayList<Tree<String>>();
		// 用于保存svn目录树形结构中间结果
		List<Tree<String>> results = new ArrayList<Tree<String>>();
		
		// 根据svn仓库名查询仓库信息
		SvnRepo svnRepo = svnRepoService.querySvnRepoBySvnRepoName(svnRootUser.getSvnRepoName());
		// 获取svn Url
		svnRootUser.setSvnRepoUrl(svnRepo.getSvnRepoUrl());
		// 获取svn仓库路径
		svnRootUser.setSvnRepoPath(svnRepo.getSvnRepoPath());
		
		// 根据svn url截取svn服务器的IP地址
		String svnServerIp = svnRepo.getSvnRepoUrl().split("//")[1].split("/")[0];
		
		// 根据svn的IP地址查询超级用户的svn用户名
		ConfigInfoDO configInfoDO = configService.getConfigInfo(svnServerIp);
		
		String svnRootUserName = configInfoDO.getSvnRootUserName(); // 配置文件中的bmtcConfig.getSvnRootUserName()
		
		// 根据SVN用户名和库名查询SVN用户信息，获取超级用户在仓库svnRootUser.getSvnRepoName()的svn密码
		SvnUserRepoInfo svnUserRepoInfo = svnUserService.querySvnUser(svnRootUserName, //默认为admin
				svnRootUser.getSvnRepoName());

		// 判断超级用户是否拥有相应仓库的根目录的读写权限
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("svnUserName", svnRootUserName);
		params.put("svnRepoName", svnRootUser.getSvnRepoName());
		params.put("svnPath", "[" + svnRootUser.getSvnRepoName() + ":/]");
		params.put("svnUserAuthz", "rw");
		// 根据SVN用户名、仓库名、svn路径、svn权限查询用户SVN权限总记录数
		int total = this.countSvnRight(params);
		
		// 若超级用户没有相应svn仓库的根权限，则直接返回
		if(svnUserRepoInfo == null || svnUserRepoInfo.getSvnPassword() == null || total == 0) {
			// 默认顶级菜单为0，根据数据库实际情况调整
			Tree<String> res = BuildTree.build(trees);
			res.setId("-2");
			res.setText(svnRootUser.getSvnRepoUrl());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("note", "获取svn目录树形结构失败，请先给超级用户:" + svnRootUserName 
					+ "开通【" + svnRootUser.getSvnRepoName() + "】仓库的根目录读写权限");
			res.setAttributes(attributes);
			logger.info("SvnUserRightController.tree() end");
			return res;
		}
		
		// 若超级用户拥有相应svn仓库的根权限，则获取整个svn的目录树形结构
		svnRootUser.setSvnUserName(svnRootUserName); //默认为admin
		
		// 对svn用户密码进行解密
		svnUserRepoInfo.setSvnPassword(EncryptUtil.decrypt(svnUserRepoInfo.getSvnPassword()));
		svnRootUser.setSvnPassword(svnUserRepoInfo.getSvnPassword()); //获取超级svn用户对应的svn密码
		
		// 程序开始时设置svn路径为根路径
		svnRootUser.setPath("/");
		// svnRepoName svn仓库名称
		String svnRepoName = svnRootUser.getSvnRepoName();
		// svn超级用户的用户名
		String svnUserName = svnRootUser.getSvnUserName();
		// svn超级用户的密码
		String svnPassword = svnRootUser.getSvnPassword();
		
		SVNRepository sVNRepository  = null;
		// 获取svn仓库信息
		try {
			sVNRepository = repositoryService.getRepository(svnRepoName, svnUserName, svnPassword);
		} catch (SVNException e) {
    		e.printStackTrace();
			logger.error(e.getMessage());
    		throw new Exception(e.getMessage());
		}
		
		// 父节点
		Tree<String> parent = new Tree<String>();
		// 获取svn仓库对应的目录树形结构
		try {
			trees = repTreeNodeService.getTreeNodes(parent, svnRootUser, results, sVNRepository);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			if(sVNRepository != null) {
				sVNRepository.closeSession();
			}
		}
		
		// 默认顶级菜单为0，根据数据库实际情况调整
		Tree<String> root = BuildTree.build(trees);
		root.setId("-1");
		root.setParentId("");
		root.setHasParent(false);
		root.setText(svnRootUser.getSvnRepoUrl());
		Map<String, Object> state = new HashMap<>(16);
		state.put("opened", true);
		root.setState(state);
		logger.info("SvnUserRightServiceImpl.getTree() end");
		return root;
	}
}
