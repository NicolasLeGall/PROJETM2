
public class Algorithme {

	int NB_TIME_SLOTS = 5;
	int NB_SUBCARRIERS = 128;
	int nb_user = 15;
	Bit bit = new Bit();
	
	public int RR(User user[], int actualTime){
		int i = 0;
		int j = 0;
		int currentUser = 0;
		int debitTotalTrame = 0;
		int tamp = 0;

		
		for(i = 0; i < NB_TIME_SLOTS ; i++){
			for(j = 0; j< NB_SUBCARRIERS ; j++){
				while(tamp != -1){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if(!(user[currentUser].isBufferVide())){
						//consumeBit renvois des valeurs de 0 à 10. renvois le nombre de bit consumer dans un packet d'un utilisateurs dans un time slot pour une subcarrier
						debitTotalTrame = debitTotalTrame + bit.consumeBit(user[currentUser], j, actualTime);
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

		
		return debitTotalTrame;
	}
	
	public int maxSNR(User user[], int actualTime){
		int MaxU = 0;
		int i, g, j, debitTotalTrame = 0;
		int nouveau = 0;
		int random_user = 0;
		MRG32k3a mrg = new MRG32k3a();
		int SNRSubcarrier_i[] = new int[128];
		int SNRSubcarrier_MaxU[] = new int[128];
		
	/*NB_SUBCARRIERS = 128 NB_TIME_SLOTS = 5 */
		for(g = 0; g < NB_TIME_SLOTS ; g++){// parcours les timeslots, //tant que User.BufferVide > 0 ou que g<5, on transmet au debit actuel a cet user
			for(j = 0; j < NB_SUBCARRIERS ; j++){ //parcourt les subcariers
			
				/*pour empécher le cas ou MaxU reste MAxu alors que sont buffer est vide*/
				nouveau = 0;
				for (i = 0; i < nb_user ; i++){
					// on regarde si l'utilisateur a quelque chose a consommer ( buffer vide = true quand il y a rien dans les paquets)
					if(!(user[i].isBufferVide())){
						MaxU = i;
						break;
					}
				}
				random_user=(int)(mrg.rand()*nb_user);
				for (i = random_user; i < nb_user ; i++){
					SNRSubcarrier_i = user[i].getSNRSubcarrier();
					SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((SNRSubcarrier_i[j] >= SNRSubcarrier_MaxU[j]) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				for (i = 0; i < random_user ; i++){
					SNRSubcarrier_i = user[i].getSNRSubcarrier();
					SNRSubcarrier_MaxU = user[MaxU].getSNRSubcarrier();
					/*si le SNR est mieu que celui a le meilleur SNR jusqu'a present et que buffervide =0 (bufervide est un bolean quand = 0 le buffer n'est pas vide)*/
					if((SNRSubcarrier_i[j] >= SNRSubcarrier_MaxU[j]) && (!(user[i].isBufferVide()))){
						// si l'User a un meilleur debit, et que son buffer n'est pas vide: il devient le MaxUser 
						MaxU = i;
						nouveau = 1;
					}
				}
				/*printf("maxU = %d   ", MaxU);*/
				if(nouveau == 1){
					debitTotalTrame = debitTotalTrame + bit.consumeBit(user[MaxU], j, actualTime);
				}
				

			}

		}
		return debitTotalTrame;
	}
	
	public int WFO(User user[], int actualTime){
		int debitTotalTrame = 0;
		
		return debitTotalTrame;
	}
	
	public int CEI(User user[], int actualTime){
		int debitTotalTrame = 0;
		
		return debitTotalTrame;
	}
	
	public int CEI_WFO(User user[], int actualTime){
		int debitTotalTrame = 0;
		
		return debitTotalTrame;
	}
}
