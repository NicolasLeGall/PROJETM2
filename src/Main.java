
public class Main {

	public static void main(String[] args) {

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
		
		
		initMatriceDebits();
	}

	static void initMatriceDebits(){
		int SNRActuels[] = new int[128];
		int j = 0;
		double mkn = 0;
		int d = 1;
		double alpha = 0;
		double puissance = 222;
		MRG32k3a mrg = new MRG32k3a();
		double somme= 0;
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
		System.out.println("moyenne = "+somme/128);
	}
	
	

	
	
	
}
