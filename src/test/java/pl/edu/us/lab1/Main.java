package pl.edu.us.lab1;

import pl.edu.us.mio.projekt.algorytmy.Dychotomia;
import pl.edu.us.mio.projekt.algorytmy.ZlotyPodzial;
import pl.edu.us.mio.projekt.funkcje.Funkcja2;
import pl.edu.us.mio.projekt.funkcje.Funkcja3;
import pl.edu.us.mio.projekt.funkcje.FunkcjaKwadratowa;
/**
 * Metody inteligencji obliczeniowej
 * Zadanie na zaliczenie Labratorium 1
 * @author dexter
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	FunkcjaKwadratowa f1 = new FunkcjaKwadratowa();
    	Funkcja2 f2 = new Funkcja2();
    	Funkcja3 f3 = new Funkcja3();
    	
        Dychotomia dychotomia1 = new Dychotomia(-3, 3, f1);
        Dychotomia dychotomia2 = new Dychotomia(-10, 10, f2);
        Dychotomia dychotomia3 = new Dychotomia(-1, 1, f3);
        
        System.out.println("Dychotomia f(x^2) dla x z przedzialu [-3; 3]");
        dychotomia1.wykonaj();
        System.out.println("\nDychotomia f(x - 5)^2 dla x z przedzialu [-10; 10]");
        dychotomia2.wykonaj();
        System.out.println("\nDychotomia f(x^3 - x) dla x z przedzialu [-1; 1]");
        dychotomia3.wykonaj();
        

        ZlotyPodzial zlotyPodzial1 = new ZlotyPodzial(-3, 3, f1);
        ZlotyPodzial zlotyPodzial2 = new ZlotyPodzial(-10, 10, f2);
        ZlotyPodzial zlotyPodzial3 = new ZlotyPodzial(-1, 1, f3);

        System.out.println("\nZłoty podział f(x^2) dla x z przedzialu [-3; 3]");
        zlotyPodzial1.wykonaj();
        System.out.println("\nZłoty podział f(x - 5)^2 dla x z przedzialu [-10; 10]");
        zlotyPodzial2.wykonaj();
        System.out.println("\nZłoty podział f(x^3 - x) dla x z przedzialu [-1; 1]");
        zlotyPodzial3.wykonaj();
    }
}
