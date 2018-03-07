package com.bmtc.svn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bmtc.svn.domain.SvnRepo;


/**
 * SVN仓库接口
 * @author lpf7161
 *
 */
@Mapper
public interface SvnRepoDao {


	/**
	 * 增加SVN仓库
	 * @param svnRepo
	 */
	int addSvnRepo(SvnRepo svnRepo);
	
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
	 * 查询svn仓库记录数
	 * @param params
	 */
	int countSvnRepo(Map<String, Object> params);
	
	/**
	 * 根据svn路径和svn url查询svn仓库记录数
	 * @param svnRepoPath, svnRepoUrl
	 */
	int getCount(@Param("svnRepoPath") String svnRepoPath, @Param("svnRepoUrl") String svnRepoUrl);
	
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
	 * 根据svn仓库id从svn仓库表中查询仓库信息
	 * @param svnRepoId
	 */
	SvnRepo querySvnRepoBySvnRepoId(long svnRepoId);
	
	/**
	 * 根据svn仓库名从svn仓库表中查询仓库id
	 * @param svnRepoName
	 */
	String querySvnRepoIdBySvnRepoName(String svnRepoName);
	
}
