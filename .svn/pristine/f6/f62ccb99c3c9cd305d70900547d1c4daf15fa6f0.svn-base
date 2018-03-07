package com.bmtc.svn.service;

import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.core.SVNException;

import com.bmtc.common.domain.Tree;
import com.bmtc.svn.domain.SvnRepo;
import com.bmtc.svn.domain.SvnRootUser;
import com.bmtc.svn.domain.SvnUserAuthz;
import com.bmtc.svn.domain.SvnUserAuthzInfo;

/**
 * SvnUserRightService业务逻辑接口
 * @author lpf7161
 *
 */
public interface SvnUserRightService {
	
	/**
	 * 根据SVN用户id查询svn_user_authz表中记录个数
	 * @param svnUserId
	 */
	int countBySvnUserId(long svnUserId);
	
	/**
	 * 根据SVN用户id和SVN路径查询svn_user_authz表中记录个数
	 * @param svnUserId,svnPath
	 */
	int countBySvnUserIdAndSvnPath(long svnUserId, String svnPath);
	
	/**
	 * 增加用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录为0时将svnUserAuthz写入svn_user_authz表；
	 * 记录非0时，说明表中已经有该用户在该SVN仓库下svnPath的权限，则不用插入记录
	 * @param svnUserAuthz
	 */
	int addSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception;
	
	/**
	 * 根据SVN用户名、库名和SVN路径修改用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 然后根据svnUserId和svnPath查询记录个数，记录非0时修改svnUserAuthz信息.
	 * @param svnUserAuthz
	 */
	int updateSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception;
	
	/**
	 * 保存SVN用户权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 然后根据svnUserId和svnPath查询记录个数，记录非0时修改svnUserAuthz信息，为0时新增svnUserAuthz信息
	 * @param svnUserAuthz
	 * @return 保存记录条数
	 */
	int saveSvnUserRight(SvnUserAuthz svnUserAuthz) throws Exception;
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限，首先根据SVN库名执行querySvnRepoIdBySvnRepoName得到svnRepoId，
	 * 然后根据SVN用户名和svnRepoId执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询该用户在该仓库下的svn权限信息集
	 * @param svnUserId
	 */
	List<SvnUserAuthzInfo> querySvnUserRight(Map<String, Object> params);

	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限记录数
	 * @param params
	 * @return
	 */
	int countSvnRight(Map<String, Object> params);
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限
	 * @param params
	 * @return
	 */
	List<SvnUserAuthzInfo> querySvnRight(Map<String, Object> params);
	
	/**
	 * 根据SVN用户名和仓库名查询用户SVN权限，首先根据SVN用户名、库名执行querySvnUserId()得到svnUserId，
	 * 然后根据svnUserId查询该用户在该仓库下的svn权限信息集
	 * @param svnUserId
	 */
	List<SvnUserAuthzInfo> querySvnUserRightBySvnUserId(Map<String, Object> params);
	
	/**
	 * 删除用户SVN权限
	 * 根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId和svnPath查询记录个数，记录非0时删除用户SVN权限
	 * @param svnUserId,svnPath
	 */
	int deleteSvnUserRight(String svnUserName, long svnUserId, String svnRepoName, String svnPath) throws Exception;
	
	/**
	 * 删除用户SVN权限
	 * 首先根据SVN用户名和仓库名执行querySvnUser()得到svnUserId，
	 * 接着根据svnUserId查询记录个数，记录非0时删除用户SVN权限，
	 * 即删除与用户名为svnUserName和仓库名为svnRepoName相关的所有权限信息
	 * @param svnUserId
	 */
	int deleteSvnUserRightBySvnUserId(long svnUserId);
	
	/**
	 * 根据SVN用户名查询svn用户id
	 * @param svnUserName
	 */
	List<String> querySvnUserIdBySvnUserName(String svnUserName);
	
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param svnRepoId 项目id
	 * @param res 资源
	 * @return 格式化后的资源
	 */
	String formatRes(long svnRepoId, String res);
	
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param svnRepo 项目
	 * @param res 资源
	 * @return 格式化后的资源
	 */
	String formatRes(SvnRepo svnRepo, String res);
	
	/**
	 * 获取SVN路径Tree信息
	 * @param 
	 * @param 
	 * @return 
	 */
	List<Tree<String>> getTreeData(SvnRootUser svnRootUser) throws SVNException;
	
	/**
	 * 获取SVN目录树形数据
	 * @return Tree
	 */
	Tree<String> getTree(SvnRootUser svnRootUser) throws Exception;
}
