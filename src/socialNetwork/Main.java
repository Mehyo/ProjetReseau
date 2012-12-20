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
	
		
		Status test = new Status("owner", "content", "date");
		Status.add(test);
		
		Commentary test2 = new Commentary("owner2","Je suis une carotte corompue");		
		Commentary.add(test2);
		
		Friends.createFriendsList();
		
		Status.createListeStatus();
		Serveur serv = new Serveur();
		serv.run();
	}
}
