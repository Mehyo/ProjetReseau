package socialNetwork.src;

import java.net.InetAddress;
import java.util.StringTokenizer;

import socialNetwork.Main;
import socialNetwork.ui.Interface;

public class Commentary {

	private String cOwner;
	private String cContent;
	private String sOwner;
	private String sDate;
	
		
	/**
	 * Crée un nouveau commentaire avec les champs spécifié.
	 * @param c_Owner Propriétaire du commentaire.
	 * @param c_Content Contenu du commentaire.
	 * @param s_Owner Propriétaire du status commenté.
	 * @param s_Date Date du status commenté.
	 */
	public Commentary(String c_Owner, String c_Content, String s_Owner, String s_Date){
		this.cContent = c_Content;
		this.cOwner = c_Owner;
		this.sOwner = s_Owner;
		this.sDate = s_Date;
	}

	/**
	 * Crée un nouveau commentaire avec les champs spécifié.
	 * @param c_Owner Propriétaire du commentaire.
	 * @param c_Content Contenu du commentaire.
	 */
	public Commentary(String c_Owner, String c_Content){
		this(c_Owner, c_Content, "", "");
	}
		
	
	/**
	 * Crée un nouveau commentaire vide.
	 */
	public Commentary(){
		this("", "", "", "");
	}

	
	/**
	 * Défini le propriétaire d'un commentaire.
	 * @param cOwner
	 */
	public void setcOwner(String cOwner) {
		this.cOwner = cOwner;
	}

	/**
	 * Défini le contenu d'un commentaire.
	 * @param cContent
	 */
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	/**
	 * Défini le propriétaire du status auquel se rapporte le commentaire.
	 * @param sOwner
	 */
	public void setsOwner(String sOwner) {
		this.sOwner = sOwner;
	}

	/**
	 * Défini la date du status auquel se rapporte le commentaire.
	 * @param sDate
	 */
	public void setsDate(String sDate) {
		this.sDate= sDate;
	}

	/**
	 * @return Le propriétaire du commentaire.
	 */
	public String getcOwner(){
		return this.cOwner;
	}

	/**
	 * @return Le propriétaire du commentaire.
	 */
	public String getcContent(){
		return this.cContent;
	}

	/**
	 * @return Le propriétaire du status auquel se rapporte le commentaire.
	 */
	public String getsOwner(){
		return this.sOwner;
	}

	/**
	 * @return La date du status auquel se rapporte le commentaire.
	 */
	public String getsDate(){
		return this.sDate;
	}
	
	/**
	 * @return Une chaine de caractère contenant propiétaire du status, la date du status, le propriétaire du commentaire, le contenu du commentaire.
	 */
	public String toString(){
		return this.getsOwner()+"&&"+this.getsDate()+"&&"+this.getcOwner()+"&&"+this.getcContent(); 
	}
	
	/**
	 * @return Une chaine de caractère contenant le nom du commenteur et le contenu du commentaire.
	 */
	public String commentToString(){
		return this.getcOwner()+":"+this.getcContent();
	}
	

	private void analyseString(String dataSend){
		StringTokenizer stringT = new StringTokenizer(dataSend, "_&§&_");
		this.setsOwner(stringT.nextToken());
		this.setsDate(stringT.nextToken());
		this.setcOwner(stringT.nextToken());
		this.setcContent(stringT.nextToken());
	}


	/**
	 * Analyse les commentaires reçu d'autre personnes.
	 * @param dataSend Les données reçues.
	 */
	public static void analyseCommentary(String dataSend){
		Commentary comment = new Commentary();
		comment.analyseString(dataSend);
		comment.addToStatus();
	}

	public static void add(Commentary comment){
		comment.addToStatus();
	}
	private void addToStatus(){
		if(this.getsOwner() == Main.userName)
			XmlTreatment.addCommentary(this.getsDate(), this.getcOwner(), this.getcContent());
		Interface.printCommentary(this);	
	}


	/**
	 * Envoi un commentaire personnel aux amis.
	 * @param status Le status commenté.
	 * @param comment Le commentaire.
	 */
	public static void sendComment(Status status, String comment){
		Commentary commentary = new Commentary(Main.userName, comment, status.getOwner(), status.getDate());
		String dataSend = commentary.toString();
		try {
			Message.postComment(dataSend, InetAddress.getByName(status.getOwner()));
		} catch (Exception e) {}
		
	}
}
