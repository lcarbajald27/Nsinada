/**
 * ContenidoECMBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.oefa.ws;

public class ContenidoECMBean  implements java.io.Serializable {
    private java.lang.String cadMsg;

    private java.lang.String codMsg;

    private byte[] contenidoFile;

    private java.lang.String encoding;

    private java.lang.String nombreArchivo;

    private java.lang.String ruta;

    private java.lang.String tamanio;

    private java.lang.String tipoContenido;

    public ContenidoECMBean() {
    }

    public ContenidoECMBean(
           java.lang.String cadMsg,
           java.lang.String codMsg,
           byte[] contenidoFile,
           java.lang.String encoding,
           java.lang.String nombreArchivo,
           java.lang.String ruta,
           java.lang.String tamanio,
           java.lang.String tipoContenido) {
           this.cadMsg = cadMsg;
           this.codMsg = codMsg;
           this.contenidoFile = contenidoFile;
           this.encoding = encoding;
           this.nombreArchivo = nombreArchivo;
           this.ruta = ruta;
           this.tamanio = tamanio;
           this.tipoContenido = tipoContenido;
    }


    /**
     * Gets the cadMsg value for this ContenidoECMBean.
     * 
     * @return cadMsg
     */
    public java.lang.String getCadMsg() {
        return cadMsg;
    }


    /**
     * Sets the cadMsg value for this ContenidoECMBean.
     * 
     * @param cadMsg
     */
    public void setCadMsg(java.lang.String cadMsg) {
        this.cadMsg = cadMsg;
    }


    /**
     * Gets the codMsg value for this ContenidoECMBean.
     * 
     * @return codMsg
     */
    public java.lang.String getCodMsg() {
        return codMsg;
    }


    /**
     * Sets the codMsg value for this ContenidoECMBean.
     * 
     * @param codMsg
     */
    public void setCodMsg(java.lang.String codMsg) {
        this.codMsg = codMsg;
    }


    /**
     * Gets the contenidoFile value for this ContenidoECMBean.
     * 
     * @return contenidoFile
     */
    public byte[] getContenidoFile() {
        return contenidoFile;
    }


    /**
     * Sets the contenidoFile value for this ContenidoECMBean.
     * 
     * @param contenidoFile
     */
    public void setContenidoFile(byte[] contenidoFile) {
        this.contenidoFile = contenidoFile;
    }


    /**
     * Gets the encoding value for this ContenidoECMBean.
     * 
     * @return encoding
     */
    public java.lang.String getEncoding() {
        return encoding;
    }


    /**
     * Sets the encoding value for this ContenidoECMBean.
     * 
     * @param encoding
     */
    public void setEncoding(java.lang.String encoding) {
        this.encoding = encoding;
    }


    /**
     * Gets the nombreArchivo value for this ContenidoECMBean.
     * 
     * @return nombreArchivo
     */
    public java.lang.String getNombreArchivo() {
        return nombreArchivo;
    }


    /**
     * Sets the nombreArchivo value for this ContenidoECMBean.
     * 
     * @param nombreArchivo
     */
    public void setNombreArchivo(java.lang.String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }


    /**
     * Gets the ruta value for this ContenidoECMBean.
     * 
     * @return ruta
     */
    public java.lang.String getRuta() {
        return ruta;
    }


    /**
     * Sets the ruta value for this ContenidoECMBean.
     * 
     * @param ruta
     */
    public void setRuta(java.lang.String ruta) {
        this.ruta = ruta;
    }


    /**
     * Gets the tamanio value for this ContenidoECMBean.
     * 
     * @return tamanio
     */
    public java.lang.String getTamanio() {
        return tamanio;
    }


    /**
     * Sets the tamanio value for this ContenidoECMBean.
     * 
     * @param tamanio
     */
    public void setTamanio(java.lang.String tamanio) {
        this.tamanio = tamanio;
    }


    /**
     * Gets the tipoContenido value for this ContenidoECMBean.
     * 
     * @return tipoContenido
     */
    public java.lang.String getTipoContenido() {
        return tipoContenido;
    }


    /**
     * Sets the tipoContenido value for this ContenidoECMBean.
     * 
     * @param tipoContenido
     */
    public void setTipoContenido(java.lang.String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ContenidoECMBean)) return false;
        ContenidoECMBean other = (ContenidoECMBean) obj;
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
            ((this.contenidoFile==null && other.getContenidoFile()==null) || 
             (this.contenidoFile!=null &&
              java.util.Arrays.equals(this.contenidoFile, other.getContenidoFile()))) &&
            ((this.encoding==null && other.getEncoding()==null) || 
             (this.encoding!=null &&
              this.encoding.equals(other.getEncoding()))) &&
            ((this.nombreArchivo==null && other.getNombreArchivo()==null) || 
             (this.nombreArchivo!=null &&
              this.nombreArchivo.equals(other.getNombreArchivo()))) &&
            ((this.ruta==null && other.getRuta()==null) || 
             (this.ruta!=null &&
              this.ruta.equals(other.getRuta()))) &&
            ((this.tamanio==null && other.getTamanio()==null) || 
             (this.tamanio!=null &&
              this.tamanio.equals(other.getTamanio()))) &&
            ((this.tipoContenido==null && other.getTipoContenido()==null) || 
             (this.tipoContenido!=null &&
              this.tipoContenido.equals(other.getTipoContenido())));
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
        if (getContenidoFile() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContenidoFile());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContenidoFile(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEncoding() != null) {
            _hashCode += getEncoding().hashCode();
        }
        if (getNombreArchivo() != null) {
            _hashCode += getNombreArchivo().hashCode();
        }
        if (getRuta() != null) {
            _hashCode += getRuta().hashCode();
        }
        if (getTamanio() != null) {
            _hashCode += getTamanio().hashCode();
        }
        if (getTipoContenido() != null) {
            _hashCode += getTipoContenido().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ContenidoECMBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "contenidoECMBean"));
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
        elemField.setFieldName("contenidoFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contenidoFile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encoding");
        elemField.setXmlName(new javax.xml.namespace.QName("", "encoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreArchivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nombreArchivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ruta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tamanio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tamanio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoContenido");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoContenido"));
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
