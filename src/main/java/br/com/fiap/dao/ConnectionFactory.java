package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("oracle.jdbc.driver.OracleDriver");
            final String URL = System.getenv("DB_URL_ORACLE_FIAP");
            final String USERNAME = System.getenv("DB_USER_ORACLE_FIAP");
            final String PASSWORD = System.getenv("DB_PASSWORD_ORACLE_FIAP");

            if (URL.isEmpty() || USERNAME.isEmpty() || PASSWORD.isEmpty()) {
                throw new RuntimeException("Variáveis de ambiente do banco, estão mal configuradas.");
            }

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erro nome da classe: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (!connection.isClosed()) {
                    connection.close();
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
