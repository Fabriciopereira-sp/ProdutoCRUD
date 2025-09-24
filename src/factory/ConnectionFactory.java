package factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String USERNAME = "rm563237";
    private static final String PASSWORD = "270604";
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
