package com.bmtc.svn.service;

import java.util.List;
import java.util.Map;

import com.bmtc.svn.common.utils.SvnRepoTree;
import com.bmtc.svn.domain.SvnRepo;

/**
 * SvnRepoService业务逻辑接口
 * @author lpf7161
 *
 */
public interface SvnRepoService {

	/**
	 * 增加SVN仓库
	 * @param svnRepo
	 */
	int addSvnRepo(SvnRepo svnRepo, String svnConfFilesLocation) throws Exception;
	
	/**
	 * 根据SVN库名查询是否存在该SVN仓库
	 * @param svnRepoName
	 */
	int isExistSvnRepo(String svnRepoName);
	
	/**
	 * 根据SVN库名删除SVN仓库
	 * @param svnRepoName
	 */
	int deleteSvnRepo(String svnRepoName);
	
	/**
	 * 根据SVN库id批量删除SVN仓库
	 * @param svnRepoIds
	 */
	int batchRemoveSvnRepo(Long[] svnRepoIds);
	
	/**
	 * 根据SVN库名修改SVN仓库
	 * @param svnRepo
	 */
	int updateSvnRepo(SvnRepo svnRepo);
	
	/**
	 * 保存SVN仓库，若存在此仓库（根据仓库名判断）则修改，否则新增
	 * @param svnRepo
	 * @return 保存记录条数
	 */
	int saveSvnRepo(SvnRepo svnRepo);
	
	/**
	 * 查询svn仓库记录数
	 * @param 
	 */
	int countSvnRepo(Map<String, Object> params);
	
	/**
	 * 查询svn仓库信息
	 * @param 
	 */
	List<SvnRepo> querySvnRepo(Map<String, Object> params);
	
	/**
	 * 根据svn仓库名从svn仓库表中查询仓库信息
	 * @param svnRepoName
	 */
	SvnRepo querySvnRepoBySvnRepoName(String svnRepoName);
	
	/**
	 * 根据svn仓库名从svn仓库表中查询仓库id
	 * @param svnRepoName
	 */
	String querySvnRepoIdBySvnRepoName(String svnRepoName);
	
	/**
	 * 获取项目的相对根路径.例如项目的path=e:/svn/projar，则返回projar。如果path为空，则返回项目ID
	 * @param pj 项目
	 * @return 项目的相对根路径
	 */
	String getRelateRootPath(SvnRepo svnRepo);
	
	/**
	 * 获取SVN仓库树形图数据
	 * @return Tree
	 */
	SvnRepoTree<SvnRepo> getTree();
	
}
