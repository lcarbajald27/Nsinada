package pe.gob.oefa.apps.sinada.presentacion.util;

import pe.gob.oefa.apps.base.util.UtilEncrypt;

public class TestEncrypt {

	public TestEncrypt() {

	}

	public static void main(String[] args) {
		
		//Encrypt.init("J4v4 W3b");
		UtilEncrypt.init("123");
		
		try {
			
			
			
//			Base64 ed=new Base64();
			//
//						String encoded=new String(ed.encode("Hello".getBytes()));
			//
			//
//						String decoded=new String(ed.decode(encoded.getBytes()));
//						
//						System.out.println("codificado" + encoded);
//						System.out.println("descodificado" +decoded);
			
			//Abc123*@
			System.out.println(UtilEncrypt.encrypt("123"));
			//Qz5deV9FkI2v0iUUXPhCwg==
			//bX84J9JmufY5b/C44K6pew==
			//System.out.println(Encrypt.decrypt("Qz5deV9FkI2v0iUUXPhCwg=="));
			System.out.println(UtilEncrypt.decrypt("aMtlOsk1aBs=wqewqeewwewe"));
				
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		

	}

}
