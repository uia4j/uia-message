//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.27 at 01:30:39 下午 CST 
//


package uia.message.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BlockBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BlockBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" default="no description" />
 *       &lt;attribute name="optionBlock" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="optionValue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="optionEq" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="optionFx" type="{http://www.w3.org/2001/XMLSchema}string" default="EQ" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlockBaseType")
@XmlSeeAlso({
    BitBlockRefType.class,
    BitBlockSeqType.class,
    BitBlockType.class
})
public abstract class BlockBaseType {

    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute
    protected String desc;
    @XmlAttribute
    protected String optionBlock;
    @XmlAttribute
    protected String optionValue;
    @XmlAttribute
    protected Boolean optionEq;
    @XmlAttribute
    protected String optionFx;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        if (desc == null) {
            return "no description";
        } else {
            return desc;
        }
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the optionBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionBlock() {
        return optionBlock;
    }

    /**
     * Sets the value of the optionBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionBlock(String value) {
        this.optionBlock = value;
    }

    /**
     * Gets the value of the optionValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionValue() {
        return optionValue;
    }

    /**
     * Sets the value of the optionValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionValue(String value) {
        this.optionValue = value;
    }

    /**
     * Gets the value of the optionEq property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isOptionEq() {
        if (optionEq == null) {
            return true;
        } else {
            return optionEq;
        }
    }

    /**
     * Sets the value of the optionEq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOptionEq(Boolean value) {
        this.optionEq = value;
    }

    /**
     * Gets the value of the optionFx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionFx() {
        if (optionFx == null) {
            return "EQ";
        } else {
            return optionFx;
        }
    }

    /**
     * Sets the value of the optionFx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionFx(String value) {
        this.optionFx = value;
    }

}
