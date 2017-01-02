import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		int nb_tours;
		int choixAlgo;
		int i;
		int actualTime = 0;
		int debitTotal = 0;
		int nbBitsgenere = 0;
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
		
		
		//initMatriceDebits();
		
		System.out.println("Nombre de tours pour la simulation, chaque tour = 2ms: ");
		nb_tours = scanInt.nextInt();          
		//System.out.println("nombre de tour "+nb_tours);
		System.out.println("Algorithme : 1 pour RR, 2 pour MAXSNR, 3 pour WFO, 4 pour CEI, 5 pour CEI+WFO :");
		choixAlgo = scanInt.nextInt();
		//System.out.println("choix algo "+choixAlgo);
		scanInt.close(); 
		
		for(i = 0; i < nb_tours; i++){
			
			/*Initialisation des paquets utilisateurs*/
			/*Le temps de création d'un packet est donnée a chaque packet avec monAntenne.actualTime */
			
			nbBitsgenere = gestion_de_bit.produceBit(tab_user, actualTime);
			//total_nbBitsgenere = total_nbBitsgenere + nbBitsgenere;
			/*On donne a chaque utilisateur un débit pour les 128 subcarrieur qui varie de 0 à 10 et qui a pour moyenne sa distance de l'antenne*/
			initMatriceDebits(tab_user);		

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

		}
		
		
		System.out.println("Fin de la simulation \n");
		
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
