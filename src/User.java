
public class User {
	
	private int SNRmoyen;
	private int SNRSubcarrier[];
	private Paquet lePaquet;
	
	public User(int snrmoyen, int snrsubcarrier[], Paquet lepaquet){
		
		SNRmoyen = snrmoyen;
		lePaquet = lepaquet;
		
		for(int i = 0; i < 128; i++){
			SNRSubcarrier[i] = snrsubcarrier[i];
		}
		
	}

	public int getSNRmoyen() {
		return SNRmoyen;
	}

	public void setSNRmoyen(int sNRmoyen) {
		SNRmoyen = sNRmoyen;
	}

	public int[] getSNRSubcarrier() {
		return SNRSubcarrier;
	}

	public void setSNRSubcarrier(int[] sNRSubcarrier) {
		SNRSubcarrier = sNRSubcarrier;
	}

	public Paquet getLePaquet() {
		return lePaquet;
	}

	public void setLePaquet(Paquet lePaquet) {
		this.lePaquet = lePaquet;
	}
	
}
