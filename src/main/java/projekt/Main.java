package projekt;

import projekt.algorytmy.Wyzarzanie;
import projekt.funkcje.F_Ackley;
import projekt.funkcje.F_Rosenbrocks;
import projekt.funkcje.F_mich;
import projekt.funkcje.F_rast;
import projekt.funkcje.F_swch;
import projekt.funkcje.FunkcjaKwadratowa;

public class Main {
	public static void main(String[] args) {
		FunkcjaKwadratowa funKwadrat = new FunkcjaKwadratowa();
    	F_mich funMichalewicz = new F_mich();
    	F_rast funRastrigin = new F_rast();
        F_swch funSchwefel = new F_swch();
        F_Rosenbrocks funRosen = new F_Rosenbrocks();
        F_Ackley funAckley = new F_Ackley();
		double smich = funMichalewicz.wykonaj(2.20, 1.57); // -1.8011407193823923   wg przykładu -1.8013
		double smich1 = funMichalewicz.wykonaj(2.20, 2.20);  
		double srast = funRastrigin.wykonaj(0, 0);  //  wg przykładu wynik = 0
		double srast1 = funRastrigin.wykonaj(2, 1);
		double srast2 = funRastrigin.wykonaj(1, 2.5);
		double swch = funSchwefel.wykonaj(420.9687, 420.9687); // -837.9658 - ok. 
		double rosen = funRosen.wykonaj(1,1); // powinno być 0
		double ackley = funAckley.wykonaj(0, 0);
		//		 double swch1 = funkcja.wykonaj(0,0);
      	
        Wyzarzanie wyzarzanie1 = new Wyzarzanie(funKwadrat, 15000, 0.85,-3, 3, 100000);
//        Wyzarzanie wyzarzanie2 = new Wyzarzanie(funMichalewicz, 350000, 0.89, 0, 3.14, 300000);
////        Wyzarzanie wyzarzanie3 = new Wyzarzanie(funRastrigin, 350000, 0.92, -1, 1, 300000);
////        Wyzarzanie wyzarzanie4 = new Wyzarzanie(funSchwefel, 350000, 0.85, -500, 500, 550000);
//
//        System.out.println("Wyzarzanie dla x^2"  );
////        wyzarzanie1.wykonaj(1);
//
//        
////        
//        System.out.println("Wyzarzanie dla mich"  );
//        wyzarzanie2.wykonaj();
        
//        
//        System.out.println("Wyzarzanie dla rast"  );
//        wyzarzanie3.wykonaj();
        
//        
//        System.out.println("Wyzarzanie dla swch"  );
//        wyzarzanie4.wykonaj();
	}
}
