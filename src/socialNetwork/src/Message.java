package socialNetwork.src;

import java.net.InetAddress;

import socialNetwork.Main;

public class Message {
	
	public static void post(String status, InetAddress address){
		String data = "10" + status;
		Serveur.createSocket(address,data);
	}

	public static void postStatus (String status){
		try{
			for (int i = 0; i < Main.friendList.length; i++){
				Friends friend = Main.friendList[i];
				if (friend.isMyFriend()==true){
					String friendAddress = friend.hostFriend();
					InetAddress address = InetAddress.getByName(friendAddress);
					post(status, address);
				}
			}
		}catch (Exception e){}
	}
	
	private void friendsRequest(InetAddress address){
		try{
		String data = "20" + System.getProperty("user.name") + '|' + InetAddress.getLocalHost().toString();
		Serveur.createSocket(address, data);
		}catch(Exception e){}
	}

	private void friendsConfirm(InetAddress address){
		String data = "21" + System.getProperty("user.name");
		Serveur.createSocket(address, data);
	}

	private void friendsListRequest(InetAddress address){
		Serveur.createSocket(address, "22");
	}

	private void friendsStatusRequest(InetAddress address){
		Serveur.createSocket(address, "30");
	}

	private void friendsStatusList(InetAddress address){
		Serveur.createSocket(address, "31");
	}

	private void friendsCommentary(InetAddress address){
		Serveur.createSocket(address, "40");
	}

	private void friendsImage(InetAddress address){
		Serveur.createSocket(address, "50");
	}


	public static void printStatus(String newStatus){
		Serveur.ex.himStatus(newStatus);
	}

}
