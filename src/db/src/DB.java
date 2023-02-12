package db.src;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
public class DB {

    private static Connection connection = null;

    // Conectando ao banco de dados
    public static Connection getConnection(){
        if(connection == null){
            try {

                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    // Fechando a conexão com o banco de dados

    public static void closeConnection(){
        if(connection != null) {
            try {
                connection.close();

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    // Obtendo os dados da properties (dados de acesso ao banco de dados)
    private static Properties loadProperties()  {

        try {
            FileInputStream fs = new FileInputStream("db.properties");
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    // Fechando a conexão com Statement
    public static void closeStatement(Statement st) {
        if(st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }

    // Fechando a conexão com ResultSet
    public static void closeResultSet(ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

}
