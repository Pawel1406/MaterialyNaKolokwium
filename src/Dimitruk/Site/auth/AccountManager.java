package Dimitruk.Site.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {


    public static int register(String name, String password) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "INSERT INTO account (username, password) VALUES(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        preparedStatement.setString(2, password);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            return id;
        } else {
            throw new RuntimeException("Nie udało się stworzyć użytkownika");
        }
    }

    public static boolean authenticate(String name, String password) throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "SELECT password  FROM account WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            String dbPassword = resultSet.getString("password");
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), dbPassword);
            if (result.verified) return true;
            else {
                System.err.println("Nie poprawne hasło");
                return false;
            }
        } else {
            System.err.println("Nie ma takiego użytkownika");
            return false;
        }
    }
}
