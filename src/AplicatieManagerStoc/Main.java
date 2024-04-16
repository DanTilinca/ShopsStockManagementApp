package AplicatieManagerStoc;

import java.util.ArrayList;

/**
 * Clasa destinata testarii functionalitatii al celorlalte clase
 * de gestionare a stocurilor
 */

public class Main {
	
	/**
	 * Metoda principala pentru exemplul de utilizare
	 * 
	 * @param args Argumentele din linia de comanda (nu sunt folosite in acest caz)
	 */
	
	public static void main(String[] args) {
		
		ManagerStoc managerStoc = new ManagerStoc();

        managerStoc.adaugaArticol(new Articol("Laptop", "Electronice", 10));
        managerStoc.adaugaArticol(new Articol("Telefon", "Electronice", 20));
        managerStoc.adaugaArticol(new Articol("Carti", "Cultura", 50));
        
        
        System.out.println("Afisarea tuturor articolelor:");
        ArrayList<Articol> toateArticolele = managerStoc.afiseazaToateArticolele();
        for (Articol articol : toateArticolele) {
            System.out.println(articol);
        }
        System.out.println("------------------------");

        System.out.println("Afisare dupa actualizare cantitate la 'Laptop'");
        managerStoc.actualizeazaCantitate("Laptop", 5);
        toateArticolele = managerStoc.afiseazaToateArticolele();
        for (Articol articol : toateArticolele) {
            System.out.println(articol);
        }
        System.out.println("------------------------");

        System.out.println("Cautare dupa 'Telefon':");
        ArrayList<Articol> rezultateCautare = managerStoc.cauta("Telefon");
        for (Articol articol : rezultateCautare) {
            System.out.println(articol);
        }
        System.out.println("------------------------");

        System.out.println("Afisare articole cu cantitate scazuta (sub 15):");
        ArrayList<Articol> articoleCantScazuta = managerStoc.afiseazaArticoleCuCantitateScazuta(15);
        for (Articol articol : articoleCantScazuta) {
            System.out.println(articol);
        }
        
        System.out.println("------------------------");

        System.out.println("Afisare articole ramase dupa stergerea lui 'Carti':");
        managerStoc.stergeArticol("Carti");
        toateArticolele = managerStoc.afiseazaToateArticolele();
        for (Articol articol : toateArticolele) {
            System.out.println(articol);
        }
        
        System.out.println("------------------------");
	}

}
