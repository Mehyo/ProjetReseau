package socialNetwork.src;

import java.net.InetAddress;

public class Message {
	
	public static void connect(InetAddress address){
		Serveur.createSocket(address, "00");
	}
	
	public static void postStatus(String status, InetAddress address){
		String data = "10" + status;
		Serveur.createSocket(address,data);
	}
		
	public static void postComment(String comment, InetAddress address){
		String data = "11" + comment;
		Serveur.createSocket(address,data);
	}	
	
	public static void friendsRequest(InetAddress address){
		try{
		String data = "20" + System.getProperty("user.name") + "_&§&_" + InetAddress.getLocalHost().getHostName();
		Serveur.createSocket(address, data);
		}catch(Exception e){}
	}

	public static void friendsAnswer(String host, String dataToSend, boolean friends){
		String data;
		if(friends)
			data = "21" + dataToSend;
		else
			data = "22" + dataToSend;
		try {
			Serveur.createSocket(InetAddress.getByName(host), data);
		} catch (Exception e) {}
	}

	public static void friendsStatusImage(InetAddress address){
		Serveur.createSocket(address, "40");
	}
	
	public static void friendsProfileImage(InetAddress address){
		Serveur.createSocket(address, "41");
	}
}
