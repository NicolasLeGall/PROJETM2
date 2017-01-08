
public class User {
	


	private int cooperation;
	private int seuilPDOR;
	private int SNRmoyen;
	private int SNRSubcarrier[] = new int[128];
	private int sommePaquet;
	private int sommeDelais;
	private int sommeDelaisPDOR;
	private int sommePaquets_consommer;
	private int bit_en_trop;
	private int sommeUR;
	private boolean bufferVide;
	private Paquet lePaquet;
	
	public User(int coop, int seuil){
		
		cooperation = coop;
		seuilPDOR = seuil;
		lePaquet = new Paquet(-1, -1, null);
		SNRmoyen = 0;
		bit_en_trop = 0;
		sommePaquet = 0;
		bufferVide = true;
		// on initialise les 128 subcarrier et on considére que cette valeur est la meme sur les 5 time slot
		for(int i = 0; i < 128; i++){
			SNRSubcarrier[i] = 0;
		}
		
	}
	
	
	public int getSommeUR() {
		return sommeUR;
	}


	public void setSommeUR(int sommeUR) {
		this.sommeUR = sommeUR;
	}


	public int getSommePaquets_consommer() {
		return sommePaquets_consommer;
	}


	public void setSommePaquets_consommer(int sommePaquets_consommer) {
		this.sommePaquets_consommer = sommePaquets_consommer;
	}


	public int getSommeDelaisPDOR() {
		return sommeDelaisPDOR;
	}


	public void setSommeDelaisPDOR(int sommeDelaisPDOR) {
		this.sommeDelaisPDOR = sommeDelaisPDOR;
	}


	public int getSommeDelais() {
		return sommeDelais;
	}


	public void setSommeDelais(int sommeDelais) {
		this.sommeDelais = sommeDelais;
	}


	public int getSommePaquet() {
		return sommePaquet;
	}


	public void setSommePaquet(int sommePaquet) {
		this.sommePaquet = sommePaquet;
	}


	public boolean isBufferVide() {
		return bufferVide;
	}


	public void setBufferVide(boolean bufferVide) {
		this.bufferVide = bufferVide;
	}


	public int getBit_en_trop() {
		return bit_en_trop;
	}
	
	public void setBit_en_trop(int bit_en_trop) {
		this.bit_en_trop = bit_en_trop;
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
