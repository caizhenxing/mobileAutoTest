package com.bmtc.svn.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SvnRepoTree<T> {
	
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 显示节点文本
	 */
	private String text;
	
	/**
	 * svn仓库路径
	 */
	private String svnRepoPath;
	
	/**
	 * svn仓库url
	 */
	private String svnRepoUrl;
	
	/**
	 * svn仓库描述
	 */
	private String svnRepoDes;

	/**
	 * 节点状态，open closed
	 */
	private Map<String, Object> state;
	/**
	 * 节点是否被选中 true false
	 */
	private boolean checked = false;
	/**
	 * 节点属性
	 */
	private Map<String, Object> attributes;

	/**
	 * 节点的子节点
	 */
	private List<SvnRepoTree<T>> children = new ArrayList<SvnRepoTree<T>>();

	/**
	 * 父ID
	 */
	private String parentId;
	/**
	 * 是否有父节点
	 */
	private boolean hasParent = false;
	/**
	 * 是否有子节点
	 */
	private boolean hasChildren = false;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSvnRepoPath() {
		return svnRepoPath;
	}

	public void setSvnRepoPath(String svnRepoPath) {
		this.svnRepoPath = svnRepoPath;
	}

	public String getSvnRepoUrl() {
		return svnRepoUrl;
	}

	public void setSvnRepoUrl(String svnRepoUrl) {
		this.svnRepoUrl = svnRepoUrl;
	}

	public String getSvnRepoDes() {
		return svnRepoDes;
	}

	public void setSvnRepoDes(String svnRepoDes) {
		this.svnRepoDes = svnRepoDes;
	}

	public Map<String, Object> getState() {
		return state;
	}

	public void setState(Map<String, Object> state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<SvnRepoTree<T>> getChildren() {
		return children;
	}

	public void setChildren(List<SvnRepoTree<T>> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public SvnRepoTree(String id, String text, String svnRepoPath,
			String svnRepoUrl, String svnRepoDes, Map<String, Object> state,
			boolean checked, Map<String, Object> attributes,
			List<SvnRepoTree<T>> children, String parentId, boolean hasParent,
			boolean hasChildren) {
		super();
		this.id = id;
		this.text = text;
		this.svnRepoPath = svnRepoPath;
		this.svnRepoUrl = svnRepoUrl;
		this.svnRepoDes = svnRepoDes;
		this.state = state;
		this.checked = checked;
		this.attributes = attributes;
		this.children = children;
		this.parentId = parentId;
		this.hasParent = hasParent;
		this.hasChildren = hasChildren;
	}

	public SvnRepoTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
