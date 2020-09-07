/**
 * Ws_cmsServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.oefa.ws;

public class Ws_cmsServiceLocator extends org.apache.axis.client.Service implements pe.gob.oefa.ws.Ws_cmsService {

    public Ws_cmsServiceLocator() {
    }


    public Ws_cmsServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Ws_cmsServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ws_cmsPort
    private java.lang.String ws_cmsPort_address = "http://10.0.0.75:8280/WS_CMS/ws_cms";

    public java.lang.String getws_cmsPortAddress() {
        return ws_cmsPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ws_cmsPortWSDDServiceName = "ws_cmsPort";

    public java.lang.String getws_cmsPortWSDDServiceName() {
        return ws_cmsPortWSDDServiceName;
    }

    public void setws_cmsPortWSDDServiceName(java.lang.String name) {
        ws_cmsPortWSDDServiceName = name;
    }

    public pe.gob.oefa.ws.Ws_cms getws_cmsPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ws_cmsPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getws_cmsPort(endpoint);
    }

    public pe.gob.oefa.ws.Ws_cms getws_cmsPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pe.gob.oefa.ws.Ws_cmsPortBindingStub _stub = new pe.gob.oefa.ws.Ws_cmsPortBindingStub(portAddress, this);
            _stub.setPortName(getws_cmsPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setws_cmsPortEndpointAddress(java.lang.String address) {
        ws_cmsPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pe.gob.oefa.ws.Ws_cms.class.isAssignableFrom(serviceEndpointInterface)) {
                pe.gob.oefa.ws.Ws_cmsPortBindingStub _stub = new pe.gob.oefa.ws.Ws_cmsPortBindingStub(new java.net.URL(ws_cmsPort_address), this);
                _stub.setPortName(getws_cmsPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ws_cmsPort".equals(inputPortName)) {
            return getws_cmsPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "ws_cmsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.oefa.gob.pe/", "ws_cmsPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ws_cmsPort".equals(portName)) {
            setws_cmsPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
