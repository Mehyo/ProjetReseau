package socialNetwork.src;

import java.net.InetAddress;
import java.util.ArrayList;
import socialNetwork.src.Message;

public class Status {

	private String sContent;
	private String sOwner;
	private String sDate;
	private Commentary sComment;
	public static ArrayList<Status> listStatus;
	
	public Status(String owner, String content, String date ,Commentary comment){
		this.sContent = content;
		this.sOwner = owner;
		this.sDate = date;
		this.sComment = comment;
	}
	
	public Status(){
		this("", "", "", new Commentary());
	}
	
	public Status(String owner, String content, String date){
		this(owner, content, date, new Commentary());
	}
	
		
	public void setOwner(String owner){
		this.sOwner = owner;
	}
	
	public void setContent(String content){
		this.sContent = content;
	}
	
	public String getOwner(){
		return this.sOwner;
	}
	
	public String getContent(){
		return this.sContent;
	}
	
	public String getDate(){
		return this.sDate;
	}
	
	public void setDate(String date){
		this.sDate = date;
	}
		
	public void setCommentary(String owner, String content){
		this.sComment = new Commentary(owner, content);
	}
	
	public Commentary getCommentary(){
		return this.sComment;
	}
	
	public static void createListeStatus(){
		/*Xml import */
	}
	
	
	public static void createNewStatus(String content){
		String date = ""; //chope la date systeme
		Status status = new Status("me", content, date);
		/*
		 * addStatus(status) <- Ajoute le status à la liste des status.
		 */
		sendStatus(status);
	}
	
	public static void sendStatus(Status status){
		try{
			for (int i = 0; i < Friends.friendList.size(); i++){
				Friends friend = (Friends) Friends.friendList.get(i);
				if (friend.statusFriends()==true){
					String friendAddress = friend.hostFriend();
					InetAddress address = InetAddress.getByName(friendAddress);
					Message.postStatus(status.getContent(), address);
				}
			}
		}catch (Exception e){}
	}
	
	public static void analyseStatusSend(String sendData){
		
		
//		Cherche comment.cOwner dans le fichier xml, et rajoute comment.cContent dedans		
//		Status.listStatus.add(comment);
//		try {
//			XmlTreatment.addCommentXML(comment);
//		} catch (Exception e) {}
	}
}
