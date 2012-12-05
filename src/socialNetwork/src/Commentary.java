package socialNetwork.src;

import java.net.InetAddress;

import socialNetwork.Main;
import socialNetwork.ui.Interface;

public class Commentary {

	private String cOwner;
	private String cContent;
	private String sOwner;
	private String sDate;
	
	public Commentary(String c_Owner, String c_Content, String s_Owner, String s_Date){
		this.cContent = c_Content;
		this.cOwner = c_Owner;
		this.sOwner = s_Owner;
		this.sDate = s_Date;
	}

	public Commentary(String c_Owner, String c_Content){
		this(c_Owner, c_Content, "", "");
	}
		
	public Commentary(){
		this("", "", "", "");
	}

	public void setcOwner(String cOwner) {
		this.cOwner = cOwner;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public void setsOwner(String sOwner) {
		this.sOwner = sOwner;
	}

	public void setsDate(String sDate) {
		this.sDate= sDate;
	}

	public String getcOwner(){
		return this.cOwner;
	}

	public String getcContent(){
		return this.cContent;
	}

	public String getsOwner(){
		return this.sOwner;
	}

	public String getsDate(){
		return this.sDate;
	}
	
	public String toString(){
		return this.getsOwner()+":"+this.getsDate()+":"+this.getcOwner()+":"+this.getcContent(); 
	}
	
	public String commentToString(){
		return this.getcOwner()+":"+this.getcContent();
	}

	private static Commentary analyseString(Commentary comment, String dataSend){
		for(int i=0; i < dataSend.length(); i++){
			char c = dataSend.charAt(i);
			if (c ==':'){
				if(comment.getsOwner() != ""){
					if(comment.getsDate() != ""){
						if(comment.getcOwner() != ""){
							comment.setcContent(dataSend.substring(0,i-1));
							break;
						}
						else{
							comment.setcOwner(dataSend.substring(0,i-1));
							analyseString(comment, dataSend.substring(i+1));
						}
					}
					else{
						comment.setsDate(dataSend.substring(0,i-1));
						analyseString(comment, dataSend.substring(i+1));
					}
				}
				else{
					comment.setsOwner(dataSend.substring(0,i-1));
					analyseString(comment, dataSend.substring(i+1));
				}
			}
		}
		return comment;
	}


	public static void analyseCommentary(String dataSend){
		Commentary comment = new Commentary();
		comment = analyseString(comment, dataSend);
		comment.addToStatus();
	}

	private void addToStatus(){
		if(this.getsOwner() == Main.userName)
			XmlTreatment.addCommentary(this.getsDate(), this.getcOwner(), this.getcContent());
		Interface.printCommentary(this);	
	}


	public static void sendComment(Status status, String comment){
		Commentary commentary = new Commentary(Main.userName, comment, status.getOwner(), status.getDate());
		String dataSend = commentary.toString();
		try {
			Message.postComment(dataSend, InetAddress.getByName(status.getOwner()));
		} catch (Exception e) {}
		
	}
}
