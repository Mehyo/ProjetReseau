package socialNetwork.src;

import java.net.InetAddress;

import socialNetwork.Main;

/**
 * Rassemble les différentes fonctions d'envoi de données.
 */
public class Message extends Protocole{

	/**
	 * Envoi un message de connexion à l'adresse spécifié.
	 * @param address L'adresse d'envoi.
	 */
	public static void connect(InetAddress address){
		try {
			String data = Main.userName+"##"+InetAddress.getLocalHost().toString();
			send("00", data,address);
		} catch (Exception e) {}
	}

	/**
	 * Envoi un status à l'adresse spécifié.
	 * @param status Le status.
	 * @param address L'adresse.
	 */
	public static void postStatus(String status, InetAddress address){
		send("10", status, address);
	}

	/**
	 * Envoi un status à l'adresse spécifié.
	 * @param status Le status.
	 * @param address L'adresse.
	 */
	public static void postComment(String comment, InetAddress address){
		send("11", comment, address);
	}	

	/**
	 * Envoi une demande d'ami à l'adresse spécifié.
	 * @param data La demande.
	 * @param address L'adresse.
	 */
	public static void friendsRequest(String data, InetAddress address){
		send("20", data, address);
	}

	/**
	 * Envoi une réponse à une requête d'amis à l'adresse spécifié.
	 * @param address L'adresse.
	 * @param dataToSend La réponse.
	 * @param friends Le type de réponse.
	 */
	public static void friendsAnswer(InetAddress address, String dataToSend, boolean friends){
		if(friends)
			send("21",  dataToSend, address);
		else
			send("22",  dataToSend, address);
	}

	/**
	 * Envoi une image de status à l'adresse spécifié.
	 * @param data L'image.
	 * @param address L'adresse.
	 */
	public static void friendsStatusImage(String data, InetAddress address){
		send("40",  data, address);
	}

	/**
	 * Envoi une image de profile à l'adresse spécifié.
	 * @param data L'image.
	 * @param address L'adresse.
	 */
	public static void friendsProfileImage(String data, InetAddress address){
		send("41",  data, address);
	}
}
