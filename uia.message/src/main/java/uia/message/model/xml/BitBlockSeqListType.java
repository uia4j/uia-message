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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BitBlockSeqListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BitBlockSeqListType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://message.uia/model/xml}BitBlockSeqType">
 *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="countBlock" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="countFactor" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="countFactorOp" type="{http://www.w3.org/2001/XMLSchema}string" default="/" />
 *       &lt;attribute name="countFx" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BitBlockSeqListType")
public class BitBlockSeqListType
    extends BitBlockSeqType
{

    @XmlAttribute
    protected Integer count;
    @XmlAttribute
    protected String countBlock;
    @XmlAttribute
    protected Integer countFactor;
    @XmlAttribute
    protected String countFactorOp;
    @XmlAttribute
    protected String countFx;

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getCount() {
        if (count == null) {
            return  1;
        } else {
            return count;
        }
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the countBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountBlock() {
        return countBlock;
    }

    /**
     * Sets the value of the countBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountBlock(String value) {
        this.countBlock = value;
    }

    /**
     * Gets the value of the countFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getCountFactor() {
        if (countFactor == null) {
            return  1;
        } else {
            return countFactor;
        }
    }

    /**
     * Sets the value of the countFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCountFactor(Integer value) {
        this.countFactor = value;
    }

    /**
     * Gets the value of the countFactorOp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountFactorOp() {
        if (countFactorOp == null) {
            return "/";
        } else {
            return countFactorOp;
        }
    }

    /**
     * Sets the value of the countFactorOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountFactorOp(String value) {
        this.countFactorOp = value;
    }

    /**
     * Gets the value of the countFx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountFx() {
        return countFx;
    }

    /**
     * Sets the value of the countFx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountFx(String value) {
        this.countFx = value;
    }

}
