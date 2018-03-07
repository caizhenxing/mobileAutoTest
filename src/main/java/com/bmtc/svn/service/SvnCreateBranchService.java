package com.bmtc.svn.service;

import java.util.List;
import java.util.Map;

import com.bmtc.svn.domain.SvnCreateBranch;
import com.bmtc.svn.domain.SvnCreateBranchInfo;

import org.springframework.stereotype.Service;

/**
 * SVN新建分支信息服务层
 * @author lpf7161
 */
@Service
public interface SvnCreateBranchService {
	
	// 通过id获取svn新建分支信息表对象
	SvnCreateBranchInfo get(Long id);
	
	// 查询svn新建分支信息
	List<SvnCreateBranchInfo> list(Map<String,Object> map);
	
	// 统计svn新建分支信息总数
	int count(Map<String,Object> map);
	
	// 保存svn新建分支信息
	String save(SvnCreateBranch svnCreateBranch) throws Exception;
	
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
