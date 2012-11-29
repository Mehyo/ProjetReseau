public class Amis{

    private String nom;
    private String url;

    public Amis(String nom, String url){
	this.nom = nom;
	this.url = url;
    }

    public supprimerAmi(){
    }

    public nomAmi(){
    	return this.nom;
    }

    public urlAmi(){
    	return this.url;
    }
    
    public String toString(){
    	return "(" + this.nom + "," + this.url +")";
    }
    
    
}
