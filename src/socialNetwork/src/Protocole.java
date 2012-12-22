package socialNetwork.src;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

/**
 * Traite l'ensemble des données reçues.
 */
public class Protocole {

	protected Protocole(){}

	/**
	 * Traite les données reçu suivant reqcode et appelle la bonne fonction de traitement en suivant.
	 * @param reqcode Le type de données.
	 * @param dataReceived Les données reçus.
	 */
	public static void treatmentProtocol(int reqcode, String dataReceived){
		for(;;){
			if(connect(reqcode, dataReceived)) break;

			if(statusReceived(reqcode, dataReceived)) break;

			if(commentReceived(reqcode, dataReceived)) break;

			if(friendRequest(reqcode, dataReceived)) break;

			if(friendYesAnswer(reqcode, dataReceived)) break;

			if(friendNoAnswer(reqcode, dataReceived)) break;

			if(imageStatus(reqcode, dataReceived)) break;

			if(imageProfile(reqcode, dataReceived)) break;

			if(errorMessage(reqcode, dataReceived)) break;

			sendErrorMessage("42 Poyo");
			break;
		}
	}

	private static boolean connect(int reqcode, String dataReceived){
		if(reqcode != 00)
			return false;
		Hashtable<String, String> dataTable = Splitter.connect(dataReceived);
		Serveur.connect(dataTable.get("Name"), dataTable.get("Host"));
		return true;
	}

	private static boolean statusReceived(int reqcode, String dataReceived){
		if (reqcode != 10)
			return false;
		Hashtable<String, String> dataTable = Splitter.status(dataReceived);
		Status.printStatus(dataTable);
		return true;
	}

	private static boolean commentReceived(int reqcode, String dataReceived){
		if (reqcode != 11)
			return false;
		Hashtable<String, String> dataTable = Splitter.comment(dataReceived);
		Commentary.add(dataTable);
		return true;
	}

	private static boolean friendRequest(int reqcode, String dataReceived){
		if (reqcode != 20)
			return false;
		Hashtable<String, String> dataTable = Splitter.friendRequest(dataReceived);
		Friends.analyseFriendsRequest(dataTable);
		return true;
	}

	private static boolean friendYesAnswer(int reqcode, String dataReceived){
		if (reqcode != 21)
			return false;
		Hashtable<String, String> dataTable = Splitter.friendResponse(dataReceived);
		Friends.analyseFriendAnswer(dataTable.get("Name"), 1);
		return true;
	}

	private static boolean friendNoAnswer(int reqcode, String dataReceived){
		if (reqcode != 22)
			return false;
		Hashtable<String, String> dataTable = Splitter.friendResponse(dataReceived);
		Friends.analyseFriendAnswer(dataTable.get("Name"), 0);
		return true;
	}

	private static boolean imageStatus(int reqcode, String dataReceived){
		if (reqcode != 40)
			return false;
		Hashtable<String, String> dataTable = Splitter.image(dataReceived);
		//A faire
		return true;
	}

	private static boolean imageProfile(int reqcode, String dataReceived){
		if (reqcode != 41)
			return false;
		Hashtable<String, String> dataTable = Splitter.image(dataReceived);
		//A faire
		return true;
	}

	private static boolean errorMessage(int reqcode, String dataReceived){
		if (reqcode != 99)
			return false;
		Hashtable<String, String> dataTable = Splitter.errorMessage(dataReceived);
		//a faire
		return true;
	}

	private static void sendErrorMessage(String error){
		send("99", error, null);
	}

	protected static void send(String reqcode, String req, InetAddress address){
		int ok=0;
		System.out.println(Serveur.connectList.toString());
		for(int i = 0; i < Serveur.connectList.size();i++){
			if(Serveur.connectList.get(i).equals(address)){
				ok = 1;
				break;
			}
		}
		System.out.println(ok);
		if(ok == 0){
			return;
		}
		try{
			sendData(reqcode, req, address);
		}catch (Exception e){ 
			Serveur.connectList.remove(address);
			}
	}

	private static void sendData(String reqcode, String req, InetAddress address) //Fonction qui va envoyer [reqcode]+[req] à l'adresse address
	{
		System.out.println(reqcode + req + address.toString());
		try{
			Socket s = new Socket(address, Serveur.port);
			OutputStream os = s.getOutputStream();
			PrintStream ps = new PrintStream(os, false, "utf-8");
			ps.println(reqcode+req);
			ps.flush();
			ps.close();
			s.close();
		}catch (Exception e){System.out.println("caca");Serveur.connectList.remove(address);}

	}
}