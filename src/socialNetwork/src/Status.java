package socialNetwork.src;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import socialNetwork.src.Message;

public class Status {

	private String sContent;
	private String sOwner;
	private String sDate;
	private String sType;
	private ArrayList<Commentary> listComment = new ArrayList<Commentary>();
	public static ArrayList<Status> listStatus = new ArrayList<Status>();
	
	public Status(String owner, String content, String date, String type, Commentary comment){
		this.sContent = content;
		this.sOwner = owner;
		this.sDate = date;
		this.sType = type;
		this.listComment.add(comment);
	}
	
	public Status(){
		this.sContent = "";
		this.sOwner = "";
		this.sDate = "";
		this.sType = "public";
	}
	
	public Status(String owner, String content, String date){
		this.sContent = content;
		this.sOwner = owner;
		this.sDate = date;
		this.sType = "public";
	}
	
	public void setOwner(String owner){
		this.sOwner = owner;
	}
	
	public void setContent(String content){
		this.sContent = content;
	}
	
	public void setDate(String date){
		this.sDate = date;
	}
	
	public void setType(String type){
		this.sType = type;
	}
		
	public void setCommentary(String owner, String content){
		this.listComment.add(new Commentary(owner, content, this.getOwner(), this.getDate()));
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
	
	public String getType(){
		return this.sType;
	}
	
	public ArrayList<Commentary> getCommentary(){
		return this.listComment;
	}
	
	public String toString(){
		String status = "["+ this.getContent();
		for(int i=0; i < listComment.size(); i ++)
			status +="&§" + listComment.get(i).commentToString();
		return status + "]";
	}

	public static void createNewStatus(String content, boolean publicStatus){
		DateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy][HH:mm:ss]");
		Date date = new Date();
		
		String sDate = date.toString();
		Status status = new Status("me", content, sDate);
		System.out.println("Friendlist size :"+Friends.friendList.size());
		
		addStatus(status);
		if(publicStatus)
			sendStatus(status, true);
		else
			sendStatus(status, false);
	}
	
	private static void addStatus(Status status){
		listStatus.add(status);
		/*add to xml*/
	}
	
	private static void sendStatus(Status status, boolean publicStatus){
		try{
			for (int i = 0; i < Friends.friendList.size(); i++){
				Friends friend = Friends.friendList.get(i);
				if(publicStatus){
					String friendAddress = friend.getHost();
					Message.postStatus(status.getContent(), InetAddress.getByName("localhost"));
				}
				else{
					if (friend.getStatus()==true){
						String friendAddress = friend.getHost();
						Message.postStatus(status.getContent(), InetAddress.getByName(friendAddress));
					}
				}
			}
		}catch (Exception e){}
	}
	
	public static void createListeStatus(){
		/*Xml import */
	}
	
	public static void printStatus(String newStatus){
		Serveur.ex.himStatus(newStatus);
	}
}
