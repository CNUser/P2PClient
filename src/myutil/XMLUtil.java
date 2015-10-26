package myutil;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	public  static Document createDocument() {
		try {
			// ����saxReader����  
	        SAXReader reader = new SAXReader();  
	        // ͨ��read������ȡһ���ļ� ת����Document����  
	        Document document = reader.read(new File("src/info.xml")); 
	        
	        return document;
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String getServerIP(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverIP").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getServerPort(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("serverPort").getTextTrim();
        
        return serverIP;
        
	}
	
	public static String getReceivePort(Document document) {
		//��ȡ���ڵ�Ԫ�ض���  
        Element root = document.getRootElement(); 
        
        String serverIP =  root.element("receivePort").getTextTrim();
        
        return serverIP;
        
	}
}

