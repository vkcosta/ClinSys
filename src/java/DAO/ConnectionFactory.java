package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Mateus Garcia
 */
public class ConnectionFactory {

    private String host = "db4free.net";
    private int port = 3307;
    private String user = "mgarcia";
    private String password = "123456";
    private String db = "clinsys";
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + db;

    public Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return (DriverManager.getConnection(url, user, password));
        } catch (Exception e) {
            System.out.println("Erro " + e);
            //JOptionPane.showMessageDialog(null, "Não foi possível conectar.\n" + e);
            return null;
        }
    }
}
