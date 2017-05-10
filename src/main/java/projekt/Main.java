package projekt;

import projekt.algorytmy.Wyzarzanie;
import projekt.funkcje.F_kwadrat;
import projekt.funkcje.F_mich;
import projekt.funkcje.F_rast;
import projekt.funkcje.F_swch;

public class Main 
{
    public static void main( String[] args )
    {
    	F_kwadrat f1 = new F_kwadrat();
      	
        Wyzarzanie wyzarzanie1 = new Wyzarzanie(f1, 15000, 0.85,-3, 3, 1500);
        
        System.out.println("Wyzarzanie dla x^2"  );
        wyzarzanie1.wykonaj();

        F_mich f2 = new F_mich();
 //       Wyzarzanie wyzarzanie2 = new Wyzarzanie(f2, 15000, 0.85,0, 3.14, 500);
        
//        System.out.println("Wyzarzanie dla mich"  );
//        wyzarzanie2.wykonaj();
//        
        F_rast f3 = new F_rast();
  //      Wyzarzanie wyzarzanie3 = new Wyzarzanie(f3, 15000, 0.85,0, 3.14, 500);
        
//        System.out.println("Wyzarzanie dla rast"  );
//        wyzarzanie3.wykonaj();
//        
        F_swch f4 = new F_swch();
  //      Wyzarzanie wyzarzanie4 = new Wyzarzanie(f4, 15000, 0.85,0, 3.14, 500);
        
//        System.out.println("Wyzarzanie dla swch"  );
//        wyzarzanie4.wykonaj();
    }
}
