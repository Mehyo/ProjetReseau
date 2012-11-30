package socialNetwork.src;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Friends{

	private String name;
	private String host;
	private boolean status;

	public Friends(String name, String host, String status){
		this.name = name;
		this.host = host;
		System.out.println(status);
		if(status.compareTo("false") == 0)
			this.status = false;
		else
			this.status = true;
	}

	public String nameFriend(){
		return this.name;
	}

	public String hostFriend(){
		return this.host;
	}

	public String toString(){
		return "(" + this.name + "," + this.host +")";
	}
	
	public boolean isMyFriend(){	
		return this.status;
	}
	
	public void setToFriend(){
		this.status = true;
		//change dans xml
	}
	
	public void setToStranger(){
		this.status = false;
		//change dans xml
	}

	/*Récupération liste d'amis*/
	
	public static Friends[] getFriends() throws Exception{
		Friends[] listFriend;
		File list = new File("friendList.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(list);
		doc.getDocumentElement().normalize();
		NodeList nodes = doc.getElementsByTagName("friend");
		listFriend = new Friends[nodes.getLength()];
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				listFriend[i] = new Friends(getValue("name", element), getValue("host", element), getValue("friendStatus", element));
			}
		}
		return listFriend;
	}

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
