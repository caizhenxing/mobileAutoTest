package com.bmtc.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long userId;
    // 用户名
    private String username;
    // 用户真实姓名
    private String name;
    // 密码
    private String password;
    // 部门
   // private Long deptId;
    // 部门名称
    private String deptName;
    // 邮箱
    private String email;
    // 手机号
    private String mobile;
    // 状态 0:禁用，1:正常, 2:待审批
    private Integer status;
    // 创建用户id
    private Long userIdCreate;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    //角色
    private List<Long> roleIds;
    //性别
    private Long sex;
    //出身日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    //图片ID
    private Long picId;
    //现居住地
    private String liveAddress;
    //爱好
    private String hobby;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
    //
    private byte enabled;
    //最后一次登录时间
    private Date lastLoginDate;
    //是否拥有所有权限
    private byte isAllPermission;
    //是否登录
    private byte isLogin;
    //删除状态
    private byte deleted;
    
    private String deptIds;
    
    private List<DeptDO> deptDO;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
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

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public byte getEnabled() {
		return enabled;
	}

	public void setEnabled(byte enabled) {
		this.enabled = enabled;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public byte getIsAllPermission() {
		return isAllPermission;
	}

	public void setIsAllPermission(byte isAllPermission) {
		this.isAllPermission = isAllPermission;
	}

	public byte getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(byte isLogin) {
		this.isLogin = isLogin;
	}

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public List<DeptDO> getDeptDO() {
		return deptDO;
	}

	public void setDeptDO(List<DeptDO> deptDO) {
		this.deptDO = deptDO;
	}

	@Override
	public String toString() {
		return "UserDO [userId=" + userId + ", username=" + username
				+ ", name=" + name + ", password=" + password + ", deptName="
				+ deptName + ", email=" + email + ", mobile=" + mobile
				+ ", status=" + status + ", userIdCreate=" + userIdCreate
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", roleIds=" + roleIds + ", sex=" + sex + ", birth=" + birth
				+ ", picId=" + picId + ", liveAddress=" + liveAddress
				+ ", hobby=" + hobby + ", province=" + province + ", city="
				+ city + ", district=" + district + ", enabled=" + enabled
				+ ", lastLoginDate=" + lastLoginDate + ", isAllPermission="
				+ isAllPermission + ", isLogin=" + isLogin + ", deleted="
				+ deleted + ", deptIds=" + deptIds + ", deptDO=" + deptDO + "]";
	}

}
