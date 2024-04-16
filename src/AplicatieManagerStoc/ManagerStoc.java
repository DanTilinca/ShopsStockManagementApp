package AplicatieManagerStoc;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clasa pentru gestionarea stocurilor magazinului
 * @author Tilinca Dan-Cristian
 */
public class ManagerStoc {
	
	private ArrayList<Articol> articole;
	private static final String URL = "jdbc:mysql://localhost:3306/managementstoc";
    private static final String USER = "root";
    private static final String PASSWORD = "";
	
    private Connection connection;
    
    private DatabaseManager databaseManager = new DatabaseManager();
    
	/**
	 * Constructor fara parametrii pentru initializarea
	 * unui sistem de management al stocului de articole
	 */
	public ManagerStoc() {
		this.articole = new ArrayList<Articol>();
		
		try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Metoda pentru adaugarea unui articol in stoc
	 * 
	 * @param articol Articolul care urmeaza sa fie adaugat in stoc
	 */
	public void adaugaArticol(Articol articol) {
		try {
	        // Verifică dacă categoria există deja în tabela "categorii"
	        if (!existaCategorie(articol.getCategorie())) {
	            // Dacă nu există, adaugă o nouă înregistrare în tabela "categorii"
	            adaugaCategorie(articol.getCategorie(), 1);
	        } else {
	            // Dacă există, doar adaugă articolul în tabela "categorii"
	            actualizeazaNrProduseCategorie(articol.getCategorie(), 1);
	        }

	        // Adaugă articolul în baza de date
	        String insertQuery = "INSERT INTO articol (denumire, categorie, cantitate) VALUES (?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            preparedStatement.setString(1, articol.getDenumire());
	            preparedStatement.setString(2, articol.getCategorie());
	            preparedStatement.setInt(3, articol.getCantitate());
	            preparedStatement.executeUpdate();
	        }

	        // Adaugă articolul în lista locală
	        this.articole.add(articol);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Metoda pentru actualizarea cantitatii unui articol
	 * 
	 * @param denumire Denumirea articolului
	 * @param cantitateNoua Noua cantitate a articolului
	 */
	public void actualizeazaCantitate(String denumire, int cantitateNoua) {
		try {
            // Actualizează cantitatea în baza de date
            String updateQuery = "UPDATE articol SET cantitate = ? WHERE denumire = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, cantitateNoua);
                preparedStatement.setString(2, denumire);
                preparedStatement.executeUpdate();
            }

            // Actualizează cantitatea în lista locală
            for (Articol articol : articole) {
                if (articol.getDenumire().equals(denumire)) {
                    articol.setCantitate(cantitateNoua);
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Metoda pentru stergerea unui articol din stoc
	 * 
	 * @param denumire Denumirea articolului care va fi sters
	 */
    public void stergeArticol(String denumire) {
    	try {
            // Obține categoria articolului pentru a actualiza tabela "categorii"
            String categorie = getCategorieByDenumire(denumire);

            // Șterge articolul din baza de date
            String deleteQuery = "DELETE FROM articol WHERE denumire = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, denumire);
                preparedStatement.executeUpdate();
            }

            // Șterge articolul din tabela "categorii"
            actualizeazaNrProduseCategorie(categorie, -1);

            // Șterge articolul din lista locală
            articole.removeIf(articol -> articol.getDenumire().equals(denumire));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metoda pentru cautarea dupa denumire sau categorie
     * 
     * @param criteriu Criteriul de cautare (denumire sau categorie)
     * @return Lista de articole care corespund criteriului de cautare
     */
    public ArrayList<Articol> cauta(String criteriu) {
    	ArrayList<Articol> rezultate = new ArrayList<>();
        try {
            // Caută în baza de date
            String searchQuery = "SELECT * FROM articol WHERE denumire LIKE ? OR categorie LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
                preparedStatement.setString(1, "%" + criteriu + "%");
                preparedStatement.setString(2, "%" + criteriu + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String denumire = resultSet.getString("denumire");
                        String categorie = resultSet.getString("categorie");
                        int cantitate = resultSet.getInt("cantitate");

                        Articol articol = new Articol(denumire, categorie, cantitate);
                        rezultate.add(articol);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultate;
    }

    /**
     * Metoda pentru afisarea tuturor articolelor din stoc
     * @return Lista tuturor articolelor din stoc
     */
    public ArrayList<Articol> afiseazaToateArticolele() {
    	ArrayList<Articol> rezultate = new ArrayList<>();
        try {
            // Afișează toate articolele din baza de date
            String selectAllQuery = "SELECT * FROM articol";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String denumire = resultSet.getString("denumire");
                    String categorie = resultSet.getString("categorie");
                    int cantitate = resultSet.getInt("cantitate");

                    Articol articol = new Articol(denumire, categorie, cantitate);
                    rezultate.add(articol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultate;
    }

    /**
     * Metoda pentru afisarea articolelor cu o cantitate scazuta
     * 
     * @param limita Limita Cantitatea minima pentru a fi considerata scazuta
     * @return Lista articolelor cu cantitate scazuta
     */
    public ArrayList<Articol> afiseazaArticoleCuCantitateScazuta(int limita) {
    	ArrayList<Articol> rezultate = new ArrayList<>();
        try {
            // Afișează articolele cu cantitate scăzută din baza de date
            String lowQuantityQuery = "SELECT * FROM nume_tabela WHERE cantitate < ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(lowQuantityQuery)) {
                preparedStatement.setInt(1, limita);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String denumire = resultSet.getString("denumire");
                        String categorie = resultSet.getString("categorie");
                        int cantitate = resultSet.getInt("cantitate");

                        Articol articol = new Articol(denumire, categorie, cantitate);
                        rezultate.add(articol);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezultate;
    }
    
    
    // Metoda pentru a verifica dacă o categorie există deja în tabela "categorii"
    private boolean existaCategorie(String categorie) throws SQLException {
        String selectQuery = "SELECT * FROM categorii WHERE denumire = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, categorie);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Metoda pentru a adăuga o nouă categorie în tabela "categorii"
    private void adaugaCategorie(String categorie, int nrProduse) throws SQLException {
        String insertQuery = "INSERT INTO categorii (denumire, nrProduse) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, categorie);
            preparedStatement.setInt(2, nrProduse); // Setează numărul inițial de produse la 1
            preparedStatement.executeUpdate();
        }
    }
    
 // Metoda pentru a actualiza numărul de produse într-o categorie și șterge categoria dacă este ultimul articol
    private void actualizeazaNrProduseCategorie(String categorie, int modificare) throws SQLException {
        String selectQuery = "SELECT nrProduse FROM categorii WHERE denumire = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, categorie);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int nrProduseCategorie = resultSet.getInt("nrProduse") + modificare;
                    if (nrProduseCategorie <= 0) {
                        // Dacă este ultimul articol, șterge categoria din tabela "categorii"
                        stergeCategorie(categorie);
                    } else {
                        // Altfel, actualizează numărul de produse în tabela "categorii"
                        String updateQuery = "UPDATE categorii SET nrProduse = ? WHERE denumire = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, nrProduseCategorie);
                            updateStatement.setString(2, categorie);
                            updateStatement.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    // Metoda pentru a șterge o categorie din tabela "categorii"
    private void stergeCategorie(String categorie) throws SQLException {
        String deleteQuery = "DELETE FROM categorii WHERE denumire = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, categorie);
            preparedStatement.executeUpdate();
        }
    }

    
    // Metoda pentru a obține categoria unui articol după denumire
    private String getCategorieByDenumire(String denumire) throws SQLException {
        String selectQuery = "SELECT categorie FROM articol WHERE denumire = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, denumire);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("categorie");
                }
            }
        }
        return null; // Sau aruncă o excepție sau întoarce o valoare semnificativă în funcție de necesități
    }
    
    public ArrayList<Furnizor> getTotiFurnizorii() {
        return databaseManager.getTotiFurnizorii();
    }

}
