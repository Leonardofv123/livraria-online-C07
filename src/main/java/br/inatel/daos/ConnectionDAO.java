package br.inatel.daos;

import java.sql.*;

public abstract class ConnectionDAO {
    protected Connection connection;
    protected PreparedStatement pst;
    protected Statement st;
    protected ResultSet rs;

    private final String database = "livraria_online";
    private final String user     = "root";
    private final String password = "root";
    private final String url      = "jdbc:mysql://localhost:3306/" + database;

    public Connection connectToBd() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return connection; // BUG CORRIGIDO: antes retornava null sempre
        } catch (SQLException e) {
            System.out.println("Erro ao conectar no banco de dados: " + e.getMessage());
            return null;
        }
    }
}
