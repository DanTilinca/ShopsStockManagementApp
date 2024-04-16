package AplicatieManagerStoc;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/managementstoc";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaCredentiale(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            // Verifică dacă există un utilizator cu aceste credențiale
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            boolean found = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return found;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void adaugaUtilizator(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            // Adaugă un nou utilizator în tabel
            String insertQuery = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
            statement.executeUpdate(insertQuery);

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Furnizor> getTotiFurnizorii() {
        ArrayList<Furnizor> furnizori = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();

            // Selectează toți furnizorii din tabel
            String query = "SELECT * FROM furnizori";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Furnizor furnizor = new Furnizor();
                furnizor.setNumeFurnizor(resultSet.getString("numeFurnizor"));
                furnizor.setAdresa(resultSet.getString("adresa"));
                furnizor.setTelefon(resultSet.getString("telefon"));
                furnizor.setEmail(resultSet.getString("email"));
                furnizor.setDataInregistrare(resultSet.getDate("dataInregistrare"));

                furnizori.add(furnizor);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return furnizori;
    }
}