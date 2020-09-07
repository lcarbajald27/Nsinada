/**
 * RptaBcBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.oefa.ws;

public class RptaBcBean  implements java.io.Serializable {
    private java.lang.String cadMsg;

    private java.lang.String codMsg;

    private pe.gob.oefa.ws.BeanFile[] list;

    public RptaBcBean() {
    }

    public RptaBcBean(
           java.lang.String cadMsg,
           java.lang.String codMsg,
           pe.gob.oefa.ws.BeanFile[] list) {
           this.cadMsg = cadMsg;
           this.codMsg = codMsg;
           this.list = list;
    }


    /**
     * Gets the cadMsg value for this RptaBcBean.
     * 
     * @return cadMsg
     */
    public java.lang.String getCadMsg() {
        return cadMsg;
    }


    /**
     * Sets the cadMsg value for this RptaBcBean.
     * 
     * @param cadMsg
     */
    public void setCadMsg(java.lang.String cadMsg) {
        this.cadMsg = cadMsg;
    }


    /**
     * Gets the codMsg value for this RptaBcBean.
     * 
     * @return codMsg
     */
    public java.lang.String getCodMsg() {
        return codMsg;
    }


    /**
     * Sets the codMsg value for this RptaBcBean.
     * 
     * @param codMsg
     */
    public void setCodMsg(java.lang.String codMsg) {
        this.codMsg = codMsg;
    }


    /**
     * Gets the list value for this RptaBcBean.
     * 
     * @return list
     */
    public pe.gob.oefa.ws.BeanFile[] getList() {
        return list;
    }


    /**
     * Sets the list value for this RptaBcBean.
     * 
     * @param list
     */
    public void setList(pe.gob.oefa.ws.BeanFile[] list) {
        this.list = list;
    }

    public pe.gob.oefa.ws.BeanFile getList(int i) {
        return this.list[i];
    }

    public void setList(int i, pe.gob.oefa.ws.BeanFile _value) {
        this.list[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RptaBcBean)) return false;
        RptaBcBean other = (RptaBcBean) obj;
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
            ((this.list==null && other.getList()==null) || 
             (this.list!=null &&
              java.util.Arrays.equals(this.list, other.getList())));
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
        if (getList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RptaBcBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "rptaBcBean"));
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
        elemField.setFieldName("list");
        elemField.setXmlName(new javax.xml.namespace.QName("", "list"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "beanFile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
