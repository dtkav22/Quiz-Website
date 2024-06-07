package DataBaseConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
        private Connection connection;
        public DataBaseConnector() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost:" + DataBaseUserProperties.port + DataBaseUserProperties.dataBaseName;
                String username = DataBaseUserProperties.userName;
                String password = DataBaseUserProperties.password;

                connection = DriverManager.getConnection(url , username , password);
                //System.out.println("Connected successfully");
            }catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Error loading JDBC driver: " + e.getMessage());
            }
        }

        public Connection getDataBaseConnection() {
            return connection;
        }
}
