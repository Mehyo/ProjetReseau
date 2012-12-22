package socialNetwork.src;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;

import socialNetwork.Main;
import socialNetwork.src.Message;

/**
 *Traite l'ajout et la création de statuts.
 */
public class Status {

	private String sContent;
	private String sOwner;
	private String sDate;
	private String sType;
	private ArrayList<Commentary> listComment = new ArrayList<Commentary>();
	public static ArrayList<Status> listStatus = new ArrayList<Status>();

	/**
	 * Crée un nouveau statuts à partir des champs spécifié.
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
	 * Crée un nouveau statuts public sans commentaire.
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
	 * Crée un nouveau statuts public vide.
	 */
	public Status(){
		this.sContent = "";
		this.sOwner = "";
		this.sDate = "";
		this.sType = "public";
	}


	/**
	 * Défini le propriétaire d'un statuts.
	 * @param owner Nom du propriétaire.
	 */
	public void setOwner(String owner){
		this.sOwner = owner;
	}

	/**
	 * Défini le contenu d'un statuts.
	 * @param content Contenu du statuts.
	 */
	public void setContent(String content){
		this.sContent = content;
	}

	/**
	 * Défini la date d'un statuts.
	 * @param date Date du statuts.
	 */
	public void setDate(String date){
		this.sDate = date;
	}

	/**
	 * Défini le type d'un statuts.
	 * @param type Type du statuts (Public/Privé)
	 */
	public void setType(String type){
		this.sType = type;
	}

	/**
	 * Défini un commentaire pour le statuts.
	 * @param owner Nom du propriétaire du commentaire.
	 * @param content Contenu du commentaire.
	 */
	public void setCommentary(String owner, String content){
		this.listComment.add(new Commentary(owner, content, this.getOwner(), this.getDate()));
	}

	/**
	 * Retourne le nom du propriétaire du statuts.
	 * @return Le nom du propriétaire.
	 */
	public String getOwner(){
		return this.sOwner;
	}

	/**
	 * Retourne le contenu du statuts.
	 * @return Contenu du statuts.
	 */
	public String getContent(){
		return this.sContent;
	}

	/**
	 * Retourne la date du statuts.
	 * @return Date du statuts
	 */
	public String getDate(){
		return this.sDate;
	}

	/**
	 * Retourne le type du statuts (Public ou privé).
	 * @return Type du statuts.
	 */
	public String getType(){
		return this.sType;
	}

	/**
	 * Retourne la liste des commentaires associé au statuts.
	 * @return Liste des commentaires.
	 */
	public ArrayList<Commentary> getCommentary(){
		return this.listComment;
	}

	/**
	 * Retourne la chaine de caractère contenant toute les informations sur le statuts.
	 * @return La chaine de caractère.
	 */
	public String toString(){
		String status = "{"+ this.getDate()+ "_§§_" + this.getContent() + "_&&_";
		for(int i=0; i < listComment.size(); i ++)
			status +=listComment.get(i).commentToString() + "_&&_";
		return status + "}" ;
	}

	/**
	 * Crée et envoi un nouveau statuts à tout les amis.
	 * @param content Contenu du status.
	 * @param publicstatus Le type de status.
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

	/**
	 * Envoi tout les statuts contenu dans la liste des statuts.
	 * @param address Adresse du destinataire.
	 * @param type Etat (Amis/Etranger)
	 */
	public static void sendAllStatus(InetAddress address, int type){
		Status status = new Status();
		for(int i = 0; i < listStatus.size(); i++){
			status = listStatus.get(i);
			if(status.getType().equals("public"))
				Message.postStatus(Main.userName + "##"+ status.getDate() + "##" + status.getContent(), address);
			else
				if (type > 0)
					Message.postStatus(Main.userName + "##"+ status.getDate() + "##" + status.getContent(), address);
		}
	}

	/**
	 * Ajoute un status à la sauvegarde XML.
	 * @param status Le status.
	 */
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
					Message.postStatus(Main.userName + "##"+ date + "##" + status.getContent(), InetAddress.getByName(friend.getHost()));
				}
				else{
					if (friend.getStatus()==true){
						Message.postStatus(Main.userName + "##"+ date + "##" + status.getContent(), InetAddress.getByName(friend.getHost()));
					}
				}
			}
		}catch (Exception e){}
	}

	/**
	 * Génére la liste des statuts depuis le fichier de stockage. 
	 */
	public static void createListeStatus(){
		XmlTreatment.getStatusXML();
	}

	/**
	 * Affiche un nouveau status dans l'interface.
	 * @param newStatus Le nouveau statuts.
	 */
	public static void printStatus(Hashtable<String, String> dataTable){
		Serveur.ex.himStatus("[" + dataTable.get("Date")+"]"+ dataTable.get("Name") + ">" + dataTable.get("Status"));
	}
}
