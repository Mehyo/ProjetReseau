package socialNetwork.src;

public class Friends{

	private String name;
	private String host;
	private boolean status;
	
	public Friends(String name, String host, String status){
		this.name = name;
		this.host = host;
		if(status.compareTo("false") == 0)
			this.status = false;
		else
			this.status = true;
	}
	
	public Friends(){
		this.name = "";
		this.host = "";
		this.status = false;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public String nameFriend(){
		return this.name;
	}

	public String hostFriend(){
		return this.host;
	}

	public String toString(){
		return "(" + this.name + "," + this.host +")";
	}
	
	public boolean isMyFriend(){	
		return this.status;
	}
	
	public void setToFriend(){
		this.status = true;
		//change dans xml
	}
	
	public void setToStranger(){
		this.status = false;
		//change dans xml
	}
}
