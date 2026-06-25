package RozbudowaneStrukturyJava.SQLLite;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Database {

    private final String URL;


    public Database(String path) {
        this.URL = "jdbc:sqlite:" + path;
    }

    public void szkielet() {
        String sql = "SELECT * FROM users";
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    //doSomething
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
            //ResultSet ma metodę .next(), która zwraca true, jeśli jest następny obiekt, czyli upraszczając wtedy, jeśli znajdzie taki rekord
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;//musi być, gdyby był jakiś błąd
    }

    public void doubleQuery(String x, String y) {
        String statementWinner = "UPDATE users SET points = points + 1 WHERE login = ?";
        String statementLoser = "UPDATE users SET points = points - 1 WHERE login = ?";


        try (Connection connection = DriverManager.getConnection(URL)) {

            connection.setAutoCommit(false);
            //robimy coś takiego po to, bo robimy dwa update i nie chcemy sytuacji, że jeden wynik zapiszemy, a drugiego nie z powodu błędu
            //chcemy, żeby nasze operacje miały atomowy charakter
            try (PreparedStatement preparedStatementWinner = connection.prepareStatement(statementWinner);
                 PreparedStatement preparedStatementLoser = connection.prepareStatement(statementLoser)){


                preparedStatementWinner.setString(1, x);
                preparedStatementWinner.executeUpdate();


                preparedStatementLoser.setString(1, y);
                preparedStatementLoser.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                //cofnięcie zmian
            }

        } catch (SQLException e) {
            System.err.println("Bląd polaczenia");
        }
    }

    public Map<String, Integer> getLeaderboard() {
        String sql_leaderboard = "SELECT login, points FROM users ORDER BY points DESC";
        Map<String,Integer>toReturn=new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement stmt_leaderboard = connection.prepareStatement(sql_leaderboard);
             ResultSet rs = stmt_leaderboard.executeQuery()){
            while (rs.next()) {
                String login=rs.getString("login");
                Integer points=rs.getInt("points");
                toReturn.put(login,points);
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return toReturn;
    }

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    points INTEGER DEFAULT 0
                );
                """;



        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.execute();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public boolean insertUser(String login, String password) {

        String sql = "INSERT INTO users (login, password, points) VALUES (?, ?, 0)";

        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Tak na wszelki wypadek, jakby mu odwaliło coś
    public int insertUserAndGetId(String login, String password) {
        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";

        // KLUCZOWE: Musimy dopisać Statement.RETURN_GENERATED_KEYS w argumencie!
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            // Przechwytujemy automatycznie wygenerowane klucze
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Zwraca wygenerowane ID (pierwsza kolumna wyniku)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Sygnał błędu
    }
}

