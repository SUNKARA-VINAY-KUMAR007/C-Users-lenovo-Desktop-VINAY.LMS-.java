import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Demonstrates connecting to a MySQL database using JDBC,
 * executing a SELECT query and displaying the results.
 *
 * Sample setup (run in MySQL first):
 *
 *   CREATE DATABASE mydatabase;
 *   USE mydatabase;
 *   CREATE TABLE users (
 *       id INT PRIMARY KEY AUTO_INCREMENT,
 *       username VARCHAR(50) NOT NULL,
 *       email VARCHAR(100) NOT NULL
 *   );
 *   INSERT INTO users (username, email) VALUES
 *       ('johndoe', 'johndoe@example.com'),
 *       ('janedoe', 'janedoe@example.com'),
 *       ('alice',   'alice@example.com');
 */
public class FetchUsers {

    // Step 2: connection constants (change to match your MySQL setup)
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {

        // Step 5: SQL query
        String sqlQuery = "SELECT * FROM users";

        // Steps 3, 4, 6: resources declared in try-with-resources
        // (closed automatically in reverse order: ResultSet, Statement, Connection)
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            // Step 7: iterate through the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                System.out.println("User ID: " + id
                        + ", Username: " + username
                        + ", Email: " + email);
            }

        } catch (SQLException e) {
            // Step 8: handle database errors
            System.err.println("Database error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState()
                    + ", Error Code: " + e.getErrorCode());
        }
    }
}