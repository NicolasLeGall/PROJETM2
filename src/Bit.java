
public class Bit {

	
	
	
	public int produceBit(User user[], int actualTime, int nb_bit_moy_genere){
		int bitsGeneres = 0;
		int total_bitsGeneres = 0;
		MRG32k3a mrg = new MRG32k3a();
		boolean continuer = true;	
		int i = 0;
		// Cr�ation d'un nouveau packet 
		Paquet packet = new Paquet(-1, -1, null);
		for(i = 0; i<15; i++){
			continuer = true;
			//packet = null;
			//bitsGeneres=(int)(MRG32k3a()*300);
			// c'est de la magie noire mais sa g�n�re en moyenne 150.5 bit
			//bitsGeneres=(int)((-1 / 0.00666666) *(Math.log( 1 - mrg.rand())));
			bitsGeneres=(int)(mrg.rand()*(nb_bit_moy_genere*2));
		//System.out.println("utilisateur: "+i+" bit geneere "+bitsGeneres);
			total_bitsGeneres = total_bitsGeneres + bitsGeneres;
			packet = user[i].getLePaquet();
			
			user[i].setBit_en_trop(user[i].getBit_en_trop()+bitsGeneres);
	        //Remplissage des paquets 
		
	        while(continuer){

				// si le buffer est vide (il reste rien a envoyer comme bit)
	        	if(user[i].isBufferVide()){
					
					// si le nombre de bit g�n�r� est plus grand que la taille d'un paquet
					if(user[i].getBit_en_trop() > 100){
						user[i].setBufferVide(false);
						
						packet.setDateCreation(actualTime);
						packet.setBitsRestants(100);
						user[i].setBit_en_trop(user[i].getBit_en_trop()-100);
						
						user[i].setSommePaquet(user[i].getSommePaquet()+1);
				//System.out.println("utilisateur: "+i+" bit geneere"+user[i].getBit_en_trop());
					}else{
						continuer = false;
	
					}
				}else{//si le buffer contient quelque chose (n'est pas vide)
					// on parcourt les paquet pour arriver au dernier
					//System.out.println("test2");
					packet = user[i].getLePaquet();
					
					while(packet.getNextPaquet() != null){
						packet = packet.getNextPaquet();
					}
					
					if(user[i].getBit_en_trop() > 100){
						packet.setNextPaquet(new Paquet(-1, -1, null));
						packet.getNextPaquet().setDateCreation(actualTime);
						packet.getNextPaquet().setBitsRestants(100);
						user[i].setBit_en_trop(user[i].getBit_en_trop()-100);
						
						/*
						user[i].setLePaquet(packet);
						packet = packet.getNextPaquet();
						*/
						user[i].setSommePaquet(user[i].getSommePaquet()+1);
					}else{
						continuer = false;
					}
				}
	        }
        }
	
		return total_bitsGeneres;
	}
	
	public int consumeBit(User user, int subCarrier, int actualTime){
		int bitConsommes = 0;

		int SNRSubcarrier[] = user.getSNRSubcarrier();
		user.setSommeUR(user.getSommeUR()+1);
		//Si on consomme plus de bits que le paquet en contient
		if(user.getLePaquet().getBitsRestants() <= SNRSubcarrier[subCarrier]){
			//Mise � jour pour les statistiques
			user.setSommeDelais(user.getSommeDelais() + (actualTime - (user.getLePaquet().getDateCreation())));  
			/*si le delais est sup�rieurs a seuil du PDOR Pour le calcul du PDOR*/
			if((actualTime - (user.getLePaquet().getDateCreation())) >= user.getSeuilPDOR()){
				user.setSommeDelaisPDOR(user.getSommeDelaisPDOR()+1);
			}
			// si il reste plusieurs packet dans la chaine exemple de chaine (64=>100=>100=>NULL)
			if((user.getLePaquet().getNextPaquet() != null)){
				user.setSommePaquets_consommer(user.getSommePaquets_consommer()+1);
				//On soustrait au prochain paquet le SNR moins le contenu du paquet actuel 
				bitConsommes = SNRSubcarrier[subCarrier];
		//System.out.println(user.getLePaquet().getNextPaquet().getBitsRestants()+" SNRSubcarrier "+SNRSubcarrier[subCarrier]+" premier paquet bit restant "+user.getLePaquet().getBitsRestants());
				user.getLePaquet().getNextPaquet().setBitsRestants((user.getLePaquet().getNextPaquet().getBitsRestants()) - (SNRSubcarrier[subCarrier] - (user.getLePaquet().getBitsRestants())));
				//Puis on supprime le paquet 
				user.setLePaquet(user.getLePaquet().getNextPaquet());
			}else{//si il rester qu'un packet
				user.setSommePaquets_consommer(user.getSommePaquets_consommer()+1);
				bitConsommes = user.getLePaquet().getBitsRestants();
				user.getLePaquet().setBitsRestants(0);
				user.setBufferVide(true);
		//System.out.println(bitConsommes);
			}
		}else{//Si il y a assez de bits dans ce paquet
			user.getLePaquet().setBitsRestants((user.getLePaquet().getBitsRestants())-(SNRSubcarrier[subCarrier]));
			bitConsommes = SNRSubcarrier[subCarrier];
		}

		// On retourne le nombre de bits c�t�s 
		
		return bitConsommes;
	}
}
