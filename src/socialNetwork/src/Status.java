package socialNetwork.src;

import java.net.InetAddress;
import java.util.ArrayList;
import socialNetwork.src.Message;

public class Status {

	private String sContent;
	private String sOwner;
	private Commentary sComment;
	public static ArrayList<Status> listStatus;
	
	public Status(){
		this.sContent = "";
		this.sOwner = "";
		this.sComment = new Commentary();
	}
	
	public Status(String owner, String content){
		this.sContent = content;
		this.sOwner = owner;
		this.sComment = new Commentary();
	}
	
	public Status(String owner, String content, Commentary comment){
		this.sContent = content;
		this.sOwner = owner;
		this.sComment = comment;
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
	
	public void setCommentary(Commentary comment){
		this.sComment = comment;
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
		Status status = new Status("me", content);
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
