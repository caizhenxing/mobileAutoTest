package com.bmtc.svn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bmtc.svn.domain.SvnUser;
import com.bmtc.svn.domain.SvnUserRepoInfo;

/**
 * SVN用户管理接口
 * @author lpf7161
 *
 */
@Mapper
public interface SvnUserDao {
	
	/**
	 * 增加SVN用户
	 * @param svnUser
	 */
	int addSvnUser(SvnUser svnUser);
	
	/**
	 * 根据SVN用户名和库名查询SVN用户记录数
	 * @param svnUserName,svnRepoId
	 */
	int countSvnUser(@Param("svnUserName") String svnUserName, 
			@Param("svnRepoId") long svnRepoId);
	
	/**
	 * 根据SVN用户名和库名删除SVN用户
	 * @param svnUserName,svnRepoId
	 */
	int deleteSvnUser(@Param("svnUserName") String svnUserName, 
			@Param("svnRepoId") long svnRepoId);
	
	/**
	 * 根据SVN用户名查询SVN用户记录数
	 * @param svnUserName
	 */
	int countSvnUserBySvnUserName(Map<String, Object> params);
	
	/**
	 * 根据SVN用户名删除SVN用户，即删除所有svn仓库中用户名为svnUserName的用户信息
	 * @param svnUserName
	 */
	int deleteSvnUserBySvnUserName(String svnUserName);
	
	/**
	 * 根据SVN用户名和库名修改SVN用户
	 * @param svnUser
	 */
	int updateSvnUser(SvnUser svnUser);
	
	/**
	 * 根据SVN用户名查询SVN用户信息
	 * @param svnUserName
	 */
	List<SvnUserRepoInfo> querySvnUserBySvnUserName(Map<String, Object> params);
	
	/**
	 * 根据SVN用户名和库名查询SVN用户信息
	 * @param svnUserName,svnRepoId
	 */
	SvnUserRepoInfo querySvnUser(@Param("svnUserName") String svnUserName, 
			@Param("svnRepoId") long svnRepoId);
	
	/**
	 * 根据SVN库名查询SVN用户信息
	 * @param svnRepoId
	 */
	List<SvnUser> getListBySvnRepoId(long svnRepoId);
	
	/**
	 * 根据SVN用户名和库名查询SVN用户id
	 * @param svnUserName,svnRepoName
	 */
	String querySvnUserId(@Param("svnUserName") String svnUserName, 
			@Param("svnRepoId") long svnRepoId);
	
	/**
	 * 根据svnUserName(可为空)和svnRepoName(可为空)查询SVN用户记录数
	 * @param params
	 * @return
	 */
	int count(Map<String, Object> params);
	
	/**
	 * 根据svnUserName(可为空)和svnRepoName(可为空)查询SVN用户
	 * @param params
	 * @return
	 */
	List<SvnUserRepoInfo> query(Map<String, Object> params);
	
	
	/**
	 * 根据svn用户名从svn用户表中查询用户名id
	 * @param svnUserName
	 */
	String querySvnUserIdBySvnUserName(String svnUserName);

}
