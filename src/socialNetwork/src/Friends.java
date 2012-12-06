package socialNetwork.src;

import java.util.ArrayList;

import socialNetwork.src.Status;

public class Friends{

	private String name;
	private String host;
	private boolean status;
	public static ArrayList<Friends> friendList = new ArrayList<Friends>();

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

	public void setName(String name){
		this.name = name;
	}

	public void setHost(String host){
		this.host = host;
	}

	public String getName(){
		return this.name;
	}

	public String getHost(){
		return this.host;
	}

	public boolean getStatus(){	
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

	public String toString(){
		return "[" + this.name + "," + this.host +"]";
	}

	public static void AcceptFriend(String name){
		for(int i=0; i < friendList.size(); i++){
			if(friendList.get(i).getName().equals(name) && friendList.get(i).getStatus() == false){
				friendList.get(i).answerFriendRequest();
				break;
			}
		}
	}

	public static void analyseFriendsRequest(String request){
		Friends newFriend = new Friends();
		for(int i=0; i < request.length(); i++){
			char a = request.charAt(i);
			if (a ==':'){
				newFriend.setName(request.substring(0,i));
				newFriend.setHost(request.substring(i+1));
				break;
			}			
		}
		if(friendList.size() == 0)
			friendList.add(newFriend);
		else{
			for(int i=0; i < friendList.size(); i++){
				if(!friendList.get(i).getName().equals(newFriend.getName()))
					friendList.add(newFriend);
			}
		}

		//		try {
		//			XmlTreatment.addFriendXML(newFriends);
		//		} catch (Exception e) {}
	}

	public static void createFriendsList(){
		//friendList = XmlTreatment.getFriendsXML();
	}

	private static String contentFriendList(){
		String content = "";
		for(int i=0; i < friendList.size(); i ++)
			content = friendList.get(i).toString();
		return content;
	}

	private static String contentStatus(boolean type){
		String content= "";
		if(type)
			for(int i=0; i < Status.listStatus.size(); i ++)
				content = Status.listStatus.get(i).toString();
		else
			for(int i=0; i < Status.listStatus.size(); i ++){
				if(Status.listStatus.get(i).getType() == "public")
					content = Status.listStatus.get(i).toString();
			}
		return content;
	}

	private void answerFriendRequest(){
		this.setToFriend();
		//Change in xml
		String dataToSend;
		if(this.getStatus())
			dataToSend = this.getName() + contentFriendList() + contentStatus(true);
		else
			dataToSend = this.getName() + contentFriendList() + contentStatus(false);
		Message.friendsAnswer(this.getHost(), dataToSend, this.getStatus());
	}
}
