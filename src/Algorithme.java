
public class Algorithme {

	int NB_TIME_SLOTS = 5;
	int NB_SUBCARRIERS = 128;
	int nb_user = 15;
	Bit bit = new Bit();
	int SNRSubcarrier_currentUser[] = new int[128];
	
	public void RR(User user[], int actualTime){
		int k = 0;
		int i = 0;
		int j = 0;
		int currentUser = 0;
		//int debitTotalTrame = 0;
		int tamp = 0;

		
		for(i = 0; i < NB_TIME_SLOTS ; i++){
			for(j = 0; j< NB_SUBCARRIERS ; j++){
				while(tamp != -1){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if((user[currentUser].getbit_restant_paquet() > 0) && (!user[currentUser].isBufferVide())){
						//consumeBit renvois des valeurs de 0 à 10. renvois le nombre de bit consumer dans un packet d'un utilisateurs dans un time slot pour une subcarrier
						//debitTotalTrame = debitTotalTrame + bit.consumeBit(user[currentUser], j, actualTime);
						
						user[currentUser].setSommeUR(user[currentUser].getSommeUR()+1);
						user[currentUser].setNb_bit_a_allouer(user[currentUser].getNb_bit_a_allouer() + user[currentUser].getSNRSubcarrier_case(j));
						user[currentUser].setbit_restant_paquet(user[currentUser].getbit_restant_paquet()-user[currentUser].getSNRSubcarrier_case(j));
						tamp = -1;
					
					//sinon on passe a l'user d'apres
					}else{
						//permet de parcourir tout les utilisateurs pour voir si chaqun a quelque chose a consommer.
						tamp = tamp + 1;
						if(tamp == nb_user){
							tamp = -1;
						}
					}
					//le modulo permet de revenir à 0 quand on a atteint le nb_user on parcour donc les utilisateur de 0 à nb_user
					currentUser = (currentUser+1) % nb_user;
					
				}
				tamp = 0;
			}
		}

	}
	
