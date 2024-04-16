package AplicatieManagerStoc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clasa care contine testele unitare ale aplicatiei
 */

public class ManagerStocTest {

    @Test
    void adaugaArticol() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol = new Articol("Mouse", "Periferice", 15);

        managerStoc.adaugaArticol(articol);

        assertTrue(managerStoc.cauta("Mouse").contains(articol));
    }

    @Test
    void actualizeazaCantitate() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol = new Articol("Tastatura", "Periferice", 25);
        managerStoc.adaugaArticol(articol);

        managerStoc.actualizeazaCantitate("Tastatura", 10);

        assertEquals(10, managerStoc.cauta("Tastatura").get(0).getCantitate());
    }

    @Test
    void stergeArticol() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol = new Articol("Monitor", "Periferice", 30);
        managerStoc.adaugaArticol(articol);

        managerStoc.stergeArticol("Monitor");

        assertFalse(managerStoc.cauta("Monitor").contains(articol));
    }

    @Test
    void cauta() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol1 = new Articol("Tablet", "Electronice", 18);
        Articol articol2 = new Articol("Caiet", "Papetarie", 50);
        managerStoc.adaugaArticol(articol1);
        managerStoc.adaugaArticol(articol2);

        assertEquals(2, managerStoc.cauta("").size());
        assertEquals(1, managerStoc.cauta("Electronice").size());
        assertEquals(1, managerStoc.cauta("Caiet").size());
    }

    @Test
    void afiseazaArticoleCuCantitateScazuta() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol1 = new Articol("Pix", "Papetarie", 8);
        Articol articol2 = new Articol("Hartie", "Papetarie", 15);
        managerStoc.adaugaArticol(articol1);
        managerStoc.adaugaArticol(articol2);

        assertEquals(1, managerStoc.afiseazaArticoleCuCantitateScazuta(10).size());
        assertEquals(2, managerStoc.afiseazaArticoleCuCantitateScazuta(17).size());
    }

    @Test
    void afiseazaToateArticolele() {
        ManagerStoc managerStoc = new ManagerStoc();
        Articol articol1 = new Articol("Cana", "Bucatarie", 12);
        Articol articol2 = new Articol("Farfurie", "Bucatarie", 20);
        managerStoc.adaugaArticol(articol1);
        managerStoc.adaugaArticol(articol2);

        assertEquals(2, managerStoc.afiseazaToateArticolele().size());
    }
}
