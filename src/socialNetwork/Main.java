package socialNetwork;

import java.net.InetAddress;
import java.util.ArrayList;

import socialNetwork.src.Serveur;
import socialNetwork.src.Friends;

public class Main {
	
	public static ArrayList<Friends> friendList;
	public static InetAddress[] address;
	public static int nb;
		
	public static void main(String[] args) throws Exception{
		friendList = new ArrayList<Friends>(Friends.getFriends());
		Serveur serv = new Serveur();
		serv.run();
	}
}
