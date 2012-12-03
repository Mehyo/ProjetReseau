package socialNetwork.src;

import socialNetwork.Main;

public class Commentary {

	private String cOwner;
	private String cContent;
	private String sOwner;
	private String sDate;

	public Commentary(){
		this.cContent = "";
		this.cOwner = "";
		this.sOwner = "";
		this.sDate = "";
	}

	public Commentary(String owner, String content){
		this.cContent = content;
		this.cOwner = owner;
		this.sOwner = "";
		this.sDate = "";
	}

	public Commentary(String c_Owner, String c_Content, String s_Owner, String s_Date){
		this.cContent = c_Content;
		this.cOwner = c_Owner;
		this.sOwner = s_Owner;
		this.sDate = s_Date;
	}

	public void setcOwner(String cOwner) {
		this.cOwner = cOwner;
	}

	public void setcContent(String cContent) {
		this.cContent = cContent;
	}

	public void setsOwner(String sOwner) {
		this.sOwner = sOwner;
	}

	public void setsDate(String sDate) {
		this.sDate= sDate;
	}

	public String getcOwner(){
		return this.cOwner;
	}

	public String getcContent(){
		return this.cContent;
	}

	public String getsOwner(){
		return this.sOwner;
	}

	public String getsDate(){
		return this.sDate;
	}

	private static Commentary analyseString(Commentary comment, String dataSend){
		for(int i=0; i < dataSend.length(); i++){
			char c = dataSend.charAt(i);
			if (c ==':'){
				if(comment.getsOwner() != ""){
					if(comment.getsDate() != ""){
						if(comment.getcOwner() != ""){
							comment.setcContent(dataSend.substring(0,i-1));
							break;
						}
						else{
							comment.setcOwner(dataSend.substring(0,i-1));
							analyseString(comment, dataSend.substring(i+1));
						}
					}
					else{
						comment.setsDate(dataSend.substring(0,i-1));
						analyseString(comment, dataSend.substring(i+1));
					}
				}
				else{
					comment.setsOwner(dataSend.substring(0,i-1));
					analyseString(comment, dataSend.substring(i+1));
				}
			}
		}
		return comment;
	}


	public static Commentary analyseCommentary(String dataSend){
		Commentary comment = new Commentary();
		comment = analyseString(comment, dataSend);
		return comment;
	}

	public static void receiveComment(String comment){
		/**/
	}


	public static void sendComment(Status status){
		/* */
	}

	public static void postComment(){
		
	}

}
