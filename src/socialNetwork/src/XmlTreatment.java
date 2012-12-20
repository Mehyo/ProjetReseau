package socialNetwork.src;

import java.io.*;

import org.jdom2.*;
import org.jdom2.output.*;
import org.jdom2.input.*;
import org.jdom2.filter.*;

import socialNetwork.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class XmlTreatment {

	static Element racine = new Element("personnes");
	static org.jdom2.Document document = new Document(racine);

	static void readFileXML(String fichier) throws Exception{
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
	}
	
	static void saveFile(String fichier){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		}
		catch(java.io.IOException e){}
	}

	public static void setUserName(){
		try{
			readFileXML("friendList.xml");
			List listName = racine.getChildren("me");
			Iterator i = listName.iterator();
			Element e = (Element) i.next();
			Main.userName = e.getChild("name").getText();
		}catch(Exception e){}
	}

	public static void deleteFriendXML(String name){
		List listElement = racine.getChildren("name");
		Iterator i = listElement.iterator();
		while(i.hasNext()){
			Element courant = (Element)i.next();
			if(courant.getChild(name)!=null){
				courant.removeChild(name);
				courant.setName("name_modified");
			}
		}
	}
	
	/*Récupération liste d'amis*/
	public static void getFriendsXML(){
		try {
			readFileXML("friendList.xml");
			racine.getChildren();
			List listTmp = racine.getChildren("friend");
			Iterator i = listTmp.iterator();
			while(i.hasNext()){
				Element courant = (Element)i.next();
				Friends.friendList.add(new Friends(courant.getChild("name").getText(), courant.getChild("host").getText(), courant.getChild("friendStatus").toString()));
			}
		} catch (Exception e) {}
	}

	public static void addFriendXML(Friends newFriends){
		try{
		readFileXML("friendList.xml");
		}catch(Exception e){}
		
		Element friend = new Element("friend");
		racine.addContent(friend);
		
		Element name = new Element("name");
		name.setText(newFriends.getName());
		friend.addContent(name);
		
		Element host = new Element("host");
		host.setText(newFriends.getHost());
		friend.addContent(host);
		
		Element status = new Element("friendStatus");
		status.setText("false");
		friend.addContent(status);
		
		Element picture = new Element("picture");
		picture.setText("none");
		friend.addContent(picture);
		
		saveFile("friendList.xml");
	}
	
	public static void getStatusXML(){
		try {
			readFileXML("statusList.xml");
			racine.getChildren();
			List listTmp = racine.getChildren("status");
			Iterator i = listTmp.iterator();
			while(i.hasNext()){
				Element courant = (Element)i.next();
				Status.listStatus.add(new Status(Main.userName,courant.getChild("content").getText(), courant.getChild("date").getText()));
			}
		} catch (Exception e) {}
	}
	
	public static void addStatusXML(Status newStatus){
		try{
			readFileXML("statusList.xml");
			}catch(Exception e){}
		
		Element status = new Element("status");
		racine.addContent(status);
		
		Element type = new Element("type");
		type.setText(newStatus.getType());
		status.addContent(type);
		
		Element content= new Element("content");
		content.setText(newStatus.getContent());
		status.addContent(content);
		
		Element date = new Element("date");
		date.setText(newStatus.getDate());
		status.addContent(date);
		
		Element comment= new Element("commentary");
		status.addContent(comment);
		
		saveFile("statusList.xml");
		
	}
	
	public static void addCommentary(String s_Date, String c_Owner,	String c_Content) {
		try{
			readFileXML("statusList.xml");
			}catch(Exception e){}
		
		racine.getChildren();
		List listTmp = racine.getChildren("status");
		Iterator i = listTmp.iterator();
		while(i.hasNext()){
			Element courant = (Element)i.next();
			if(s_Date.equals(courant.getChild("name").getText())){

				Element comment = new Element("commentary");
				racine.addContent(comment);
				
				Element owner = new Element("owner");
				owner.setText(c_Owner);
				comment.addContent(owner);
				
				Element content= new Element("content");
				content.setText(c_Content);
				comment.addContent(content);
				
				break;
			}
		}	
		
		
		saveFile("statusList.xml");
	}
}