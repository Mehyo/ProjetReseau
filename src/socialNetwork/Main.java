package socialNetwork;

import java.net.InetAddress;

import socialNetwork.src.Serveur;
import socialNetwork.src.Friends;
import socialNetwork.src.Status;

public class Main {

	public static String userName;
		
	public static void main(String[] args) throws Exception{
		Friends.createFriendsList();
		Status.createListeStatus();
		Serveur serv = new Serveur();
		serv.run();
	}
}
