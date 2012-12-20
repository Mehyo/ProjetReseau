package socialNetwork.src;

public abstract class Protocole {
	
	public void treatmentProtocol(int i, String dataSend){
		for(;;){
			if(connect(i, dataSend)) break;
			
			if(statusSend(i, dataSend)) break;
			
			if(commentSend(i, dataSend)) break;
			
			if(friendRequest(i, dataSend)) break;
			
			if(friendYesAnswer(i, dataSend)) break;
			
			if(friendNoAnswer(i, dataSend)) break;
			
			if(imageStatus(i, dataSend)) break;
			
			if(imageProfile(i, dataSend)) break;
			
			if(errorMessage(i, dataSend)) break;
			
			sendErrorMessage(i);
			break;
		}
	}
	
	private boolean connect(int i, String dataSend){
		if( i == 00){
			//function
			return true;
		}
		return false;
	}
	
	private boolean statusSend(int i, String dataSend){
		if (i == 10){
			//function
			return true;
		}
		return false;
	}
	private boolean commentSend(int i, String dataSend){
		if (i == 11){
			//function
			return true;
		}
		return false;
	}
	private boolean friendRequest(int i, String dataSend){
		if (i == 20){
			//function
			return true;
		}
		return false;
	}
	private boolean friendYesAnswer(int i, String dataSend){
		if (i == 21){
			//function
			return true;
		}
		return false;
	}
	private boolean friendNoAnswer(int i, String dataSend){
		if (i == 22){
			//function
			return true;
		}
		return false;
	}

	private boolean imageStatus(int i, String dataSend){
		if (i == 40){
			//function
			return true;
		}
		return false;
	}
	private boolean imageProfile(int i, String dataSend){
		if (i == 41){
			//function
			return true;
		}
		return false;
	}
	private boolean errorMessage(int i, String dataSend){
		if (i == 99){
			//function
			return true;
		}
		return false;
	}
	
	private void sendErrorMessage(int i){
		String error =  "Erreur: le cas "+i+" n'est pas traité.";
		//Function d'envoi 
	}
	
	
}
