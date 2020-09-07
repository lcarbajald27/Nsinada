package pe.gob.oefa.ws;

public class Ws_cmsProxy implements pe.gob.oefa.ws.Ws_cms {
  private String _endpoint = null;
  private pe.gob.oefa.ws.Ws_cms ws_cms = null;
  
  public Ws_cmsProxy() {
    _initWs_cmsProxy();
  }
  
  public Ws_cmsProxy(String endpoint) {
    _endpoint = endpoint;
    _initWs_cmsProxy();
  }
  
  private void _initWs_cmsProxy() {
    try {
      ws_cms = (new pe.gob.oefa.ws.Ws_cmsServiceLocator()).getws_cmsPort();
      if (ws_cms != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ws_cms)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ws_cms)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ws_cms != null)
      ((javax.xml.rpc.Stub)ws_cms)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pe.gob.oefa.ws.Ws_cms getWs_cms() {
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms;
  }
  
  public pe.gob.oefa.ws.ContenidoECMBean leerDocumento(java.lang.String key, java.lang.String UUID) throws java.rmi.RemoteException{
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms.leerDocumento(key, UUID);
  }
  
  public pe.gob.oefa.ws.RptaWsBean registrarDocumento(java.lang.String key, java.lang.String nomFile, java.lang.String ruta, byte[] file) throws java.rmi.RemoteException{
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms.registrarDocumento(key, nomFile, ruta, file);
  }
  
  public pe.gob.oefa.ws.RptaWsBean actualizarDocumento(java.lang.String key, java.lang.String anteriorUUID, java.lang.String nomFile, java.lang.String ruta, byte[] file) throws java.rmi.RemoteException{
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms.actualizarDocumento(key, anteriorUUID, nomFile, ruta, file);
  }
  
  public pe.gob.oefa.ws.RptaBcBean busquedaContenido(java.lang.String key, java.lang.String rutaSubCarpeta, java.lang.String parametro1, java.lang.String parametro2, java.lang.String parametro3, java.lang.String parametro4) throws java.rmi.RemoteException{
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms.busquedaContenido(key, rutaSubCarpeta, parametro1, parametro2, parametro3, parametro4);
  }
  
  public pe.gob.oefa.ws.RptaWsBean eliminarDocumento(java.lang.String key, java.lang.String UUID) throws java.rmi.RemoteException{
    if (ws_cms == null)
      _initWs_cmsProxy();
    return ws_cms.eliminarDocumento(key, UUID);
  }
  
  
}