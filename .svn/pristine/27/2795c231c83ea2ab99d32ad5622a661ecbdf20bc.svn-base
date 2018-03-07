package com.bmtc.task.domain;

import java.io.Serializable;

/**
 * 测试任务和脚本关联的实体类
 * @author Administrator
 *
 */
public class TaskScriptDO implements Serializable{

	/**
	 * 属性
	 */
	private static final long serialVersionUID = 1L;
	// 主键id
	private String id;
	// 测试任务id
	private Long taskId;
	// 脚本id
	private Long scriptId;
	// 选中的caseName
	private String checkedCaseName;
	/**
	 * 构造
	 */
	public TaskScriptDO() {
		super();
	}
	public TaskScriptDO(Long taskId, Long scriptId) {
		super();
		this.taskId = taskId;
		this.scriptId = scriptId;
	}
	public TaskScriptDO(Long taskId, Long scriptId,
			String checkedCaseName) {
		super();
		this.taskId = taskId;
		this.scriptId = scriptId;
		this.checkedCaseName = checkedCaseName;
	}
	/**
	 * set&get
	 */
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getScriptId() {
		return scriptId;
	}
	public void setScriptId(Long scriptId) {
		this.scriptId = scriptId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return "TaskScriptDO [id=" + id + ", taskId=" + taskId
				+ ", scriptId=" + scriptId + ", checkedCaseName="
				+ checkedCaseName + "]";
	}

}
