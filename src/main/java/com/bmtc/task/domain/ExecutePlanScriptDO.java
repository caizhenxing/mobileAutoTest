package com.bmtc.task.domain;

import java.io.Serializable;

/**
 * 执行计划和脚本关联的实体类
 * @author Administrator
 *
 */
public class ExecutePlanScriptDO implements Serializable{

	/**
	 * 属性
	 */
	private static final long serialVersionUID = 1L;
	// 主键id
	private String id;
	// 测试任务id
	private Long executePlanId;
	// 脚本id
	private Long scriptId;
	// 选中的caseName
	private String checkedCaseName;
	/**
	 * 构造
	 */
	public ExecutePlanScriptDO() {
		super();
	}
	public ExecutePlanScriptDO(Long executePlanId, Long scriptId,
			String checkedCaseName) {
		super();
		this.executePlanId = executePlanId;
		this.scriptId = scriptId;
		this.checkedCaseName = checkedCaseName;
	}
	/**
	 * set&get
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getExecutePlanId() {
		return executePlanId;
	}
	public void setExecutePlanId(Long executePlanId) {
		this.executePlanId = executePlanId;
	}
	public Long getScriptId() {
		return scriptId;
	}
	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}
	public String getCheckedCaseName() {
		return checkedCaseName;
	}
	public void setCheckedCaseName(String checkedCaseName) {
		this.checkedCaseName = checkedCaseName;
	}
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "ExecutePlanScriptDO [id=" + id + ", executePlanId="
				+ executePlanId + ", scriptId=" + scriptId
				+ ", checkedCaseName=" + checkedCaseName + "]";
	}
	
}
