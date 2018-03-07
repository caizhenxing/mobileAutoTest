
package com.bmtc.system.utils.BMTC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Organization complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Organization">
 *   &lt;complexContent>
 *     &lt;extension base="{BMTC}BaseEntity">
 *       &lt;sequence>
 *         &lt;element name="OrganName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrganLevel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Findkey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OrganType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Parent" type="{BMTC}Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Organization", propOrder = {
    "organName",
    "organLevel",
    "findkey",
    "sequence",
    "organType",
    "parent"
})
public class Organization
    extends BaseEntity
{

    @XmlElement(name = "OrganName")
    protected String organName;
    @XmlElement(name = "OrganLevel")
    protected int organLevel;
    @XmlElement(name = "Findkey")
    protected String findkey;
    @XmlElement(name = "Sequence")
    protected int sequence;
    @XmlElement(name = "OrganType")
    protected int organType;
    @XmlElement(name = "Parent")
    protected Organization parent;

    /**
     * 获取organName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganName() {
        return organName;
    }

    /**
     * 设置organName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganName(String value) {
        this.organName = value;
    }

    /**
     * 获取organLevel属性的值。
     * 
     */
    public int getOrganLevel() {
        return organLevel;
    }

    /**
     * 设置organLevel属性的值。
     * 
     */
    public void setOrganLevel(int value) {
        this.organLevel = value;
    }

    /**
     * 获取findkey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFindkey() {
        return findkey;
    }

    /**
     * 设置findkey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFindkey(String value) {
        this.findkey = value;
    }

    /**
     * 获取sequence属性的值。
     * 
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * 设置sequence属性的值。
     * 
     */
    public void setSequence(int value) {
        this.sequence = value;
    }

    /**
     * 获取organType属性的值。
     * 
     */
    public int getOrganType() {
        return organType;
    }

    /**
     * 设置organType属性的值。
     * 
     */
    public void setOrganType(int value) {
        this.organType = value;
    }

    /**
     * 获取parent属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getParent() {
        return parent;
    }

    /**
     * 设置parent属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setParent(Organization value) {
        this.parent = value;
    }

}
