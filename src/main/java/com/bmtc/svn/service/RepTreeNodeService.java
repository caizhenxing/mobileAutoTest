/**
 * 
 */
package com.bmtc.svn.service;

import java.util.List;

import org.tmatesoft.svn.core.io.SVNRepository;






import com.bmtc.common.domain.Tree;
import com.bmtc.svn.domain.SvnRootUser;

/**
 * 仓库目录结构树节点服务层
 * @author 
 * 
 */
public interface RepTreeNodeService {

	List<Tree<String>> getTreeNodes(Tree<String> parent, SvnRootUser svnRootUser, 
			List<Tree<String>> results, SVNRepository sVNRepository) throws Exception;

}
