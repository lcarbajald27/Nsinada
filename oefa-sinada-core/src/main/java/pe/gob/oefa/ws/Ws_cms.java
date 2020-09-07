/**
 * Ws_cms.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pe.gob.oefa.ws;

public interface Ws_cms extends java.rmi.Remote {
    public pe.gob.oefa.ws.ContenidoECMBean leerDocumento(java.lang.String key, java.lang.String UUID) throws java.rmi.RemoteException;
    public pe.gob.oefa.ws.RptaWsBean registrarDocumento(java.lang.String key, java.lang.String nomFile, java.lang.String ruta, byte[] file) throws java.rmi.RemoteException;
    public pe.gob.oefa.ws.RptaWsBean actualizarDocumento(java.lang.String key, java.lang.String anteriorUUID, java.lang.String nomFile, java.lang.String ruta, byte[] file) throws java.rmi.RemoteException;
    public pe.gob.oefa.ws.RptaBcBean busquedaContenido(java.lang.String key, java.lang.String rutaSubCarpeta, java.lang.String parametro1, java.lang.String parametro2, java.lang.String parametro3, java.lang.String parametro4) throws java.rmi.RemoteException;
    public pe.gob.oefa.ws.RptaWsBean eliminarDocumento(java.lang.String key, java.lang.String UUID) throws java.rmi.RemoteException;
}
