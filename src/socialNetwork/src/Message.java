package socialNetwork.src;

import java.net.InetAddress;
import java.net.UnknownHostException;

import socialNetwork.Main;

public class Message {

	public static void connect(InetAddress address){
		try {
			String data = Main.userName+"##"+InetAddress.getLocalHost().toString();
			Protocole.send("00", data,address);
		} catch (Exception e) {}
	}

	public static void postStatus(String status, InetAddress address){
		Protocole.send("10", status, address);
	}

	public static void postComment(String comment, InetAddress address){
		Protocole.send("11", comment, address);
	}	

	public static void friendsRequest(String data, InetAddress address){
		Protocole.send("20", data, address);
	}

	public static void friendsAnswer(InetAddress address, String dataToSend, boolean friends){
		if(friends)
			Protocole.send("21",  dataToSend, address);
		else
			Protocole.send("22",  dataToSend, address);
	}

	public static void friendsStatusImage(String data, InetAddress address){
		Protocole.send("40",  data, address);
	}

	public static void friendsProfileImage(String data, InetAddress address){
		Protocole.send("41",  data, address);
	}
}
