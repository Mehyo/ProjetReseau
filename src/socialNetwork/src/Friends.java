package socialNetwork.src;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import socialNetwork.Main;
import socialNetwork.src.Status;

/**
 *Traite l'ajout et la création d'amis.
 */
public class Friends{

	private String name;
	private String host;
	private boolean status;

	public static ArrayList<Friends> friendList = new ArrayList<Friends>();

	/**
	 * Génère un nouvel ami défini par son nom, son addresse et son statut (Accepté ou non)
	 * @param name Le nom de l'ami.
	 * @param host Son addresse.
	 * @param status Son état (Accepté ou non)
	 */
	public Friends(String name, String host, String status){
		this.name = name;
		this.host = host;
		if(status.compareTo("false") == 0)
			this.status = false;
		else
			this.status = true;
	}

	/**
	 * Génère un nouvel ami vide.
	 */
	public Friends(){
		this.name = "";
		this.host = "";
		this.status = false;
	}

	/**
	 * Défini le nom d'un ami.
	 * @param name Le nom de l'ami.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Défini l'addresse d'un ami.
	 * @param host L'addresse de l'ami.
	 */
	public void setHost(String host){
		this.host = host;
	}

	/**
	 * Retourne le nom d'un ami.
	 * @return Le nom de l'ami.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Retourne l'addresse d'un ami.
	 * @return L'addresse de l'ami.
	 */
	public String getHost(){
		return this.host;
	}

	/**
	 * Retourne le status de l'ami.
	 * @return Le status de l'ami.
	 */
	public boolean getStatus(){	
		return this.status;
	}

	/**
	 * Défini l'ami commme accepté.
	 */
	public void setToFriend(){
		this.status = true;
		//change dans xml
	}

	/**
	 * Défini l'ami comme non accepté.
	 */
	public void setToStranger(){
		this.status = false;
		//change dans xml
	}

	/**
	 * Retourne  Une chaine de caractère contenant le nom et l'addresse d'un ami.
	 * @return La chaine de caractère.
	 */
	public String toString(){
		return "##" + this.name + "##" + this.host;
	}

	/**
	 * Rajoute l'ami à la liste des amis.
	 * @param name Le nom de l'ami.
	 */
	public static void AcceptFriend(String name){
		for(int i=0; i < friendList.size(); i++){
			if(friendList.get(i).getName().equals(name) && friendList.get(i).getStatus() == false){
				friendList.get(i).setToFriend();
				friendList.get(i).informFriend(true);
				break;
			}
		}
	}

	public void informFriend(boolean state){
		this.answerFriendRequest(state);
	}
	
	
	/**
	 * Crée et envoi les données d'une requête amis.
	 * @param address L'adresse du destinataire
	 */
	public static void sendRequest(InetAddress address){
		String data = null;
		try {
			data = Main.userName +"##"+InetAddress.getLocalHost();
		}catch (UnknownHostException e) {}
		
		for(int i = 0; i < friendList.size(); i++){
			data += "##"+friendList.get(i).getName() + "##" + friendList.get(i).getHost();
		}
		Message.friendsRequest(data, address);
	}
	
	/**
	 * Analyse une demande d'ami reçu.
	 * @param dataTable La requête.
	 */
	public static void analyseFriendsRequest(Hashtable<String, String> dataTable){
		Friends newFriend = new Friends(dataTable.get("NewFriendName"), dataTable.get("NewFriendHost"), "false");		
		if(friendList.size() == 0)
			friendList.add(newFriend);
		else{
			for(int i=0; i < friendList.size(); i++){
				if(!friendList.get(i).getName().equals(newFriend.getName()))
					friendList.add(newFriend);
			}
		}
		for(int i = 2; i <dataTable.size();i++)
			for(int j = 0; j < friendList.size(); i++)
				if(!dataTable.get("Friend"+i+"Name").equals(friendList.get(j))){
					newFriend.setName(dataTable.get("Friend"+i+"Name"));
					newFriend.setHost(dataTable.get("Friend"+i+"Host"));
					friendList.add(newFriend);
				}
		try {
			XmlTreatment.addFriendXML(newFriend);
		} catch (Exception e) {}
	}

	/**
	 * Analyse la réponse à une requête d'ami.
	 * @param FriendName Le nom de l'ami répondant.
	 * @param answer Oui ou non.
	 */
	public static void analyseFriendAnswer(String FriendName, int answer){
		Friends friend = new Friends();
		for(int i = 0; i < friendList.size(); i++)
			if(friendList.get(i).equals(FriendName))
				friend = friendList.get(i);
		if(answer == 1)
			friend.setToFriend();
		else
			friend.setToStranger();
	}


	/**
	 * Génére la liste d'ami à partir d'un fichier de configuration.
	 */
	public static void createFriendsList(){
		XmlTreatment.getFriendsXML();
	}
	
	/**
	 * Trouve un ami via son nom dans la liste des amis.
	 * @param name Le nom de l'ami.
	 * @return L'ami ou null si rien n'est trouvé.
	 */
	public static Friends findFriend(String name){
		for(int i =0; i < friendList.size();i++){
			if(friendList.get(i).getName().equals((String) name))
				return friendList.get(i);
		}
		return null;
	}

	private static String contentFriendList(){
		String content = "";
		for(int i=0; i < friendList.size(); i ++)
			content += friendList.get(i).toString()+"##";
		return content;
	}

	private void answerFriendRequest(boolean state){
		try{
			String dataToSend;
			dataToSend = Main.userName + contentFriendList();
			Message.friendsAnswer(InetAddress.getByName(this.getHost()), dataToSend, this.getStatus());
			if(state)
				Status.sendAllStatus(InetAddress.getByName(this.getHost()), 1);
			else
				Status.sendAllStatus(InetAddress.getByName(this.getHost()), 0);
		}catch(Exception e){ e.toString(); }
	}

}
