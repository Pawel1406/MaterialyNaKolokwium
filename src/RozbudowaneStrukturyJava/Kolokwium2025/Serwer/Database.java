package RozbudowaneStrukturyJava.Kolokwium2025.Serwer;

import java.sql.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class Database {
    private String URL;

    public Database(String URL) {
        this.URL = "jdbc:sqlite:"+URL;
    }

    public boolean authenticate(String login, String password) {

       String statement = "SELECT * FROM users WHERE login = ? AND password = ?";
        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement preparedStatement = connection.prepareStatement(statement);){

            //Stosujemy zawsze PreparedStatement, bo jest lepszy
            //Chociaż w tym wypadku trzeba z niego korzystać
            //Dajemy preparedStatement wewnątrz bloku try, żeby nie zamykać go ręcznie.
            //Tak samo robimy z ResultSet


            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }

    }

    public void updateLeaderboard(String winner, String loser) {
        String statementWinner="UPDATE users SET points = points + 1 WHERE login = ?";
        String statementLoser= "UPDATE users SET points = points - 1 WHERE login = ?";


        try(Connection connection=DriverManager.getConnection(URL)){

            connection.setAutoCommit(false);
            //robimy coś takiego po to, bo robimy dwa update i nie chcemy sytuacji że jeden wynik zapiszemy a drugiego nie z powodu błędu
            //chcemy, żeby nasze operacje miały atomowy charakter
            try(PreparedStatement preparedStatementWinner=connection.prepareStatement(statementWinner);
                PreparedStatement preparedStatementLoser= connection.prepareStatement(statementLoser)){


            preparedStatementWinner.setString(1, winner);
            preparedStatementWinner.executeUpdate();


            preparedStatementLoser.setString(1, loser);
            preparedStatementLoser.executeUpdate();

            connection.commit();}
            catch (SQLException e){
                connection.rollback();
                //cofnięcie zmian
            }

        }
        catch (SQLException e){
            System.err.println("Bląd polaczenia");
        }
    }

    public Map<String, Integer> getLeaderboard() {
        String sql_leaderboard = "SELECT login, points FROM users ORDER BY points DESC";
        Map<String,Integer>toReturn=new LinkedHashMap<>();//Musi być do poprawnej kolejności
        try (Connection connection = DriverManager.getConnection(URL);PreparedStatement stmt_leaderboard = connection.prepareStatement(sql_leaderboard);){

            try(ResultSet rs = stmt_leaderboard.executeQuery();){
                while (rs.next()) {
                    String login=rs.getString("login");
                    Integer points=rs.getInt("points");
                    toReturn.put(login,points);
                }
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return toReturn;
    }
}
