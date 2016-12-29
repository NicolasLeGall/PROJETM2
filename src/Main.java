
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello les nuls !!");
		
		/*User user_0_80 = new User(0, 80);
		User user_0_250 = new User(0, 250);
		User user_0_1000 = new User(0, 1000);
		
		User user_25_80 = new User(25, 80);
		User user_25_250 = new User(25, 250);
		User user_25_1000 = new User(25, 1000);
		
		User user_50_80 = new User(50, 80);
		User user_50_250 = new User(50, 250);
		User user_50_1000 = new User(50, 1000);
		
		User user_75_80 = new User(75, 80);
		User user_75_250 = new User(75, 250);
		User user_75_1000 = new User(75, 1000);
		
		User user_100_80 = new User(100, 80);
		User user_100_250 = new User(100, 250);
		User user_100_1000 = new User(100, 1000);*/
		
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
