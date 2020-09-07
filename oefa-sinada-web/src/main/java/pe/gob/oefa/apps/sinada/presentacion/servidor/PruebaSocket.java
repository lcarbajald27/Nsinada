package pe.gob.oefa.apps.sinada.presentacion.servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.websocket.server.ServerEndpoint;



import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ServerEndpoint(value = "/wschat")
public class PruebaSocket{

	 private static ArrayList<Session> sessionList = new ArrayList<Session>();
	 
	   @OnOpen
	    public void onOpen(Session session){
	        try{
	            sessionList.add(session);
	            //asynchronous communication
	            session.getBasicRemote().sendText("Hello!");
	        }catch(IOException e){}
	    }
	    
	    @OnClose
	    public void onClose(Session session){
	        sessionList.remove(session);
	    }
	    
	    @OnMessage
	    public void onMessage(String msg){
	        try{
	            for(Session session : sessionList){
	                //asynchronous communication
	                session.getBasicRemote().sendText(msg);
	            }
	        }catch(IOException e){}
	    }

}
