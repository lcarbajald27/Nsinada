/**
 * RptaWsBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.oefa.ws;

public class RptaWsBean  implements java.io.Serializable {
    private java.lang.String cadMsg;

    private java.lang.String codMsg;

    private java.lang.String uuid;

    public RptaWsBean() {
    }

    public RptaWsBean(
           java.lang.String cadMsg,
           java.lang.String codMsg,
           java.lang.String uuid) {
           this.cadMsg = cadMsg;
           this.codMsg = codMsg;
           this.uuid = uuid;
    }


    /**
     * Gets the cadMsg value for this RptaWsBean.
     * 
     * @return cadMsg
     */
    public java.lang.String getCadMsg() {
        return cadMsg;
    }


    /**
     * Sets the cadMsg value for this RptaWsBean.
     * 
     * @param cadMsg
     */
    public void setCadMsg(java.lang.String cadMsg) {
        this.cadMsg = cadMsg;
    }


    /**
     * Gets the codMsg value for this RptaWsBean.
     * 
     * @return codMsg
     */
    public java.lang.String getCodMsg() {
        return codMsg;
    }


    /**
     * Sets the codMsg value for this RptaWsBean.
     * 
     * @param codMsg
     */
    public void setCodMsg(java.lang.String codMsg) {
        this.codMsg = codMsg;
    }


    /**
     * Gets the uuid value for this RptaWsBean.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this RptaWsBean.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RptaWsBean)) return false;
        RptaWsBean other = (RptaWsBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cadMsg==null && other.getCadMsg()==null) || 
             (this.cadMsg!=null &&
              this.cadMsg.equals(other.getCadMsg()))) &&
            ((this.codMsg==null && other.getCodMsg()==null) || 
             (this.codMsg!=null &&
              this.codMsg.equals(other.getCodMsg()))) &&
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCadMsg() != null) {
            _hashCode += getCadMsg().hashCode();
        }
        if (getCodMsg() != null) {
            _hashCode += getCodMsg().hashCode();
        }
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RptaWsBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "rptaWsBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cadMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cadMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
