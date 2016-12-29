
public class Antenne {
	
	private User user[];
	private int tempsActuel;
	
	public Antenne(User utilisateur[], int tempsactuel){
		
		tempsActuel=tempsactuel;
		
	}

	public User[] getUser() {
		return user;
	}

	public void setUser(User[] user) {
		this.user = user;
	}

	public int getTempsActuel() {
		return tempsActuel;
	}

	public void setTempsActuel(int tempsActuel) {
		this.tempsActuel = tempsActuel;
	}
	
	
}
