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
    public Friends[] listFriend;
    
    public Friends(String name, String host){
    	this.name = name;
    	this.host = host;
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
    
    /*Récupération liste d'amis*/
    public Friends[] getFriends() throws Exception{
      	File list = new File("Friends.xml");
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
        		//Création liste des amis
        		for (int j = 0; j < nodes.getLength(); j++){
        			listFriend[j] = new Friends(getValue("name", element), getValue("host", element));
        		}
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
