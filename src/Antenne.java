
public class Antenne {
	
	private User user[];
	private int tempsActuel;
	private int max_user;
	
	public Antenne(User utilisateur[], int tempsactuel, int max_u){
		
		tempsActuel=tempsactuel;
		max_user = max_u;
		
		for(int i = 0; i < max_u; i++){
			user[i] = utilisateur[i];
		}
		
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

	public int getmax_user() {
		return max_user;
	}

	public void setmax_user(int mAX_USER) {
		max_user = mAX_USER;
	}
	
	
}
