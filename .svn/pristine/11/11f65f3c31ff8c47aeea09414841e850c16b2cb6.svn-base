package com.bmtc.scene.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.bmtc.script.domain.Script;
import com.bmtc.task.domain.BMTCTask;
/**
 * 
 * @author Administrator
 *
 */
public class Scene implements Serializable{

	/**
	 * 属性
	 */
	private static final long serialVersionUID = 1L;
	// 场景ID
	private Long sceneId;
	// 场景名称
	private String sceneName;
	// 状态(逻辑删除标记:1,存在；0:删除)
	private Long status;
	// 修改用户ID
	private Long updateUserId;
	// 创建用户ID
	private Long createUserId;
	// 创建时间
	private Date gmtCreate;
	// 修改时间
	private Date gmtModified;
	// 测试数据路径
	private String testDataPaths;
	// 所属任务
	private List<BMTCTask> tasks;
	// 关联脚本
	private List<Script> scripts;
	/**
	 * 构造
	 */
	public Scene() {
		super();
	}
	public Scene(Long sceneId, String sceneName, Long status,
			Long updateUserId, Long createUserId, Date gmtCreate,
			Date gmtModified, String testDataPaths, List<BMTCTask> tasks,
			List<Script> scripts) {
		super();
		this.sceneId = sceneId;
		this.sceneName = sceneName;
		this.status = status;
		this.updateUserId = updateUserId;
		this.createUserId = createUserId;
		this.gmtCreate = gmtCreate;
		this.gmtModified = gmtModified;
		this.testDataPaths = testDataPaths;
		this.tasks = tasks;
		this.scripts = scripts;
	}

	/**
	 * set&get
	 */
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public String getTestDataPaths() {
		return testDataPaths;
	}
	public void setTestDataPaths(String testDataPaths) {
		this.testDataPaths = testDataPaths;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<BMTCTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<BMTCTask> tasks) {
		this.tasks = tasks;
	}
	public List<Script> getScripts() {
		return scripts;
	}
	public void setScripts(List<Script> scripts) {
		this.scripts = scripts;
	}
	/**
	 * toString
	 */
	@Override
	public String toString() {
		return "Scene [sceneId=" + sceneId + ", sceneName=" + sceneName
				+ ", status=" + status + ", updateUserId=" + updateUserId
				+ ", createUserId=" + createUserId + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + ", testDataPaths="
				+ testDataPaths + ", tasks=" + tasks + ", scripts=" + scripts
				+ "]";
	}
	
}