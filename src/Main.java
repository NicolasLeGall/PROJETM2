import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		int nb_tours;
		int choixAlgo;
		int i, j, g;
		
		double nb_bit_moy_genere = 130;
		
		Algorithme scheduler = new Algorithme();
		Bit gestion_de_bit = new Bit();
		Scanner scanInt = new Scanner(System.in);
		User tab_user[] = new User[15];
		
		tab_user[0] = new User(100, 80, 6);
		tab_user[1] = new User(100, 250, 6);
		tab_user[2] = new User(100, 1000, 6);
	
		tab_user[3] = new User(125, 80, 6);
		tab_user[4] = new User(125, 250, 6);
		tab_user[5] = new User(125, 1000, 6);
		
		tab_user[6] = new User(150, 80, 6);
		tab_user[7] = new User(150, 250, 6);
		tab_user[8] = new User(150, 1000, 6);
		
		tab_user[9] = new User(175, 80, 6);
		tab_user[10] = new User(175, 250, 6);
		tab_user[11] = new User(175, 1000, 6);
		
		tab_user[12] = new User(200, 80, 6);
		tab_user[13] = new User(200, 250, 6);
		tab_user[14] = new User(200, 1000, 6);
		
		
		int actualTime = 0;
		int bit_restant = 0;
		int debit[] = null;
		long tab_paquet_PDOR[][] = new long[15][1000];
		long tab_paquet_consommer[][] = new long[15][1000];
		long somme_tab_paquet_PDOR[]=new long[15];
		long somme_tab_paquet_consommer[] = new long[15];
		double tab_delais_moyen_user[] = new double[15];
		double debitTotal = 0;
		double debit_relayer = 0;
		double debit_user = 0;
		double nbBitsgenere = 0;
		double debit_total_simu = 0;
		double debit_total_simu_user = 0;
		double debit_total_simu_relayer = 0;
		double total_nbBitsgenere = 0;
		double sommeDelais = 0 ;
		double nbPaquetsTotal = 0 ; 
		double nbPaquetsTotalPDOR = 0 ;
		double nbPaquetsTotalsommePaquets_consommer = 0 ;
		double delais_moyen = 0;
		double PDOR = 0;
		double user_sommeUR = 0;
		double nbPaquetsNonEnvoyes = 0;
		double res_sommeUR = 0;
		double bit_par_UR = 0;
		double taux_remplissage_buffer = 0;
		double somme_bitsRestants = 0;
		
		//initMatriceDebits();
		
		System.out.println("Nombre de tours pour la simulation, chaque tour = 2ms: ");
		nb_tours = scanInt.nextInt();          
		//System.out.println("nombre de tour "+nb_tours);
		System.out.println("Algorithme : 1 pour RR, 2 pour MAXSNR, 3 pour WFO, 4 pour CEI, 5 pour CEI+WFO :");
		choixAlgo = scanInt.nextInt();
		//System.out.println("choix algo "+choixAlgo);
		scanInt.close(); 
		
		/*On �crit dans le fichier csv le nom des colonnes */
		String fichier ="Resultat.csv";
		FileWriter fw = new FileWriter (fichier);
		BufferedWriter bw = new BufferedWriter (fw);
		PrintWriter fichierSortie = new PrintWriter (bw); 
		try {
			
			fichierSortie.println ("nb_tours="+nb_tours+";choixAlgo="+choixAlgo+";\n"); 
			if(choixAlgo == 1){
				fichierSortie.println ("nb_bit_moy_genere_RR;debit_total_simu_RR;debit_user_RR;debit_relayer_RR;total_nbBitsgenere_RR;debitTotal_RR;delais_moyen_RR;PDOR_RR;res_sommeUR_RR;bit_par_UR_RR;taux_remplissage_buffer_RR;nbPaquetsTotalsommePaquets_consommer_RR;"); 
			}
			else if(choixAlgo == 2){
				fichierSortie.println ("nb_bit_moy_genere_MaxSNR;debit_total_simu_MaxSNR;debit_user_MaxSNR;debit_relayer_MaxSNR;total_nbBitsgenere_MaxSNR;debitTotal_MaxSNR;delais_moyen_MaxSNR;PDOR_MaxSNR;res_sommeUR_MaxSNR;bit_par_UR_MaxSNR;taux_remplissage_buffer_MaxSNR;nbPaquetsTotalsommePaquets_consommer_MaxSNR;"); 
			}
			else if(choixAlgo == 3){
				fichierSortie.println ("nb_bit_moy_genere_WFO;debit_total_simu_WFO;debit_user_WFO;debit_relayer_WFO;total_nbBitsgenere_WFO;debitTotal_WFO;delais_moyen_WFO;PDOR_WFO;res_sommeUR_WFO;bit_par_UR_WFO;taux_remplissage_buffer_WFO;nbPaquetsTotalsommePaquets_consommer_WFO;"); 
			}
			else if(choixAlgo == 4){
				fichierSortie.println ("nb_bit_moy_genere_CEI;debit_total_simu_CEI;debit_user;debit_relayer_CEI;total_nbBitsgenere_CEI;debitTotal_CEI;delais_moyen_CEI;PDOR_CEI;res_sommeUR;bit_par_UR_CEI;taux_remplissage_buffer_CEI;nbPaquetsTotalsommePaquets_consommer_CEI;"); 
			}
			else if(choixAlgo == 5){
				fichierSortie.println ("nb_bit_moy_genere_CEIWFO;debit_total_simu_CEIWFO;debit_user;debit_relayer_CEIWFO;total_nbBitsgenere_CEIWFO;debitTotal_CEIWFO;delais_moyen_CEIWFO;PDOR_CEIWFO;res_sommeUR_CEIWFO;bit_par_UR_CEIWFO;taux_remplissage_buffer_CEIWFO;nbPaquetsTotalsommePaquets_consommer_CEIWFO;"); 
			}
			
			fichierSortie.close();
			
		}catch (Exception e){
			System.out.println(e.toString());
		}	
		
		String fichierPDOR ="ResultatPDOR.csv";
		FileWriter fwPDOR = new FileWriter (fichierPDOR);
		BufferedWriter bwPDOR = new BufferedWriter (fwPDOR);
		PrintWriter fichierSortiePDOR = new PrintWriter (bwPDOR); 
		try {
			
			fichierSortiePDOR.println ("nb_tours="+nb_tours+";choixAlgo="+choixAlgo+";\n"); 
			fichierSortiePDOR.println ("nb_bit_moy_genere;[0]0 80;[1]0 250;[2]0 1000;[3]25 80;[4]25 250;[5]25 1000;[6]50 80;[7]50 250;[8]50 1000;[9]75 80;[10]75 250;[11]75 1000;[12]100 80;[13]100 250;[14]100 100;;"
					+ "[0]0 80;[1]0 250;[2]0 1000;[3]25 80;[4]25 250;[5]25 1000;[6]50 80;[7]50 250;[8]50 1000;[9]75 80;[10]75 250;[11]75 1000;[12]100 80;[13]100 250;[14]100 100;;"
					+ "[0]0 80;[1]0 250;[2]0 1000;[3]25 80;[4]25 250;[5]25 1000;[6]50 80;[7]50 250;[8]50 1000;[9]75 80;[10]75 250;[11]75 1000;[12]100 80;[13]100 250;[14]100 100;;"); 
			fichierSortiePDOR.close();
		}catch (Exception e){
			System.out.println(e.toString());
		}
		
		
		/*boucle principal on incr�ment nb_bit_moy_genere de 10 par tour*/
		while(nb_bit_moy_genere < 270){
				
			// un packet qui sert de paquet tampoon pour r�cuper� des informations
			Paquet packet = new Paquet(0, 0, null);
			
			//boucle du temps
			for(i = 0; i < nb_tours; i++){
				/*On donne a chaque utilisateur un d�bit pour les 128 subcarrieur qui varie de 0 � 10 et qui a pour moyenne 6*/
				initMatriceDebits(tab_user);	
				
				/*for(i=0; i< 15;i++){
					System.out.println("\n user: "+i);
					for(j=0; j<128; j++){
						System.out.print(tab_user[i].getSNRSubcarrier_case(j)+" ");		
					}	
				}*/
				
				
				/*Initialisation des paquets utilisateurs*/
				/*Le temps de cr�ation d'un packet est donn�e a chaque packet avec actualTime */
				nbBitsgenere = gestion_de_bit.produceBit(tab_user, actualTime, nb_bit_moy_genere);
				total_nbBitsgenere = total_nbBitsgenere + nbBitsgenere;
				
				
				//listage du contenue des paquets
				/*System.out.println("Apres creation des bits");
				for(j=0; j<15;j++){
					System.out.println("utilisateur: "+j+" bit en trop "+tab_user[j].getBit_en_trop());
					packet = tab_user[j].getLePaquet();
					System.out.print(packet.getBitsRestants()+"("+(actualTime-packet.getDateCreation())+")=>");
					while(packet.getNextPaquet() != null){
						//System.out.println("test3");
						packet = packet.getNextPaquet();
						System.out.print(packet.getBitsRestants()+"("+(actualTime-packet.getDateCreation())+")=>");
					}
					System.out.println();
				}
				System.out.println();*/
	
	
				/*Application de l'algorithme et �tage des bits dans les paquets*/
				if(choixAlgo == 1){
					scheduler.RR(tab_user, actualTime); //congestion a partir de 150
				}
				else if(choixAlgo == 2){
					 scheduler.maxSNR(tab_user, actualTime);//congestion a partir de 210
				}
				else if(choixAlgo == 3){
					 scheduler.WFO(tab_user, actualTime);//congestion a partir de 216
				}
				else if(choixAlgo == 4){
					 scheduler.CEI(tab_user, actualTime);//congestion a partir de 200
				}
				else if(choixAlgo == 5){
					 scheduler.CEI_WFO(tab_user, actualTime);//congestion a partir de 200
				}else{
					System.out.println("choix de l'algorithme mauvais. Arret. \n");
				}
			
				debit = gestion_de_bit.consumeBit(tab_user, actualTime);
				
				debitTotal +=debit[0];
				debit_user +=debit[1];
				debit_relayer +=debit[2];
				
				
				for(g = 0; g < 15; g++){
					//on enl�ve de notre somme la valeur de la case ou on est arriver
					somme_tab_paquet_PDOR[g] = somme_tab_paquet_PDOR[g] - tab_paquet_PDOR[g][i%1000];
					somme_tab_paquet_consommer[g] = somme_tab_paquet_consommer[g] - tab_paquet_consommer[g][i%1000];
					//on met dans le tableau la nouvelle valeur
					tab_paquet_PDOR[g][i%1000] = tab_user[g].getSommeDelaisPDOR_tour();
					tab_paquet_consommer[g][i%1000] = tab_user[g].getSommePaquets_consommer_tour();
					//on calcul la nouvelle somme
					somme_tab_paquet_PDOR[g] = somme_tab_paquet_PDOR[g] + tab_paquet_PDOR[g][i%1000];
					somme_tab_paquet_consommer[g] = somme_tab_paquet_consommer[g] + tab_paquet_consommer[g][i%1000];
					//on calcul le PDOR a partir de la somme mis a jour
					if(somme_tab_paquet_consommer[g]==0){
						//tab_user[g].setFenetrePDOR(((double)(somme_tab_paquet_PDOR[g])) / ((double)(1)));
						tab_user[g].setPDOR(100);
					}else{
						tab_user[g].setFenetrePDOR(((double)(somme_tab_paquet_PDOR[g])) / ((double)(somme_tab_paquet_consommer[g])));
					}
					//System.out.println(tab_user[g].getFenetrePDOR());
					//on met a 0 le nb paquet comsommer par tour  pour le prochain tour
					tab_user[g].setSommePaquets_consommer_tour(0);
					tab_user[g].setSommeDelaisPDOR_tour(0);
					
					
					
					if(tab_user[g].getSommePaquets_consommer()==0){//emp�cher la division par 0
						//tab_user[g].setPDOR(((double)tab_user[g].getSommeDelaisPDOR()/((double)(1))));
						tab_user[g].setPDOR(100);
					}else{
						tab_user[g].setPDOR(((double)tab_user[g].getSommeDelaisPDOR()/((double)(tab_user[g].getSommePaquets_consommer()))));
					}
					/*Calcul du nombre de bit qui reste dans les paquets non envoyer pour chaque utilisateurs*/
					packet = tab_user[g].getLePaquet();
					/*while(packet != null){
						bit_restant = bit_restant + packet.getBitsRestants();
						tab_user[g].setbit_restant_paquet(bit_restant);
						somme_bitsRestants = somme_bitsRestants + packet.getBitsRestants();
						packet = packet.getNextPaquet();
					}*/
					tab_user[g].setbit_restant_paquet(tab_user[g].getReal_bit_restant_paquet());
					somme_bitsRestants = somme_bitsRestants + tab_user[g].getReal_bit_restant_paquet();
					bit_restant=0;
				}
				
				/*System.out.println("apres consommation");
				//listage du contenue des paquets
				for(j=0; j<15;j++){
					System.out.println("utilisateur: "+j+" bit en trop "+tab_user[j].getBit_en_trop()+" bit restant dans paquet "+tab_user[j].getbit_restant_paquet());
					packet = tab_user[j].getLePaquet();
					System.out.print(packet.getBitsRestants()+"("+(actualTime-packet.getDateCreation())+")=>");
					while(packet.getNextPaquet() != null){
						//System.out.println("test3");
						packet = packet.getNextPaquet();
						System.out.print(packet.getBitsRestants()+"("+(actualTime-packet.getDateCreation())+")=>");
					}
					System.out.println();
				}*/
				
				//+2 car on a 5 time slot et qu'on a dit que sa repr�senter 2ms
				actualTime += 2;
	
	
				
	
				
			}
			//parcour des utilisateurs
			for(j=0; j<15;j++){
				/*Si il reste des packet non envoyer --> R�cup�ration des d�lais et nb de paquets restants dans les paquets non envoyes */
				if(tab_user[j].getLePaquet().getBitsRestants() != 0){
					packet = tab_user[j].getLePaquet();
					while(packet.getNextPaquet() != null){
						//System.out.println("test3");
						packet = packet.getNextPaquet();
						nbPaquetsNonEnvoyes++;
					}
						/* Stats globales */
						/*sommeDelais += (monAntenne.actualTime - tmpPacket->dateCreation);*/
				}
				
				/* R�cup�ration des delais et paquets enregistr�s */
				sommeDelais += tab_user[j].getSommeDelais();
				nbPaquetsTotal += tab_user[j].getSommePaquet();
				nbPaquetsTotalPDOR+= tab_user[j].getSommeDelaisPDOR();
				nbPaquetsTotalsommePaquets_consommer += tab_user[j].getSommePaquets_consommer();
				user_sommeUR += tab_user[j].getSommeUR();
				tab_delais_moyen_user[j] = tab_user[j].getSommeDelais()/tab_user[j].getSommePaquets_consommer();
			}
			
			bit_par_UR = debitTotal/((double)user_sommeUR);
			taux_remplissage_buffer = somme_bitsRestants/actualTime;
			res_sommeUR = ((double)(user_sommeUR)/(double)(5*128*nb_tours))*100;
			debit_total_simu = debitTotal/actualTime;
			debit_total_simu_user = debit_user/actualTime;
			debit_total_simu_relayer = debit_relayer/actualTime;
			delais_moyen = sommeDelais/(nbPaquetsTotalsommePaquets_consommer);
			PDOR=((double)nbPaquetsTotalPDOR/((double)(nbPaquetsTotalsommePaquets_consommer)))*100;
			if(nbPaquetsTotalsommePaquets_consommer == 0){
				PDOR = 100;
			}
			
			System.out.println("nbPaquetsNonEnvoyes : "+nbPaquetsNonEnvoyes);
			System.out.println("nbPaquetsEnvoyes : "+nbPaquetsTotalsommePaquets_consommer );
			System.out.println("NonEnvoyes+Envoyes : "+(nbPaquetsNonEnvoyes+nbPaquetsTotalsommePaquets_consommer)+" || nbPaquetsTotal reel : "+nbPaquetsTotal);
			System.out.println("Pourcentage de bande passante utilis� : "+res_sommeUR);
			System.out.println("Bit par Unit� de ressource : "+bit_par_UR);
			System.out.println("somme_bitsRestants/le temps : "+taux_remplissage_buffer);
			System.out.println("% utilisation des UR par USER [0]: "+(tab_user[0].getSommeUR()/user_sommeUR)*100+" [1]: "+(tab_user[1].getSommeUR()/user_sommeUR)*100+" [2]: "+(tab_user[2].getSommeUR()/user_sommeUR)*100+" [3]: "+(tab_user[3].getSommeUR()/user_sommeUR)*100+" [4]: "+(tab_user[4].getSommeUR()/user_sommeUR)*100+" [5]: "
			+(tab_user[5].getSommeUR()/user_sommeUR)*100+" [6]: "+(tab_user[6].getSommeUR()/user_sommeUR)*100+" [7]: "+(tab_user[7].getSommeUR()/user_sommeUR)*100+" [8]: "+(tab_user[8].getSommeUR()/user_sommeUR)*100+" [9]: "+(tab_user[9].getSommeUR()/user_sommeUR)*100+" [10]: "+(tab_user[10].getSommeUR()/user_sommeUR)*100+" [11]: "
			+(tab_user[11].getSommeUR()/user_sommeUR)*100+" [12]: "+(tab_user[12].getSommeUR()/user_sommeUR)*100+" [13]: "+(tab_user[13].getSommeUR()/user_sommeUR)*100+" [14]: "+(tab_user[14].getSommeUR()/user_sommeUR)*100);
			System.out.println("PDOR : "+PDOR+" [0]: "+tab_user[0].getPDOR()*100+" [1]: "+tab_user[1].getPDOR()*100+" [2]: "+tab_user[2].getPDOR()*100+" [3]: "+tab_user[3].getPDOR()*100+" [4]: "+tab_user[4].getPDOR()*100+" [5]: "
			+tab_user[5].getPDOR()*100+" [6]: "+tab_user[6].getPDOR()*100+" [7]: "+tab_user[7].getPDOR()*100+" [8]: "+tab_user[8].getPDOR()*100+" [9]: "+tab_user[9].getPDOR()*100+" [10]: "+tab_user[10].getPDOR()*100+" [11]: "
			+tab_user[11].getPDOR()*100+" [12]: "+tab_user[12].getPDOR()*100+" [13]: "+tab_user[13].getPDOR()*100+" [14]: "+tab_user[14].getPDOR()*100);
			System.out.println("Delais par User [0]: "+tab_delais_moyen_user[0]+" [1]: "+tab_delais_moyen_user[1]+" [2]: "+tab_delais_moyen_user[2]+" [3]: "+tab_delais_moyen_user[3]+" [4]: "+tab_delais_moyen_user[4]+" [5]: "+tab_delais_moyen_user[5]
			+" [6]: "+tab_delais_moyen_user[6]+" [7]: "+tab_delais_moyen_user[7]+" [8]: "+tab_delais_moyen_user[8]+" [9]: "+tab_delais_moyen_user[9]+" [10]: "+tab_delais_moyen_user[10]
			+" [11]: "+tab_delais_moyen_user[11]+" [12]: "+tab_delais_moyen_user[12]+" [13]: "+tab_delais_moyen_user[13]+" [14]: "+tab_delais_moyen_user[14]);
			System.out.println("Nombre total de Bits genere : "+total_nbBitsgenere+" bits || consommer : "+debitTotal+" bits || user : "+debit_user+" bits || relayer : "+debit_relayer+" bits" );
			System.out.println("D�bit total de la simulation: "+debit_total_simu+" bits/ms || bit genere : "+nb_bit_moy_genere);
			System.out.println("D�bit user: "+debit_total_simu_user+" bits/ms || D�bit relayer: "+debit_total_simu_relayer);
			System.out.println("Delai moyen : "+delais_moyen+" ms  || Somme des delais: "+sommeDelais+" ms");
			System.out.println("");
			
			try {
				fw = new FileWriter (fichier, true);
				bw = new BufferedWriter (fw);
				fichierSortie = new PrintWriter (bw); 
				//on �crit dans le fichier les resultat obtenue
				fichierSortie.println (nb_bit_moy_genere+";"+debit_total_simu+";"+debit_total_simu_user+";"+debit_total_simu_relayer+";"+total_nbBitsgenere+";"+debitTotal+";"+delais_moyen+";"+PDOR+";"+res_sommeUR+";"+bit_par_UR+";"+taux_remplissage_buffer+";"+nbPaquetsTotalsommePaquets_consommer+";"); 
				
				fichierSortie.close();
			}catch (Exception e){
				System.out.println(e.toString());
			}
			
			try {
				fwPDOR = new FileWriter (fichierPDOR, true);
				bwPDOR = new BufferedWriter (fwPDOR);
				fichierSortiePDOR = new PrintWriter (bwPDOR); 
				//on �crit dans le fichier les resultat obtenue
				fichierSortiePDOR.println (nb_bit_moy_genere+";"+tab_user[0].getPDOR()*100+";"+tab_user[1].getPDOR()*100+";"+tab_user[2].getPDOR()*100+";"+tab_user[3].getPDOR()*100+";"+tab_user[4].getPDOR()*100+";"
				+tab_user[5].getPDOR()*100+";"+tab_user[6].getPDOR()*100+";"+tab_user[7].getPDOR()*100+";"+tab_user[8].getPDOR()*100+";"+tab_user[9].getPDOR()*100+";"+tab_user[10].getPDOR()*100+";"
				+tab_user[11].getPDOR()*100+";"+tab_user[12].getPDOR()*100+";"+tab_user[13].getPDOR()*100+";"+tab_user[14].getPDOR()*100+";;"+(tab_user[0].getSommeUR()/user_sommeUR)*100+";"+(tab_user[1].getSommeUR()/user_sommeUR)*100+";"+(tab_user[2].getSommeUR()/user_sommeUR)*100+";"
				+(tab_user[3].getSommeUR()/user_sommeUR)*100+";"+(tab_user[4].getSommeUR()/user_sommeUR)*100+";"+(tab_user[5].getSommeUR()/user_sommeUR)*100+";"+(tab_user[6].getSommeUR()/user_sommeUR)*100+";"+(tab_user[7].getSommeUR()/user_sommeUR)*100+";"
				+(tab_user[8].getSommeUR()/user_sommeUR)*100+";"+(tab_user[9].getSommeUR()/user_sommeUR)*100+";"+(tab_user[10].getSommeUR()/user_sommeUR)*100+";"+(tab_user[11].getSommeUR()/user_sommeUR)*100+";"+(tab_user[12].getSommeUR()/user_sommeUR)*100+";"
				+(tab_user[13].getSommeUR()/user_sommeUR)*100+";"+(tab_user[14].getSommeUR()/user_sommeUR)*100+";;"+tab_delais_moyen_user[0]+";"+tab_delais_moyen_user[1]+";"+tab_delais_moyen_user[2]+";"+tab_delais_moyen_user[3]+";"+tab_delais_moyen_user[4]+";"
				+tab_delais_moyen_user[5]+";"+tab_delais_moyen_user[6]+";"+tab_delais_moyen_user[7]+";"+tab_delais_moyen_user[8]+";"+tab_delais_moyen_user[9]+";"+tab_delais_moyen_user[10]+";"+tab_delais_moyen_user[11]+";"+tab_delais_moyen_user[12]+";"
				+tab_delais_moyen_user[13]+";"+tab_delais_moyen_user[14]+";"); 
				fichierSortiePDOR.close();
			}catch (Exception e){
				System.out.println(e.toString());
			}

			/*if(delais_moyen > 2000){
				//on incr�mente le nb de bit qu'on va g�n�rer au prochain tour
				nb_bit_moy_genere = nb_bit_moy_genere + 10;
			}else{*/
				//on incr�mente le nb de bit qu'on va g�n�rer au prochain tour
				nb_bit_moy_genere = nb_bit_moy_genere + 2;
				
			//}
			
			
			
			//on r�initialise toute les variables
			debit[0] = 0;
			debit[1] = 0;
			debit[2] = 0;
			debitTotal = 0;
			debit_user = 0;
			debit_relayer = 0;
			debit_total_simu_user = 0;
			debit_total_simu_relayer = 0;
			nbBitsgenere = 0;
			debit_total_simu = 0;
			total_nbBitsgenere = 0;
			sommeDelais = 0 ;
			nbPaquetsTotal = 0 ; 
			nbPaquetsTotalPDOR = 0 ;
			nbPaquetsTotalsommePaquets_consommer = 0 ;
			delais_moyen = 0;
			PDOR = 0;
			user_sommeUR = 0;
			nbPaquetsNonEnvoyes = 0;
			res_sommeUR = 0;
			bit_par_UR = 0;
			taux_remplissage_buffer = 0;
			somme_bitsRestants = 0;
			
			actualTime = 0;
			
			tab_user[0] = new User(100, 80, 6);
			tab_user[1] = new User(100, 250, 6);
			tab_user[2] = new User(100, 1000, 6);
		
			tab_user[3] = new User(125, 80, 6);
			tab_user[4] = new User(125, 250, 6);
			tab_user[5] = new User(125, 1000, 6);
			
			tab_user[6] = new User(150, 80, 6);
			tab_user[7] = new User(150, 250, 6);
			tab_user[8] = new User(150, 1000, 6);
			
			tab_user[9] = new User(175, 80, 6);
			tab_user[10] = new User(175, 250, 6);
			tab_user[11] = new User(175, 1000, 6);
			
			tab_user[12] = new User(200, 80, 6);
			tab_user[13] = new User(200, 250, 6);
			tab_user[14] = new User(200, 1000, 6);
			
		}
		
		System.out.println("Simulation terminer");
	}

	
	
	static void initMatriceDebits(User user[]){
		int SNRActuels[] = new int[128];
		int j = 0;
		int i = 0;
		double mkn = 0;
		int d = 1;
		double alpha = 0;
		double puissance = 222;
		MRG32k3a mrg = new MRG32k3a();
		double somme= 0;
		//parcour des utilisateurs
		for(i = 0; i<15; i++){
			for(j = 0; j<128; j++){//parcour des subcarrier. On parcour pas les timeslots car on consid�re que le mkn ne varie pas sur un si petit laps de temps. Donc oui les Times slot ne serve a rien.
				//System.out.println("test3 = "+mrg.rand());
				//formule du mkn demander a c�dric pour plus de d�tail moi je m'en rapelle plus. Mais en gros sa prend en compte la distance, la puissance, sa g�nre des log etc
				alpha =((-1 / 1) *(Math.log( 1 - mrg.rand())));/*alpha = 1 en moyenne*/
				mkn=1+(((puissance)*alpha)/(d*d));
				//System.out.println("test4 = "+((int)((Math.log(mkn)/Math.log(2))-0.5)));
				/*-0.5 pour et (int) pour convertir a l'arrondie inf�rieur*/
				SNRActuels[j] = ((int)((Math.log(mkn)/Math.log(2))-0.5));// sa fait 6 en moyenne
	
				//somme = somme + SNRActuels[j];
				//System.out.println("SNRActuels sur UR"+j+" = "+SNRActuels[j]);
				user[i].setSNRSubcarrier_case(SNRActuels, j);
			}
			
			//System.out.println("moyenne = "+somme/128);
		}
	}

	
}
