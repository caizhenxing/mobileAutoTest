package com.bmtc.svn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bmtc.svn.domain.SvnCreateBranch;
import com.bmtc.svn.domain.SvnCreateBranchInfo;

/**
 * SVN新建分支信息接口
 * @author lpf7161
 */
@Mapper
public interface SvnCreateBranchDao {
	
	// 通过id获取svn新建分支信息表对象
	SvnCreateBranchInfo get(Long id);
	
	// 查询svn新建分支信息
	List<SvnCreateBranchInfo> list(Map<String,Object> map);
	
	// 统计svn新建分支信息总数
	int count(Map<String,Object> map);
	
	// 保存svn新建分支信息
	int save(SvnCreateBranch svnCreateBranch);
	
	// 更新svn新建分支信息
	int update(SvnCreateBranch svnCreateBranch);
	
	// 通过svn仓库名查询svn新建分支信息
	SvnCreateBranchInfo getSvnCreateBranchInfo(String svnRepoName);
	
	// 通过svn仓库id、svn新建分支基线、svn需要新建的分支查询svn新建分支信息
	SvnCreateBranchInfo querySvnCreateBranchInfo(SvnCreateBranch svnCreateBranch);
	
	// 删除svn新建分支信息
	int remove(Long id);
	
	// 批量删除svn新建分支信息
	int batchRemove(Long[] ids);
}
