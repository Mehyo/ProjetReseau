package socialNetwork;

import java.net.InetAddress;
import java.util.ArrayList;

import socialNetwork.src.Serveur;
import socialNetwork.src.Friends;
import socialNetwork.src.XmlTreatment;

public class Main {
	
	public static ArrayList<Friends> friendList;
	public static InetAddress[] address;
	public static int nb;
		
	public static void main(String[] args) throws Exception{
		friendList = new ArrayList<Friends>(XmlTreatment.getFriendsXML());
		Serveur serv = new Serveur();
		serv.run();
	}
}
