//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.06 at 10:07:24 AM ICT 
//


package id.go.pajak.ibk.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ljkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ljkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="npwpLjk" type="{}npwpType"/>
 *         &lt;element name="namaLjk" type="{}namaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ljkType", propOrder = {
    "npwpLjk",
    "namaLjk"
})
public class LjkType {

    @XmlElement(required = true)
    protected String npwpLjk;
    @XmlElement(required = true)
    protected String namaLjk;

    /**
     * Gets the value of the npwpLjk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNpwpLjk() {
        return npwpLjk;
    }

    /**
     * Sets the value of the npwpLjk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNpwpLjk(String value) {
        this.npwpLjk = value;
    }

    /**
     * Gets the value of the namaLjk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamaLjk() {
        return namaLjk;
    }

    /**
     * Sets the value of the namaLjk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamaLjk(String value) {
        this.namaLjk = value;
    }

}
