
public class Bit {

	
	
	
	public int produceBit(User user, int actualTime){
		int bitsGeneres =0 ;
		MRG32k3a mrg = new MRG32k3a();
		boolean continuer = true;	
			
		// Création d'un nouveau packet 
		Paquet packet = null;

		//bitsGeneres=(int)(MRG32k3a()*300);
		// c'est de la magie noire mais sa génére en moyenne 150.5 bit
		bitsGeneres=(int)((-1 / 0.00666666) *(Math.log( 1 - mrg.rand())));
		user.setBit_en_trop(user.getBit_en_trop()+bitsGeneres);
        //Remplissage des paquets 

        while(continuer){

			// si le buffer est vide (il reste rien a envoyer comme bit)
        	if(user.isBufferVide()){
				
				// si le nombre de bit a généré est plus grand que la taille d'un paquet
				if(user.getBit_en_trop() > 100){
					user.setBufferVide(false);
					
					packet.setDateCreation(actualTime);
					packet.setBitsRestants(100);
					user.setBit_en_trop(user.getBit_en_trop()-100);
					packet.setNextPaquet(new Paquet(0, 0, null));
					user.setLePaquet(packet);
					packet = packet.getNextPaquet();
					user.setSommePaquet(user.getSommePaquet()+1);
				}else{
					continuer = false;

				}
			}else{//si le buffer contient quelque chose (n'est pas vide)
				// on parcourt les paquet pour arriver au dernier
				while(user.getLePaquet() != null){
					packet = user.getLePaquet();
				}
				
				if(user.getBit_en_trop() > 100){
					packet.setDateCreation(actualTime);
					packet.setBitsRestants(100);
					user.setBit_en_trop(user.getBit_en_trop()-100);
					packet.setNextPaquet(new Paquet(0, 0, null));
					user.setLePaquet(packet);
					packet = packet.getNextPaquet();
					user.setSommePaquet(user.getSommePaquet()+1);
				}else{
					continuer = false;
				}
			}
        }
	
		return bitsGeneres;
	}
	
	public int consumeBit(User user, int subCarrier, int actualTime){
		int bitConsommes = 0;
		int SNRSubcarrier[] = user.getSNRSubcarrier();
		//Si on consomme plus de bits que le paquet en contient
		if(user.getLePaquet().getBitsRestants() <= SNRSubcarrier[subCarrier]){
			//Mise à jour pour les statistiques
			user.setSommeDelais(user.getSommeDelais() + (actualTime - (user.getLePaquet().getDateCreation())));  
			/*si le delais est supérieurs a seuil du PDOR Pour le calcul du PDOR*/
			if((actualTime - (user.getLePaquet().getDateCreation())) >= user.getSeuilPDOR()){
				user.setSommeDelaisPDOR(user.getSommeDelaisPDOR()+1);
			}
			// si il reste plusieurs packet dans la chaine
			if((user.getLePaquet().getNextPaquet() != null) ){
				user.setSommePaquets_consommer(user.getSommePaquets_consommer()+1);
				//On soustrait au prochain paquet le SNR moins le contenu du paquet actuel 
				bitConsommes = SNRSubcarrier[subCarrier];
				user.getLePaquet().getNextPaquet().setBitsRestants(user.getLePaquet().getNextPaquet().getBitsRestants() - (SNRSubcarrier[subCarrier] - user.getLePaquet().getBitsRestants()));
				//Puis on supprime le paquet 
				user.setLePaquet(user.getLePaquet().getNextPaquet());
			}else{//si il rester qu'un packet
				user.setSommePaquets_consommer(user.getSommePaquets_consommer()+1);
				bitConsommes = user.getLePaquet().getBitsRestants();
				user.getLePaquet().setBitsRestants(0);
				user.setBufferVide(true);
			}
		}else{//Si il y a assez de bits dans ce paquet
			user.getLePaquet().setBitsRestants((user.getLePaquet().getBitsRestants())-(SNRSubcarrier[subCarrier]));
			bitConsommes = SNRSubcarrier[subCarrier];
		}

		// On retourne le nombre de bits côtés 
		
		return bitConsommes;
	}
}
