package socialNetwork.src;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import socialNetwork.Main;
import socialNetwork.src.Message;

public class Status {

	private String sContent;
	private String sOwner;
	private String sDate;
	private String sType;
	private ArrayList<Commentary> listComment = new ArrayList<Commentary>();
	public static ArrayList<Status> listStatus = new ArrayList<Status>();
	
	/**
	 * Crée un nouveau status à partir des champs spécifié.
	 * @param owner Propriétaire.
	 * @param content Contenu.
	 * @param date Date.
	 * @param type Public ou privé.
	 * @param comment Commentaire associé.
	 */
	public Status(String owner, String content, String date, String type, Commentary comment){
		this.sContent = content;
		this.sOwner = owner;
		this.sDate = date;
		this.sType = type;
		this.listComment.add(comment);
	}
	
	/**
	 * Crée un nouveau status public sans commentaire.
	 * @param owner Propriétaire.
	 * @param content Contenu.
	 * @param date Date.
	 */
	public Status(String owner, String content, String date){
		this.sContent = content;
		this.sOwner = owner;
		this.sDate = date;
		this.sType = "public";
	}
	
	/**
	 * Crée un nouveau status public vide.
	 */
	public Status(){
		this.sContent = "";
		this.sOwner = "";
		this.sDate = "";
		this.sType = "public";
	}
	
	
	/**
	 * Défini le propriétaire d'un status.
	 * @param owner Nom du propriétaire.
	 */
	public void setOwner(String owner){
		this.sOwner = owner;
	}
	
	/**
	 * Défini le contenu d'un status.
	 * @param content Contenu du status.
	 */
	public void setContent(String content){
		this.sContent = content;
	}
	
	/**
	 * Défini la date d'un status.
	 * @param date Date du status.
	 */
	public void setDate(String date){
		this.sDate = date;
	}
	
	/**
	 * Défini le type d'un status.
	 * @param type Type du status (Public/Privé)
	 */
	public void setType(String type){
		this.sType = type;
	}
		
	/**
	 * Défini un commentaire pour le status.
	 * @param owner Nom du propriétaire du commentaire.
	 * @param content Contenu du commentaire.
	 */
	public void setCommentary(String owner, String content){
		this.listComment.add(new Commentary(owner, content, this.getOwner(), this.getDate()));
	}
	
	/**
	 * @return Nom du propriétaire du status.
	 */
	public String getOwner(){
		return this.sOwner;
	}
	
	/**
	 * @return Contenu du status.
	 */
	public String getContent(){
		return this.sContent;
	}
	
	/**
	 * @return Date du status
	 */
	public String getDate(){
		return this.sDate;
	}
	
	/**
	 * @return Type du status.
	 */
	public String getType(){
		return this.sType;
	}
	
	/**
	 * @return Liste des commentaires.
	 */
	public ArrayList<Commentary> getCommentary(){
		return this.listComment;
	}
	
	/**
	 * @return Chaine de caractère contenant toute les informations sur le status.
	 */
	public String toString(){
		String status = "{"+ this.getDate()+ "_§§_" + this.getContent() + "_&&_";
		for(int i=0; i < listComment.size(); i ++)
			status +=listComment.get(i).commentToString() + "_&&_";
		return status + "}" ;
	}

	/**
	 * Crée et envoi un nouveau status à tout les amis.
	 * @param content
	 * @param publicStatus
	 */
	public static void createNewStatus(String content, boolean publicStatus){
		DateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy][HH:mm:ss]");
		Date date = new Date();
		
		String sDate = date.toString();
		Status status = new Status("me", content, sDate);
				
		addStatus(status);
		if(publicStatus)
			sendStatus(status, sDate, true);
		else
			sendStatus(status, sDate, false);
	}
	
	public static void add(Status status){
		addStatus(status);
	}
	private static void addStatus(Status status){
		listStatus.add(status);
		XmlTreatment.addStatusXML(status);
	}
	
	private static void sendStatus(Status status, String date, boolean publicStatus){
		try{
			for (int i = 0; i < Friends.friendList.size(); i++){
				Friends friend = Friends.friendList.get(i);
				if(publicStatus){
					Message.postStatus(Main.userName + "_&§&_"+ date + "_&§&_" + status.getContent(), InetAddress.getByName(friend.getHost()));
				}
				else{
					if (friend.getStatus()==true){
						String friendAddress = friend.getHost();
						Message.postStatus(status.getContent(), InetAddress.getByName(friendAddress));
					}
				}
			}
		}catch (Exception e){}
	}
	
	/**
	 * Génére la liste des status depuis le fichier de stockage. 
	 */
	public static void createListeStatus(){
		/*Xml import */
	}
	
	/**
	 * Affiche un nouveau status dans l'interface.
	 * @param newStatus Le nouveau status.
	 */
	public static void printStatus(String newStatus){
		StringTokenizer st = new StringTokenizer(newStatus, "_&§&_");
		String tmp = st.nextToken();
		Serveur.ex.himStatus("[" + st.nextToken()+"]"+ tmp + ">" +st.nextToken());
	}
}
