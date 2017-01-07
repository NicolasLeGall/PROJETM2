import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int nb_tours;
		int choixAlgo;
		int i, j;
		int actualTime = 0;
		Algorithme scheduler = new Algorithme();
		Bit gestion_de_bit = new Bit();
		Scanner scanInt = new Scanner(System.in);
		User tab_user[] = new User[15];
		
		tab_user[0] = new User(0, 80);
		tab_user[1] = new User(0, 250);
		tab_user[2] = new User(0, 1000);
	
		tab_user[3] = new User(25, 80);
		tab_user[4] = new User(25, 250);
		tab_user[5] = new User(25, 1000);
		
		tab_user[6] = new User(50, 80);
		tab_user[7] = new User(50, 250);
		tab_user[8] = new User(50, 1000);
		
		tab_user[9] = new User(75, 80);
		tab_user[10] = new User(75, 250);
		tab_user[11] = new User(75, 1000);
		
		tab_user[12] = new User(100, 80);
		tab_user[13] = new User(100, 250);
		tab_user[14] = new User(100, 1000);
		
		double debitTotal = 0;
		double nbBitsgenere = 0;
		double debit_total_simu = 0;
		double total_nbBitsgenere = 0;
		double sommeDelais = 0 ;
		double nbPaquetsTotal = 0 ; 
		double nbPaquetsTotalPDOR = 0 ;
		double nbPaquetsTotalsommePaquets_consommer = 0 ;
		double delais_moyen = 0;
		double PDOR = 0;
		
		//initMatriceDebits();
		
		System.out.println("Nombre de tours pour la simulation, chaque tour = 2ms: ");
		nb_tours = scanInt.nextInt();          
		//System.out.println("nombre de tour "+nb_tours);
		System.out.println("Algorithme : 1 pour RR, 2 pour MAXSNR, 3 pour WFO, 4 pour CEI, 5 pour CEI+WFO :");
		choixAlgo = scanInt.nextInt();
		//System.out.println("choix algo "+choixAlgo);
		scanInt.close(); 
		
		
		String fichier ="Resultat.csv";
		try {
			FileWriter fw = new FileWriter (fichier);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println ("nb_tours="+nb_tours+";choixAlgo="+choixAlgo+";\n"); 
			
			fichierSortie.close();
		}catch (Exception e){
			System.out.println(e.toString());
		}	
		
		
		
		
		Paquet packet = new Paquet(0, 0, null);
		
		for(i = 0; i < nb_tours; i++){
			/*On donne a chaque utilisateur un débit pour les 128 subcarrieur qui varie de 0 à 10 et qui a pour moyenne sa distance de l'antenne*/
			initMatriceDebits(tab_user);	
			
			/*Initialisation des paquets utilisateurs*/
			/*Le temps de création d'un packet est donnée a chaque packet avec monAntenne.actualTime */
			
			nbBitsgenere = gestion_de_bit.produceBit(tab_user, actualTime);
			total_nbBitsgenere = total_nbBitsgenere + nbBitsgenere;
			
			
		/*	//listage du contenue des paquets
			for(j=0; j<15;j++){
				System.out.println("utilisateur: "+j+" bit en trop "+tab_user[j].getBit_en_trop());
				packet = tab_user[j].getLePaquet();
				System.out.print(packet.getBitsRestants()+"=>");
				while(packet.getNextPaquet() != null){
					//System.out.println("test3");
					packet = packet.getNextPaquet();
					System.out.print(packet.getBitsRestants()+"=>");
				}
				System.out.println();
			}
			
			System.out.println();
				*/

			/*Application de l'algorithme et ôtage des bits envoyés avec maxSNR*/
			if(choixAlgo == 1){
				debitTotal += scheduler.RR(tab_user, actualTime);
			}
			else if(choixAlgo == 2){
				debitTotal += scheduler.maxSNR(tab_user, actualTime);
			}
			else if(choixAlgo == 3){
				debitTotal += scheduler.WFO(tab_user, actualTime);
			}
			else if(choixAlgo == 4){
				debitTotal += scheduler.CEI(tab_user, actualTime);
			}
			else if(choixAlgo == 5){
				debitTotal += scheduler.CEI_WFO(tab_user, actualTime);
			}
			else{
				System.out.println("choix de l'algorithme mauvais. Arret. \n");
			}
		
			//+2 car on a 5 time slot et qu'on a dit que sa représenter 2secondes
			actualTime += 2;


			/*for(j=0; j<15;j++){
				System.out.println("utilisateur: "+j+" bit en trop "+tab_user[j].getBit_en_trop());
				packet = tab_user[j].getLePaquet();
				System.out.print(packet.getBitsRestants()+"=>");
				while(packet.getNextPaquet() != null){
					//System.out.println("test3");
					packet = packet.getNextPaquet();
					System.out.print(packet.getBitsRestants()+"=>");
				}
				System.out.println();
			}*/
			
		}
		//parcour des utilisateurs
		for(j=0; j<15;j++){
		
			/* Récupération des delais et paquets enregistrés */
			sommeDelais += tab_user[j].getSommeDelais();
			nbPaquetsTotal += tab_user[j].getSommePaquet();
			nbPaquetsTotalPDOR+= tab_user[j].getSommeDelaisPDOR();
			nbPaquetsTotalsommePaquets_consommer += tab_user[j].getSommePaquets_consommer();
			//user_sommeUR = user_sommeUR + tab_user[j]->sommeUR;
		}
		
		
		
		debit_total_simu = debitTotal/actualTime;
		delais_moyen = sommeDelais/(nbPaquetsTotalsommePaquets_consommer);
		PDOR=((double)nbPaquetsTotalPDOR/((double)(nbPaquetsTotalsommePaquets_consommer)))*100;
		if(nbPaquetsTotalsommePaquets_consommer == 0){
			PDOR = 100;
		}
		
		
		
		System.out.println("Nombre total de Bits genere : "+total_nbBitsgenere+" bits || consommer : "+debitTotal+" bits");
		System.out.println("Débit total de la simulation: "+debit_total_simu+" bits/ms || Somme des delais: "+sommeDelais+" ms");
		System.out.println("Delai moyen : "+delais_moyen+" ms");
		System.out.println("PDOR : "+PDOR+" Ce resultat veux rien dire pour l'instant car il ont outs un seuil de PDOR different");
		System.out.println("Fin de la simulation");
		
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
		for(i = 0; i<15; i++){
			for(j = 0; j<128; j++){
				//System.out.println("test3 = "+mrg.rand());
				alpha =((-1 / 1) *(Math.log( 1 - mrg.rand())));/*alpha = 1 en moyenne*/
				mkn=1+(((puissance)*alpha)/(d*d));
				//System.out.println("test4 = "+((int)((Math.log(mkn)/Math.log(2))-0.5)));
				/*-0.5 pour et (int) pour convertir a l'arrondie inférieur*/
				SNRActuels[j] = ((int)((Math.log(mkn)/Math.log(2))-0.5));// sa fait 6 en moyenne
	
				somme = somme + SNRActuels[j];
				//System.out.println("SNRActuels sur UR"+j+" = "+SNRActuels[j]);
			}
			user[i].setSNRSubcarrier(SNRActuels);
			//System.out.println("moyenne = "+somme/128);
		}
	}

	
}