	public void maxSNR(User user[], int actualTime){
		int MaxU = 0;
		int i, g, j, k, debitTotalTrame = 0;
		int nouveau = 0;
		int random_user = 0;
		MRG32k3a mrg = new MRG32k3a();
		int[][] SNRSubcarrier_i = new int[15][128];
		int SNRSubcarrier_MaxU[] = new int[128];
		
		for(i=0; i< 15;i++){
			for(j=0; j<128; j++){
				SNRSubcarrier_i[i][j] = user[i].getSNRSubcarrier_case(j);			
			}	
		}
		i=0;
		j=0;
		
	/*NB_SUBCARRIERS = 128 NB_TIME_SLOTS = 5 */
		for(g = 0; g < NB_TIME_SLOTS ; g++){// parcours les timeslots, //tant que User.BufferVide > 0 ou que g<5, on transmet au debit actuel a cet user
			for(j = 0; j < NB_SUBCARRIERS ; j++){ //parcourt les subcariers
			
				/*pour empécher le cas ou MaxU reste MAxu alors que sont buffer est vide*/
				nouveau = 0;
				for (i = 0; i < nb_user ; i++){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if((user[i].getbit_restant_paquet() > 0) && !(user[i].isBufferVide())){
						MaxU = i;
						break;
					}
				}
				//on commence a parcourir la liste des utilisateurs par un user au hasard pour ne pas parcourir la liste tout le temps de la meme façon
				random_user=(int)(mrg.rand()*nb_user);
				//parcour de la premier partie de la liste des utilisateurs
				for (i = random_user; i < nb_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
			
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((SNRSubcarrier_i[i][j] >= user[MaxU].getSNRSubcarrier_case(j)) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				//parcour de la seconde partie de la liste dse utilisateurs
				for (i = 0; i < random_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
			
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((SNRSubcarrier_i[i][j] >= user[MaxU].getSNRSubcarrier_case(j)) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				/*printf("maxU = %d   ", MaxU);*/
				//une fois qu'on a notre MaxU c'est a dire l'utilisateurs avec le meilleur SNR sur la subcarrier on lui fait envoyer ces bits
				if(nouveau == 1){
					//debitTotalTrame = debitTotalTrame + bit.consumeBit(user[MaxU], actualTime);
			
					user[MaxU].setSommeUR(user[MaxU].getSommeUR()+1);
					user[MaxU].setNb_bit_a_allouer(user[MaxU].getNb_bit_a_allouer() + user[MaxU].getSNRSubcarrier_case(j));
					user[MaxU].setbit_restant_paquet(user[MaxU].getbit_restant_paquet()-user[MaxU].getSNRSubcarrier_case(j));
				}
				

			}

		}
	}
	
	public void WFO(User user[], int actualTime){
		int MaxU = 0;
		int i, g, j, k, debitTotalTrame = 0;
		int nouveau = 0;
		int random_user = 0;
		MRG32k3a mrg = new MRG32k3a();
		int[][] SNRSubcarrier_i = new int[15][128];
		int SNRSubcarrier_MaxU[] = new int[128];
		
		for(i=0; i< 15;i++){
			for(j=0; j<128; j++){
				SNRSubcarrier_i[i][j] = user[i].getSNRSubcarrier_case(j);			
			}	
		}
		i=0;
		j=0;
		
	/*NB_SUBCARRIERS = 128 NB_TIME_SLOTS = 5 */
		for(g = 0; g < NB_TIME_SLOTS ; g++){// parcours les timeslots, //tant que User.BufferVide > 0 ou que g<5, on transmet au debit actuel a cet user
			for(j = 0; j < NB_SUBCARRIERS ; j++){ //parcourt les subcariers
			
				/*pour empécher le cas ou MaxU reste MAxu alors que sont buffer est vide*/
				nouveau = 0;
				for (i = 0; i < nb_user ; i++){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if((user[i].getbit_restant_paquet() > 0) && !(user[i].isBufferVide())){
						MaxU = i;
						break;
					}
				}
				//on commence a parcourir la liste des utilisateurs par un user au hasard pour ne pas parcourir la liste tout le temps de la meme façon
				random_user=(int)(mrg.rand()*nb_user);
				//parcour de la premier partie de la liste des utilisateurs
				for (i = random_user; i < nb_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
				
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((((SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getPDOR()*user[i].getPDOR()*user[i].getPDOR()))) >= ((user[MaxU].getSNRSubcarrier_case(j))*(1+1000000*(user[MaxU].getSommeDelaisPDOR()*user[MaxU].getSommeDelaisPDOR()*user[MaxU].getSommeDelaisPDOR())))) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				//parcour de la seconde partie de la liste dse utilisateurs
				for (i = 0; i < random_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
				
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((((SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getPDOR()*user[i].getPDOR()*user[i].getPDOR()))) >= ((user[MaxU].getSNRSubcarrier_case(j))*(1+1000000*(user[MaxU].getSommeDelaisPDOR()*user[MaxU].getSommeDelaisPDOR()*user[MaxU].getSommeDelaisPDOR())))) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				/*printf("maxU = %d   ", MaxU);*/
				//une fois qu'on a notre MaxU c'est a dire l'utilisateurs avec le meilleur SNR sur la subcarrier on lui fait envoyer ces bits
				if(nouveau == 1){
				//	debitTotalTrame = debitTotalTrame + bit.consumeBit(user[MaxU], j, actualTime);
					user[MaxU].setSommeUR(user[MaxU].getSommeUR()+1);
					user[MaxU].setNb_bit_a_allouer(user[MaxU].getNb_bit_a_allouer() + user[MaxU].getSNRSubcarrier_case(j));
					user[MaxU].setbit_restant_paquet(user[MaxU].getbit_restant_paquet()-user[MaxU].getSNRSubcarrier_case(j));
				}
				

			}

		}
	}
	
	public void CEI(User user[], int actualTime){
		int MaxU = 0;
		int i, g, j, k, debitTotalTrame = 0;
		int nouveau = 0;
		int random_user = 0;
		MRG32k3a mrg = new MRG32k3a();
		int[][] SNRSubcarrier_i = new int[15][128];
		int SNRSubcarrier_MaxU[] = new int[128];
		
		int Rk_i = 0;// where Rk = global amount of data,
	    double Dk_i = 0;// and Dk is the amount of data for his own requirement
	    int Tk_i = 1;// Tk is a Security variable (too disable Cheaters)
	    double formule_i =0;
	    
	    int Rk_MaxU = 0;// where Rk = global amount of data,
	    double Dk_MaxU = 0;// and Dk is the amount of data for his own requirement
	    int Tk_MaxU = 1;// Tk is a Security variable (too disable Cheaters)
	    double formule_MaxU =0;
	    
		for(i=0; i< 15;i++){
			for(j=0; j<128; j++){
				SNRSubcarrier_i[i][j] = user[i].getSNRSubcarrier_case(j);			
			}	
		}
		i=0;
		j=0;
		
	/*NB_SUBCARRIERS = 128 NB_TIME_SLOTS = 5 */
		for(g = 0; g < NB_TIME_SLOTS ; g++){// parcours les timeslots, //tant que User.BufferVide > 0 ou que g<5, on transmet au debit actuel a cet user
			for(j = 0; j < NB_SUBCARRIERS ; j++){ //parcourt les subcariers
			
				/*pour empécher le cas ou MaxU reste MAxu alors que sont buffer est vide*/
				nouveau = 0;
				for (i = 0; i < nb_user ; i++){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if((user[i].getbit_restant_paquet() > 0) && !(user[i].isBufferVide())){
						MaxU = i;
						break;
					}
				}
				//on commence a parcourir la liste des utilisateurs par un user au hasard pour ne pas parcourir la liste tout le temps de la meme façon
				random_user=(int)(mrg.rand()*nb_user);
				//parcour de la premier partie de la liste des utilisateurs
				for (i = random_user; i < nb_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
		
					Rk_i = SNRSubcarrier_i[i][j];
	                Dk_i = Rk_i - ((((double)(user[i].getCooperation())/200.0))*Rk_i); 
	                if(Dk_i==0){
	                	Dk_i=0.1;
	                }
	                formule_i = (Rk_i/Dk_i)*Tk_i;
	                
	                //System.out.println("SNR "+SNRSubcarrier_i[i][j]+" coop: "+(((user[i].getCooperation())/200.0))+" *rk_i "+Rk_i+ " Dk_i "+Dk_i+" formule_i "+formule_i);
	                
	                Rk_MaxU = user[MaxU].getSNRSubcarrier_case(j);
	                Dk_MaxU = Rk_MaxU - (((double)(user[MaxU].getCooperation())/200.0))*Rk_MaxU; 
	                if(Dk_MaxU==0){
	                	Dk_MaxU=0.1;
	                }
	                formule_MaxU = (Rk_MaxU/Dk_MaxU)*Tk_MaxU;
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((formule_i >= formule_MaxU) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				//parcour de la seconde partie de la liste dse utilisateurs
				for (i = 0; i < random_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
				
					
					Rk_i = SNRSubcarrier_i[i][j];
	                Dk_i = Rk_i - ((((double)(user[i].getCooperation())/200.0))*Rk_i); 
	                if(Dk_i==0){
	                	Dk_i=0.1;
	                }
	                formule_i = (Rk_i/Dk_i)*Tk_i;
	                
	                //System.out.println("SNR "+SNRSubcarrier_i[i][j]+" coop: "+(((user[i].getCooperation())/200.0))+" *rk_i "+Rk_i+ " Dk_i "+Dk_i+" formule_i "+formule_i);
	                
	                Rk_MaxU = user[MaxU].getSNRSubcarrier_case(j);
	                Dk_MaxU = Rk_MaxU - (((double)(user[MaxU].getCooperation())/200.0))*Rk_MaxU; 
	                if(Dk_MaxU==0){
	                	Dk_MaxU=0.1;
	                }
	                formule_MaxU = (Rk_MaxU/Dk_MaxU)*Tk_MaxU;
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((formule_i >= formule_MaxU) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				/*printf("maxU = %d   ", MaxU);*/
				//une fois qu'on a notre MaxU c'est a dire l'utilisateurs avec le meilleur SNR sur la subcarrier on lui fait envoyer ces bits
				if(nouveau == 1){
				//	debitTotalTrame = debitTotalTrame + bit.consumeBit(user[MaxU], j, actualTime);
					user[MaxU].setSommeUR(user[MaxU].getSommeUR()+1);
					user[MaxU].setNb_bit_a_allouer(user[MaxU].getNb_bit_a_allouer() + user[MaxU].getSNRSubcarrier_case(j));
					user[MaxU].setbit_restant_paquet(user[MaxU].getbit_restant_paquet()-user[MaxU].getSNRSubcarrier_case(j));
				}
				

			}

		}

	}
	
	public void CEI_WFO(User user[], int actualTime){
		int MaxU = 0;
		int i, g, j, k, debitTotalTrame = 0;
		int nouveau = 0;
		int random_user = 0;
		MRG32k3a mrg = new MRG32k3a();
		int[][] SNRSubcarrier_i = new int[15][128];
		int SNRSubcarrier_MaxU[] = new int[128];
		
		int Rk_i = 0;// where Rk = global amount of data,
	    double Dk_i = 0;// and Dk is the amount of data for his own requirement
	    int Tk_i = 1;// Tk is a Security variable (too disable Cheaters)
	    double formule_i =0;
	    
	    int Rk_MaxU = 0;// where Rk = global amount of data,
	    double Dk_MaxU = 0;// and Dk is the amount of data for his own requirement
	    int Tk_MaxU = 1;// Tk is a Security variable (too disable Cheaters)
	    double formule_MaxU =0;
	    
		for(i=0; i< 15;i++){
			for(j=0; j<128; j++){
				SNRSubcarrier_i[i][j] = user[i].getSNRSubcarrier_case(j);			
			}	
		}
		i=0;
		j=0;
		
	/*NB_SUBCARRIERS = 128 NB_TIME_SLOTS = 5 */
		for(g = 0; g < NB_TIME_SLOTS ; g++){// parcours les timeslots, //tant que User.BufferVide > 0 ou que g<5, on transmet au debit actuel a cet user
			for(j = 0; j < NB_SUBCARRIERS ; j++){ //parcourt les subcariers
			
				/*pour empécher le cas ou MaxU reste MAxu alors que sont buffer est vide*/
				nouveau = 0;
				for (i = 0; i < nb_user ; i++){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if((user[i].getbit_restant_paquet() > 0) && !(user[i].isBufferVide())){
						MaxU = i;
						break;
					}
				}
				//on commence a parcourir la liste des utilisateurs par un user au hasard pour ne pas parcourir la liste tout le temps de la meme façon
				random_user=(int)(mrg.rand()*nb_user);
				//parcour de la premier partie de la liste des utilisateurs
				for (i = random_user; i < nb_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
				
					
					//(SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getSommeDelaisPDOR()^3)
					Rk_i = (int) ((SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getPDOR()*user[i].getPDOR()*user[i].getPDOR())));
	                Dk_i = Rk_i - ((((double)(user[i].getCooperation())/200.0))*Rk_i); 
	                if(Dk_i==0){
	                	Dk_i=0.1;
	                }
	                formule_i = (Rk_i/Dk_i)*Tk_i;
	                
	                //System.out.println("SNR "+SNRSubcarrier_i[i][j]+" coop: "+(((user[i].getCooperation())/200.0))+" *rk_i "+Rk_i+ " Dk_i "+Dk_i+" formule_i "+formule_i);
	                
	                Rk_MaxU =  (int) ((user[MaxU].getSNRSubcarrier_case(j))*(1+1000000*(user[MaxU].getPDOR()*user[MaxU].getPDOR()*user[MaxU].getPDOR())));
	                Dk_MaxU = Rk_MaxU - (((double)(user[MaxU].getCooperation())/200.0))*Rk_MaxU; 
	                if(Dk_MaxU==0){
	                	Dk_MaxU=0.1;
	                }
	                formule_MaxU = (Rk_MaxU/Dk_MaxU)*Tk_MaxU;
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((formule_i >= formule_MaxU) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				//parcour de la seconde partie de la liste dse utilisateurs
				for (i = 0; i < random_user ; i++){
					//SNRSubcarrier_i = user[i].getSNRSubcarrier();
					//SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
				
					
					//(SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getSommeDelaisPDOR()^3)
					Rk_i = (int) ((SNRSubcarrier_i[i][j])*(1+1000000*(user[i].getPDOR()*user[i].getPDOR()*user[i].getPDOR())));
	                Dk_i = Rk_i - ((((double)(user[i].getCooperation())/200.0))*Rk_i); 
	                if(Dk_i==0){
	                	Dk_i=0.1;
	                }
	                formule_i = (Rk_i/Dk_i)*Tk_i;
	                
	                //System.out.println("SNR "+SNRSubcarrier_i[i][j]+" coop: "+(((user[i].getCooperation())/200.0))+" *rk_i "+Rk_i+ " Dk_i "+Dk_i+" formule_i "+formule_i);
	                
	                Rk_MaxU =  (int) ((user[MaxU].getSNRSubcarrier_case(j))*(1+1000000*(user[MaxU].getPDOR()*user[MaxU].getPDOR()*user[MaxU].getPDOR())));
	                Dk_MaxU = Rk_MaxU - (((double)(user[MaxU].getCooperation())/200.0))*Rk_MaxU; 
	                if(Dk_MaxU==0){
	                	Dk_MaxU=0.1;
	                }
	                formule_MaxU = (Rk_MaxU/Dk_MaxU)*Tk_MaxU;
					//System.out.println("user: "+i+" sub: "+j+" SNR: "+SNRSubcarrier_i[i][j]);
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((formule_i >= formule_MaxU) && (user[i].getbit_restant_paquet() > 0) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				/*printf("maxU = %d   ", MaxU);*/
				//une fois qu'on a notre MaxU c'est a dire l'utilisateurs avec le meilleur SNR sur la subcarrier on lui fait envoyer ces bits
				if(nouveau == 1){
				//	debitTotalTrame = debitTotalTrame + bit.consumeBit(user[MaxU], j, actualTime);
					user[MaxU].setSommeUR(user[MaxU].getSommeUR()+1);
					user[MaxU].setNb_bit_a_allouer(user[MaxU].getNb_bit_a_allouer() + user[MaxU].getSNRSubcarrier_case(j));
					user[MaxU].setbit_restant_paquet(user[MaxU].getbit_restant_paquet()-user[MaxU].getSNRSubcarrier_case(j));
				}
				

			}

		}

		/*	 //CEI= Mkn * (Rk/Dk) * Tk 
	    int Rk;// where Rk = global amount of data,
	    int Dk;// and Dk is the amount of data for his own requirement
	    int Tk = 1;// Tk is a Security variable (too disable Cheaters)
	    int formule;
	 
	 
	        for (int sub = 0; sub < NB_SUBCARRIERS ;sub ++){
	            for (int u = userRand; u < NB_MAX_USERS; u++){
	                 
	                Rk = a->MKN[u][sub];
	                Dk = Rk - (((a->users[u]->cooperation)/2)/100)*Rk; //CEI STUFF
	                if(Dk == 0) 
	                    formule = 1; //on ne peut pas faire une division par 0 donc on met une valeure par defaut si ca tombe a 0
	                else
	                    formule = (Rk/Dk)*Tk;
	 
	                if(a->MKN[u][sub]*formule > bestMkn ){//quand on trouve un user u sur la frequence sub qui a un plus gros mkn
	                    if(a->users[u]->bufferVide == false){//si il a qqchose dans son buffer
	                        a->bestUser[sub] = u;//il prends sa place
	                        bestMkn = a->MKN[u][sub]; // et on remplace le bestmkn par cet user
	                    }//if
	                    if(u == (NB_MAX_USERS - 1) && a->users[u]->bufferVide && bestMkn == -1)//si on atteint le dernier user, que son buffer est vide et que tous le bestmkn reste inchangé
	                        a->bestUser[sub] = bestMkn; // le tableau des meilleurs mkn affiche -1 (aucun user need allocation)
	                }//if
	            if ( u == (NB_MAX_USERS - 1)){
	                u = -1; boucle = 1; // systeme pour eviter de mettre 2 for, et de dupliquer le code
	            }
	            if(u == userRand -1 && boucle == 1) //si on a fait un tour
	                break;  
	            }//for
	        bestMkn = -1;  //et le mkn redeviens nul pour effectuer une boucle sur la prochaine frequence
	        }//for
	    }//IF sch
	 
	    // si on est en WFO on ajoutera le PDOR a l'equation de selection
	    else if (sch == WFO){
	        for (int sub = 0; sub < NB_SUBCARRIERS ;sub ++){
	            for (int u = userRand; u < NB_MAX_USERS; u++){
	                a->users[u]->PDOR = FPDOR(a,u); //on met a jour son pdor
	                if(a->MKN[u][sub]*FPDOR(a,u) > bestMkn ){//quand on trouve un user u sur la frequence sub qui a un plus gros mkn*PDOR
	                    if(a->users[u]->bufferVide == false){//si il a qqchose dans son buffer
	                        a->bestUser[sub] = u;//il prends sa place
	                        bestMkn = a->MKN[u][sub]; // et on remplace le bestmkn par cet user
	                    }//if
	                    if(u == (NB_MAX_USERS - 1) && a->users[u]->bufferVide && bestMkn == -1)//si on atteint le dernier user, que son buffer est vide et que tous le bestmkn reste inchangé
	                        a->bestUser[sub] = bestMkn; // le tableau des meilleurs mkn affiche -1 (aucun user need allocation)
	                }//if
	            if ( u == (NB_MAX_USERS - 1)){
	                u = -1; boucle = 1; // systeme pour eviter de mettre 2 for, et de dupliquer le code
	            }
	            if(u == userRand -1 && boucle == 1) //si on a fait un tour
	                break;  
	            }//for
	        bestMkn = -1;  //et le mkn redeviens nul pour effectuer une boucle sur la prochaine frequence
	        }//for
	    }//IF sch
		
		
		
		*/
	}
}
