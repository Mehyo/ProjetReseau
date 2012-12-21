package socialNetwork;

import java.net.InetAddress;

import socialNetwork.src.Commentary;
import socialNetwork.src.Message;
import socialNetwork.src.Serveur;
import socialNetwork.src.Friends;
import socialNetwork.src.Status;
import socialNetwork.src.XmlTreatment;

public class Main {

	public static String userName;
		
	public static void main(String[] args) throws Exception{
		XmlTreatment.setUserName();
		Friends.createFriendsList();
		Status.createListeStatus();
		Serveur.connect();
		Serveur serv = new Serveur();
		serv.run();
	}
}
