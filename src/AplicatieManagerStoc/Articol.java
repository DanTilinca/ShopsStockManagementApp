package AplicatieManagerStoc;

/**
 * Clasa pentru reprezentarea unui articol din magazin
 */

public class Articol {
	
	private String denumire;
	private String categorie;
	private int cantitate;
	
	/**
	 * Constructor cu parametri pentru un articol
	 * 
	 * @param denumire Denumirea articolului
	 * @param categorie Categoria din care va face parte articolul
	 * @param cantitate Cantitate articolului
	 */
	public Articol(String denumire, String categorie, int cantitate){
		super();
		this.denumire = denumire;
		this.categorie = categorie;
		this.cantitate = cantitate;
	}

	/**
	 * Metoda de acces pentru a obtine denumirea articolului
	 * 
	 * @return denumire Denumirea articolului
	 */
	public String getDenumire() {
		return denumire;
	}
	
	/**
	 * Metoda de acces pentru a obtine categoria articolului
	 * 
	 * @return cateogrie Categoria articolului
	 */
	public String getCategorie() {
		return categorie;
	}
	
	/**
	 * Metoda de acces pentru a obtine cantitatea articolului
	 * 
	 * @return cantitate Cantitatea articolului
	 */
	public int getCantitate() {
		return cantitate;
	}

	/**
	 * Metoda de acces pentru a seta cantitatea articolului
	 * 
	 * @param cantitate Cantitatea articolului
	 */
	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	/**
	 * Metoda pentru afisarea produsului
	 */
	@Override
	public String toString() {
		return "Articol [denumire=" + denumire + ", categorie=" + categorie + ", cantitate=" + cantitate + "]";
	}
}
