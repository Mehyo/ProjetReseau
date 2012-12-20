package socialNetwork.src;

import java.util.ArrayList;
import java.util.StringTokenizer;

import socialNetwork.src.Status;

public class Friends{

	private String name;
	private String host;
	private boolean status;
	private ArrayList<Friends> ListOfFriend;
	
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
		this.ListOfFriend = new ArrayList<Friends>();
	}

	/**
	 * Génère un nouvel ami vide.
	 */
	public Friends(){
		this.name = "";
		this.host = "";
		this.status = false;
		this.ListOfFriend = new ArrayList<Friends>();
	}

	/**
	 * Défini le nom d'un ami.
	 * @param name Le nom de l'ami.
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Défini l'addressed d'un ami.
	 * @param host L'addresse de l'ami.
	 */
	public void setHost(String host){
		this.host = host;
	}

	/**
	 * @return Le nom d'un ami.
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * @return L'addresse d'un ami.
	 */
	public String getHost(){
		return this.host;
	}

	/**
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
	 * @retrun Une chaine de caractère contenant le nom et l'addresse d'un ami.
	 */
	public String toString(){
		return "[" + this.name + "," + this.host +"]";
	}

	/**
	 * Rajoute l'ami à la liste des amis.
	 * @param name Le nom de l'ami.
	 */
	public static void AcceptFriend(String name){
		for(int i=0; i < friendList.size(); i++){
			if(friendList.get(i).getName().equals(name) && friendList.get(i).getStatus() == false){
				friendList.get(i).answerFriendRequest();
				break;
			}
		}
	}

	/**
	 * Analyse une demande d'ami reçu.
	 * @param request La requête.
	 */
	public static void analyseFriendsRequest(String request){
		Friends newFriend = new Friends();
		StringTokenizer stringT = new StringTokenizer(request, "_&§&_");		

		newFriend.setName(stringT.nextToken());
		newFriend.setHost(stringT.nextToken());
		newFriend.setToFriend();

		if(friendList.size() == 0)
			friendList.add(newFriend);
		else{
			for(int i=0; i < friendList.size(); i++){
				if(!friendList.get(i).getName().equals(newFriend.getName()))
					friendList.add(newFriend);
			}
		}

		//		try {
		//			XmlTreatment.addFriendXML(newFriends);
		//		} catch (Exception e) {}
	}

	/**
	 * Génére la liste d'ami à partir d'un fichier de configuration.
	 */
	public static void createFriendsList(){
		XmlTreatment.getFriendsXML();
	}

	private static String contentFriendList(){
		String content = "";
		for(int i=0; i < friendList.size(); i ++)
			content = friendList.get(i).toString();
		return content;
	}

	private static String contentStatus(boolean type){
		String content = null;
		if(type)
			for(int i=0; i < Status.listStatus.size(); i ++)
				content = Status.listStatus.get(i).toString();
		else
			for(int i=0; i < Status.listStatus.size(); i ++){
				if(Status.listStatus.get(i).getType() == "public")
					content = Status.listStatus.get(i).toString();
			}
		return content;
	}


	private void answerFriendRequest(){
		this.setToFriend();
		//Change in xml
		String dataToSend;
		if(this.getStatus())
			dataToSend = System.getProperty("user.name") + contentFriendList() + contentStatus(true);
		else
			dataToSend = System.getProperty("user.name") + contentFriendList() + contentStatus(false);
		Message.friendsAnswer(this.getHost(), dataToSend, this.getStatus());
	}

	private static Friends findFriend(String name){
		for(int i =0; i < friendList.size();i++){
			if(friendList.get(i).getName().equals((String) name))
				return friendList.get(i);
		}
		return null;
	}

	private static void getFriendData(String dataSend){
		StringTokenizer st1 = new StringTokenizer(dataSend, "_&§&_");
		Friends friend = findFriend(st1.nextToken());
		
		StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "_§§_");
		for(int j=0; j < st2.countTokens();j++){
			StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "_o/");
			friend.ListOfFriend.add(new Friends(st3.nextToken(), st3.nextToken(), "false"));
		}
		
		StringTokenizer st3 = new StringTokenizer(st1.nextToken(),"{");
		for(int i =0; i < st3.countTokens();i++){
			//affiche Status friend.getName() et affiche commentaire associé.
		}
	}
}
