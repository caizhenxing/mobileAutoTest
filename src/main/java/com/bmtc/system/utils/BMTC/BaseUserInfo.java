
package com.bmtc.system.utils.BMTC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>BaseUserInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BaseUserInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{BMTC}BaseEntity">
 *       &lt;sequence>
 *         &lt;element name="LoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PhoneNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DBEnable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="LastLoginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IsAllPermission" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsLogin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RoleID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DelFlag" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrgCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DeptID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GroupID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="goToUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseUserInfo", propOrder = {
    "loginName",
    "password",
    "userName",
    "phoneNumber",
    "mailAddress",
    "dbEnable",
    "lastLoginDate",
    "isAllPermission",
    "isLogin",
    "roleID",
    "delFlag",
    "orgCode",
    "deptID",
    "groupID",
    "enabled",
    "goToUrl"
})
@XmlSeeAlso({
    UserInfo.class
})
public class BaseUserInfo
    extends BaseEntity
{

    @XmlElement(name = "LoginName")
    protected String loginName;
    @XmlElement(name = "Password")
    protected String password;
    @XmlElement(name = "UserName")
    protected String userName;
    @XmlElement(name = "PhoneNumber")
    protected String phoneNumber;
    @XmlElement(name = "MailAddress")
    protected String mailAddress;
    @XmlElement(name = "DBEnable", required = true, type = Boolean.class, nillable = true)
    protected Boolean dbEnable;
    @XmlElement(name = "LastLoginDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLoginDate;
    @XmlElement(name = "IsAllPermission")
    protected boolean isAllPermission;
    @XmlElement(name = "IsLogin")
    protected boolean isLogin;
    @XmlElement(name = "RoleID")
    protected int roleID;
    @XmlElement(name = "DelFlag")
    protected int delFlag;
    @XmlElement(name = "OrgCode")
    protected int orgCode;
    @XmlElement(name = "DeptID")
    protected int deptID;
    @XmlElement(name = "GroupID")
    protected int groupID;
    @XmlElement(name = "Enabled")
    protected boolean enabled;
    protected String goToUrl;

    /**
     * 获取loginName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置loginName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginName(String value) {
        this.loginName = value;
    }

    /**
     * 获取password属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * 获取userName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * 获取phoneNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置phoneNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * 获取mailAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * 设置mailAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailAddress(String value) {
        this.mailAddress = value;
    }

    /**
     * 获取dbEnable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDBEnable() {
        return dbEnable;
    }

    /**
     * 设置dbEnable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDBEnable(Boolean value) {
        this.dbEnable = value;
    }

    /**
     * 获取lastLoginDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * 设置lastLoginDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastLoginDate(XMLGregorianCalendar value) {
        this.lastLoginDate = value;
    }

    /**
     * 获取isAllPermission属性的值。
     * 
     */
    public boolean isIsAllPermission() {
        return isAllPermission;
    }

    /**
     * 设置isAllPermission属性的值。
     * 
     */
    public void setIsAllPermission(boolean value) {
        this.isAllPermission = value;
    }

    /**
     * 获取isLogin属性的值。
     * 
     */
    public boolean isIsLogin() {
        return isLogin;
    }

    /**
     * 设置isLogin属性的值。
     * 
     */
    public void setIsLogin(boolean value) {
        this.isLogin = value;
    }

    /**
     * 获取roleID属性的值。
     * 
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * 设置roleID属性的值。
     * 
     */
    public void setRoleID(int value) {
        this.roleID = value;
    }

    /**
     * 获取delFlag属性的值。
     * 
     */
    public int getDelFlag() {
        return delFlag;
    }

    /**
     * 设置delFlag属性的值。
     * 
     */
    public void setDelFlag(int value) {
        this.delFlag = value;
    }

    /**
     * 获取orgCode属性的值。
     * 
     */
    public int getOrgCode() {
        return orgCode;
    }

    /**
     * 设置orgCode属性的值。
     * 
     */
    public void setOrgCode(int value) {
        this.orgCode = value;
    }

    /**
     * 获取deptID属性的值。
     * 
     */
    public int getDeptID() {
        return deptID;
    }

    /**
     * 设置deptID属性的值。
     * 
     */
    public void setDeptID(int value) {
        this.deptID = value;
    }

    /**
     * 获取groupID属性的值。
     * 
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * 设置groupID属性的值。
     * 
     */
    public void setGroupID(int value) {
        this.groupID = value;
    }

    /**
     * 获取enabled属性的值。
     * 
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置enabled属性的值。
     * 
     */
    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    /**
     * 获取goToUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoToUrl() {
        return goToUrl;
    }

    /**
     * 设置goToUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoToUrl(String value) {
        this.goToUrl = value;
    }

}
