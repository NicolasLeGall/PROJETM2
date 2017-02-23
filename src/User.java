
public class User {
	

	private int distance;
	private int cooperation;
	private int seuilPDOR;
	private int SNRmoyen;
	private double PDOR;
	private double fenetrePDOR;
	private int sommeDelaisPDOR_tour;
	private int sommePaquets_consommer_tour;
	private int SNRSubcarrier[] = new int[128];
	private long sommePaquet;
	private long sommeDelais;
	private long sommeDelaisPDOR;
	private long sommePaquets_consommer;
	private long somme_nb_bit_relayer;
	private long bit_en_trop;
	private long sommeUR;
	private boolean bufferVide;
	private Paquet lePaquet;
	private Paquet lePaquet_queue;
	private int bitsGeneres;
	private int compteur_bitsGeneres;
	private int nb_bit_a_allouer;
	private long bit_restant_paquet;
	private long real_bit_restant_paquet;



	public User(int coop, int seuil, int dist){
		distance = dist;
		cooperation = coop;
		seuilPDOR = seuil;
		lePaquet = new Paquet(-1, -1, null);
		lePaquet_queue = null;
		SNRmoyen = 0;
		bit_en_trop = 0;
		sommePaquet = 0;
		real_bit_restant_paquet=0;
		bitsGeneres = 0;
		compteur_bitsGeneres = 0;	
		nb_bit_a_allouer = 0;
		somme_nb_bit_relayer = 0;
		bit_restant_paquet=0;
		PDOR = 0;
		fenetrePDOR = 0;
		sommeDelaisPDOR_tour = 0;
		sommePaquets_consommer_tour = 0;
		bufferVide = true;
		// on initialise les 128 subcarrier et on considére que cette valeur est la meme sur les 5 time slot
		for(int i = 0; i < 128; i++){
			SNRSubcarrier[i] = 0;
		}
		
	}
	
	
	public Paquet getLePaquet_queue() {
		return lePaquet_queue;
	}


	public void setLePaquet_queue(Paquet lePaquet_queue) {
		this.lePaquet_queue = lePaquet_queue;
	}


	public int getDistance() {
		return distance;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}


	public long getReal_bit_restant_paquet() {
		return real_bit_restant_paquet;
	}


	public void setReal_bit_restant_paquet(long real_bit_restant_paquet) {
		this.real_bit_restant_paquet = real_bit_restant_paquet;
	}


	public int getSommeDelaisPDOR_tour() {
		return sommeDelaisPDOR_tour;
	}


	public void setSommeDelaisPDOR_tour(int sommeDelaisPDOR_tour) {
		this.sommeDelaisPDOR_tour = sommeDelaisPDOR_tour;
	}


	public int getSommePaquets_consommer_tour() {
		return sommePaquets_consommer_tour;
	}


	public void setSommePaquets_consommer_tour(int sommePaquets_consommer_tour) {
		this.sommePaquets_consommer_tour = sommePaquets_consommer_tour;
	}


	public double getFenetrePDOR() {
		return fenetrePDOR;
	}


	public void setFenetrePDOR(double fenetrePDOR) {
		this.fenetrePDOR = fenetrePDOR;
	}


	public double getPDOR() {
		return PDOR;
	}


	public void setPDOR(double pDOR) {
		PDOR = pDOR;
	}


	public long getBit_restant_paquet() {
		return bit_restant_paquet;
	}


	public void setBit_restant_paquet(long bit_restant_paquet) {
		this.bit_restant_paquet = bit_restant_paquet;
	}


	public long getbit_restant_paquet() {
		return bit_restant_paquet;
	}


	public void setbit_restant_paquet(long bit_restant_paquet) {
		this.bit_restant_paquet = bit_restant_paquet;
	}


	public long getSomme_nb_bit_relayer() {
		return somme_nb_bit_relayer;
	}

	public void setSomme_nb_bit_relayer(long somme_nb_bit_relayer) {
		this.somme_nb_bit_relayer = somme_nb_bit_relayer;
	}

	public int getNb_bit_a_allouer() {
		return nb_bit_a_allouer;
	}

	public void setNb_bit_a_allouer(int nb_bit_a_consommer) {
		this.nb_bit_a_allouer = nb_bit_a_consommer;
	}

	public int getBitsGeneres() {
		return bitsGeneres;
	}


	public void setBitsGeneres(int bitsGeneres) {
		this.bitsGeneres = bitsGeneres;
	}


	public int getCompteur_bitsGeneres() {
		return compteur_bitsGeneres;
	}


	public void setCompteur_bitsGeneres(int compteur_bitsGeneres) {
		this.compteur_bitsGeneres = compteur_bitsGeneres;
	}
	
	public long getSommeUR() {
		return sommeUR;
	}


	public void setSommeUR(long sommeUR) {
		this.sommeUR = sommeUR;
	}


	public long getSommePaquets_consommer() {
		return sommePaquets_consommer;
	}


	public void setSommePaquets_consommer(long sommePaquets_consommer) {
		this.sommePaquets_consommer = sommePaquets_consommer;
	}


	public long getSommeDelaisPDOR() {
		return sommeDelaisPDOR;
	}


	public void setSommeDelaisPDOR(long sommeDelaisPDOR) {
		this.sommeDelaisPDOR = sommeDelaisPDOR;
	}


	public long getSommeDelais() {
		return sommeDelais;
	}


	public void setSommeDelais(long sommeDelais) {
		this.sommeDelais = sommeDelais;
	}


	public long getSommePaquet() {
		return sommePaquet;
	}


	public void setSommePaquet(long sommePaquet) {
		this.sommePaquet = sommePaquet;
	}


	public boolean isBufferVide() {
		return bufferVide;
	}


	public void setBufferVide(boolean bufferVide) {
		this.bufferVide = bufferVide;
	}


	public long getBit_en_trop() {
		return bit_en_trop;
	}
	
	public void setBit_en_trop(long bit_en_trop) {
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
	
	public int getSNRSubcarrier_case(int i) {
		return SNRSubcarrier[i];
	}
	
	public void setSNRSubcarrier(int[] sNRSubcarrier) {
		SNRSubcarrier = sNRSubcarrier;
	}
	
	public void setSNRSubcarrier_case(int[] sNRSubcarrier, int i) {
		SNRSubcarrier[i] = sNRSubcarrier[i];
	}

	public Paquet getLePaquet() {
		return lePaquet;
	}

	public void setLePaquet(Paquet lePaquet) {
		this.lePaquet = lePaquet;
	}
	
}
