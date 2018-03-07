package com.bmtc.task.domain;

import java.io.Serializable;

/**
 * task和scene关联实体
 * @author Administrator
 *
 */
public class TaskSceneDO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 属性
	 */
	// 主键id
	private Long id;
	// 任务ID
	private Long taskId;
	// 场景ID
	private Long sceneId;
	/**
	 * 构造
	 */
	public TaskSceneDO(Long id, Long taskId, Long sceneId) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.sceneId = sceneId;
	}
	public TaskSceneDO(Long taskId, Long sceneId) {
		super();
		this.taskId = taskId;
		this.sceneId = sceneId;
	}
	public TaskSceneDO() {
		super();
	}
	/**
	 * set&get
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "TaskSceneDO [id=" + id + ", taskId=" + taskId + ", sceneId="
				+ sceneId + "]";
	}
}
