
public class User {
	
<<<<<<< HEAD


	private int cooperation;
	private int seuilPDOR;
=======
>>>>>>> origin/master
	private int SNRmoyen;
	private int SNRSubcarrier[];
	private Paquet lePaquet;
	
<<<<<<< HEAD
	public User(int coop, int seuil){
		
		cooperation = coop;
		seuilPDOR = seuil;
		
		SNRmoyen = 0;
		//lePaquet = new Paquet(seuil, seuil, lePaquet);
		
		for(int i = 0; i < 128; i++){
			SNRSubcarrier[i] = 0;
		}
		
	}
	public int getCooperation() {
		return cooperation;
	}

	public void setCooperation(int cooperation) {
		this.cooperation = cooperation;
	}

	public int getSeuilPDOR() {
		return seuilPDOR;
	}

	public void setSeuilPDOR(int seuilPDOR) {
		this.seuilPDOR = seuilPDOR;
	}
	
=======
	public User(int snrmoyen, int snrsubcarrier[], Paquet lepaquet){
		
		SNRmoyen = snrmoyen;
		lePaquet = lepaquet;
		
		for(int i = 0; i < 128; i++){
			SNRSubcarrier[i] = snrsubcarrier[i];
		}
		
	}

>>>>>>> origin/master
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
