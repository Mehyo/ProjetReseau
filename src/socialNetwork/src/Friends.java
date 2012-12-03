package socialNetwork.src;

import java.util.ArrayList;

import socialNetwork.Main;

public class Friends{

	private String name;
	private String host;
	private boolean status;
	public static ArrayList<Friends> friendList;

	public Friends(String name, String host, String status){
		this.name = name;
		this.host = host;
		if(status.compareTo("false") == 0)
			this.status = false;
		else
			this.status = true;
	}

	public Friends(){
		this.name = "";
		this.host = "";
		this.status = false;
	}

	public static void AcceptFriend(String name){	
		/**/
	}

	public void setName(String name){
		this.name = name;
	}

	public void setHost(String host){
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

	public boolean statusFriends(){	
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

	public static void analyseFriendsRequest(String request){
		Friends newFriends = new Friends();
		for(int i=0; i < request.length(); i++){
			char a = request.charAt(i);
			if (a ==':'){
				newFriends.setName(request.substring(0,i-1));
				newFriends.setHost(request.substring(i+1));
				break;
			}			
		}
		friendList.add(newFriends);
		try {
			XmlTreatment.addFriendXML(newFriends);
		} catch (Exception e) {}
	}
	
	public static void createFriendsList(){
		friendList = XmlTreatment.getFriendsXML();
	}
}
