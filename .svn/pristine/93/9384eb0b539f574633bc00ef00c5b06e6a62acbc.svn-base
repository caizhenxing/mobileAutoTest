/**
 * 
 */
package com.bmtc.svn.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;

import com.bmtc.common.domain.Tree;
import com.bmtc.svn.domain.SvnRootUser;
import com.bmtc.svn.service.RepTreeNodeService;
import com.bmtc.svn.service.RepositoryService;

/**
 * 仓库目录结构树节点服务层
 * @author lpf7161
 * 
 */
@Service("RepTreeNodeService")
public class RepTreeNodeServiceImpl implements RepTreeNodeService {
	
	private static final String AND = "$AND$";
	
	/**
	 * 日志
	 */
	private final Logger LOG = Logger.getLogger(RepTreeNodeServiceImpl.class);
	
	/**
	 * 仓库服务层
	 */
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public List<Tree<String>> getTreeNodes(Tree<String> parent, SvnRootUser svnRootUser, 
			List<Tree<String>> results, SVNRepository sVNRepository) throws Exception {
		
		//当前树节点
		Tree<String> currentNode = new Tree<String>();	
		currentNode.setParentId(parent.getParentId());
		
		// svnRepoName svn仓库
		String svnRepoName = svnRootUser.getSvnRepoName();
		// svn超级用户的用户名
		String svnUserName = svnRootUser.getSvnUserName();
		// svn超级用户的密码
		String svnPassword = svnRootUser.getSvnPassword();
		
		// svn路径
		String path = svnRootUser.getPath();
		
		// 设置当前节点ID
		currentNode.setId(path);
		// 设置当前节点文本
		currentNode.setText(svnRootUser.getSvnItem());
		
		path = StringUtils.replace(path, AND, "&");
		if(StringUtils.isBlank(svnRepoName)) {
			LOG.warn("svnRepoName is blank ");
			return null;
		}
		try{
			Collection<SVNDirEntry> entries = repositoryService.getDir(svnRepoName, path, svnUserName, svnPassword, sVNRepository);
			if(entries == null) {
				return null;
			}
//	    	 Collection<SVNDirEntry> entries = repository.getDir("/trunk", SVNRevision.HEAD.getNumber(), properties, (Collection) null);
	    	 for (SVNDirEntry svnDirEntry : entries) {
//				System.out.println(svnDirEntry);
//	    		System.out.println(svnDirEntry.getName()//文件夹或文件名
//	    				+","+svnDirEntry.getKind()//类型,参考SVNNodeKind.FILE,SVNNodeKind.DIR
//	    				+","+svnDirEntry.getRevision()//版本
//	    				+","+svnDirEntry.getAuthor()//作者
//	    				+","+svnDirEntry.getSize()//如果kind是SVNNodeKind.FILE时返回文件的大小
//	    				+","+svnDirEntry.getDate());//日期
	    		 Tree<String> tree = new Tree<String>();
	    		 // 设置tree的节点文本，内容为文件夹或者文件名称
	    		 tree.setText(svnDirEntry.getName());	
	    		 //tree.setText(svnDirEntry.getRelativePath());
	    		 // 设置tree的父id为上一级目录，path为相对于svn仓库的路径
	    		 tree.setParentId(path);
	    		 Map<String, Object> attributes = new HashMap<String, Object>();	 
	    		 //tree.setChildren(!SVNNodeKind.FILE.equals(svnDirEntry.getKind())); //是否有子节点，叶子?
	    		 // 设置svnPath属性
	    		 if(path.endsWith("/")) {
	    			 attributes.put("svnPath", path + StringUtils.replace(svnDirEntry.getName(), "&", AND));	    			 
	    			 tree.setAttributes(attributes);
	    		 } else {
	    			 attributes.put("svnPath", path + "/" + StringUtils.replace(svnDirEntry.getName(), "&", AND));
	    			 tree.setAttributes(attributes);
	    		 }
	    		 // 设置tree的id为相对于svn仓库的路径
	    		 tree.setId(tree.getAttributes().get("svnPath").toString());
	    		 // 如果是文件夹的话递归调用getTreeNodes
	    		 if(SVNNodeKind.DIR.equals(svnDirEntry.getKind())) {
	    			 // 保存tree的节点文本给svnItem属性
	    			 svnRootUser.setSvnItem(tree.getText());
	    			 // 重新修改svn文件夹路径（相对于svn仓库的路径），为下一次递归调用做准备
	    			 svnRootUser.setPath(tree.getAttributes().get("svnPath").toString());
	    			 getTreeNodes(tree, svnRootUser, results, sVNRepository);
	    		 } else {
	    			 // 如果是文件则将tree添加到结果集合中
	    			 results.add(tree);
	    		 }
			}
			//Collections.sort(results);// 排序		
		} catch (Exception e) {
    		LOG.error(e.getMessage());
			e.printStackTrace();
    		results.clear();
    		Tree<String> errorNode = new Tree<String>();
    		errorNode.setText(e.getMessage());
    		results.add(errorNode);
    		return results;
		}
		
		// 将当前节点添加到结果集中
		results.add(currentNode);
    	return results;
	}
}
