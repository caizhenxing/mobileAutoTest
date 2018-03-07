package com.bmtc.svn.service;

import java.util.List;
import java.util.Map;

import com.bmtc.svn.domain.SvnConfDiff;
import com.bmtc.svn.domain.SvnConfDiffInfo;

import org.springframework.stereotype.Service;

/**
 * svn配置文件差异服务层
 * @author lpf7161
 */
@Service
public interface SvnConfDiffService {
	
	// 通过id获取svn配置文件差异对象
	SvnConfDiffInfo get(Long id);
	
	// 查询svn配置文件差异数据
	List<SvnConfDiffInfo> list(Map<String,Object> map);
	
	// 统计svn配置文件差异总数
	int count(Map<String,Object> map);
	
	// 保存svn配置文件差异信息
	int save() throws Exception;
	
	// 更新svn配置文件差异信息
	int update(SvnConfDiff svnConfDiff);
	
	// 通过svn仓库名查询svn配置文件差异信息
	SvnConfDiffInfo getSvnConfDiff(String svnRepoName);
	
	// 删除svn配置文件差异信息
	int remove(Long id);
	
	// 批量删除svn配置文件差异信息
	int batchRemove(Long[] ids);

}
