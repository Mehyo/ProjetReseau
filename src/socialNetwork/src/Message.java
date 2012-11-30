package socialNetwork.src;

import java.net.InetAddress;

import socialNetwork.Main;

public class Message {
	
	public static void sendStatus (String status){
		try{
			for (int i = 0; i < Main.friendList.length; i++){
				Friends friend = Main.friendList[i];
				if (friend.isMyFriend()==true){
					String friendAddress = friend.hostFriend();
					InetAddress address = InetAddress.getByName(friendAddress);
					postStatus(status, address);
				}
			}
		}catch (Exception e){}
	}
	
	public static void postStatus(String status, InetAddress address){
		String data = "10" + status;
		Serveur.createSocket(address,data);
	}
	
	public static void postComment(String comment, InetAddress address){
		String data = "11" + comment;
		Serveur.createSocket(address,data);
	}	
	
	private static void friendsRequest(InetAddress address){
		try{
		String data = "20" + System.getProperty("user.name") + '|' + InetAddress.getLocalHost().toString();
		Serveur.createSocket(address, data);
		}catch(Exception e){}
	}

	private static void friendsPositivAnswer(InetAddress address){
		String data = "21" + System.getProperty("user.name") /* + donnée à envoyer */ ;
		Serveur.createSocket(address, data);
	}
	
	private static void friendsNegativAnswer(InetAddress address){
		String data = "22" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
	}

	private static void friendsListRequest(InetAddress address){
		String data = "23" + System.getProperty("user.name");
		Serveur.createSocket(address, data);
	}
	
	private static void friendsListGive(InetAddress address){
		String data = "24" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
	}

	private static void friendsStatusRequest(InetAddress address){
		String data = "30" + System.getProperty("user.name");
		Serveur.createSocket(address, data);
	}

	private static void friendsStatusList(InetAddress address){
		String data = "31" + System.getProperty("user.name") /* + donnée */;
		Serveur.createSocket(address, data);
	}

	private static void friendsCommentary(InetAddress address){
		Serveur.createSocket(address, "40");
	}

	private static void friendsImage(InetAddress address){
		Serveur.createSocket(address, "50");
	}

	public static void printStatus(String newStatus){
		Serveur.ex.himStatus(newStatus);
	}

}