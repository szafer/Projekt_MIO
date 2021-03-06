package pl.edu.us.mio.projekt;

import pl.edu.us.mio.projekt.algorytmy.Wyzarzanie;
import pl.edu.us.mio.projekt.funkcje.F_mich;
import pl.edu.us.mio.projekt.funkcje.F_rast;
import pl.edu.us.mio.projekt.funkcje.F_swch;
import pl.edu.us.mio.projekt.funkcje.FunkcjaKwadratowa;
/**
 * Klasa uruchamiajaca wersję konsolowa - głównie do testu
 * @author dexter
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
     	FunkcjaKwadratowa funKwadrat = new FunkcjaKwadratowa();
    	F_mich funMichalewicz = new F_mich();
    	F_rast funRastrigin = new F_rast();
        F_swch funSchwefel = new F_swch();

		double smich = funMichalewicz.wykonaj(2.20, 1.57); // -1.8011407193823923   wg przykładu -1.8013
		double smich1 = funMichalewicz.wykonaj(2.20, 2.20);  
		double srast = funRastrigin.wykonaj(0, 0);  //  wg przykładu wynik = 0 dla 0,0
		double srast1 = funRastrigin.wykonaj(2, 1);
		double srast2 = funRastrigin.wykonaj(1, 2.5);
		double swch = funSchwefel.wykonaj(420.9687, 420.9687); //  wg przykładu wynik =0 dla  420.9687, 420.9687
		double swch1 = funSchwefel.wykonaj(420.9616, 420.9752);
//		 double swch1 = funkcja.wykonaj(0,0);
      	
        Wyzarzanie wyzarzanie1 = new Wyzarzanie(funKwadrat, 15000, 0.85,-3, 3, 10000);
        Wyzarzanie wyzarzanie2 = new Wyzarzanie(funMichalewicz, 350000, 0.89, 0, 3.14, 200000);
        Wyzarzanie wyzarzanie3 = new Wyzarzanie(funRastrigin, 3500000, 0.93, -5.12, 5.12, 350000); 
        Wyzarzanie wyzarzanie4 = new Wyzarzanie(funSchwefel, 3500000, 0.95, -500, 500, 350000);

//        System.out.println("Wyzarzanie dla x^2"  );
//        wyzarzanie1.wykonaj(1);

        
//        
//        System.out.println("Wyzarzanie dla mich"  );
//        wyzarzanie2.wykonaj();
        
////        
//        System.out.println("Wyzarzanie dla rast"  );
//        wyzarzanie3.wykonaj();
//        
//        
        System.out.println("Wyzarzanie dla swch"  );
        wyzarzanie4.wykonaj();
    }
}
