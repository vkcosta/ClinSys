package Entidades;

import DAO.ConnectionFactory;
import DAO.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mateus garcia
 */
public class Endereco implements DAO {

    private int codigo;
    private char[] cep = new char[8];
    private int numero;
    private char[] telFixo = new char[10];

    //opção vazia
    public Endereco() {
    };

    //construtor
    public Endereco(char[] cep, int num, char[] telfixo) {
        setcep(cep);
        setnumero(num);
        if (telfixo != null)settelFixo(telfixo);
    }
    public Endereco(String cep, int num, String telfixo) {
        this.cep = cep.toCharArray();
        numero = num;
        if (telfixo != null)telFixo = telfixo.toCharArray();
    }
    public int getcodigo() {
        return this.codigo;
    }
    public char[] getcep() {
        return this.cep;
    }
    public int getnumero() {
        return this.numero;
    }
    public char[] gettelFixo() {
        return this.telFixo;
    }
    public void setcodigo(int id) {
        this.codigo = id;
    }
    public void setcep(char[] cep) {
        if (cep.length == 8) {
            this.cep = cep;
        } else {
            //System.out.println("cep INVALIDO");
            //JOptionPane.showMessageDialog(null, "CEP inválido");
        }
    }
    public void setnumero(int num) {
        this.numero = num;
    }
    public void settelFixo(char[] tel) {
        /*if (tel.length == 10) {
            this.telFixo = tel;
        } else {
            System.out.println("Telefone Fixo INVALIDO");
            JOptionPane.showMessageDialog(null, "Telefone Fixo inválido");
        }*/
        this.telFixo = tel;
    }

    
    @Override
    public void add(Object o) {
        if (o instanceof Endereco) {
            Endereco e = (Endereco) o;
            Connection con = new ConnectionFactory().getConnection();
            String sql = "INSERT INTO Endereco (cep,numero,telfixo) VALUES (?,?,?)";
            try {
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, new String(e.getcep()));
                stmt.setInt(2, e.getnumero());
                stmt.setString(3, new String(e.gettelFixo()));
                stmt.execute();
                stmt.close();
                con.close();
            } catch (Exception ex) {
                System.out.println("Erro ao inserir endereço no BD: " + ex);
                JOptionPane.showMessageDialog(null, "Erro ao inserir endereço no BD: " + ex);
            }
        } else {
            System.out.println("O objeto apontado não é um Endereco.");
            JOptionPane.showMessageDialog(null, "O objeto apontado não é um Endereco.");
        }
    }
    @Override
    public void update(Object o) {
        if (o instanceof Endereco) {
            Endereco e = (Endereco) o;
            Connection con = new ConnectionFactory().getConnection();
            String sql = "UPDATE Endereco SET "
                    + "cep = " + e.getcep() + ", "
                    + "numero = " + e.getnumero() + ", "
                    + "telfixo = " + e.gettelFixo()
                    + " WHERE codigo = " + e.getcodigo();

            try {
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.executeUpdate(sql);

            } catch (Exception ex) {
                System.out.println("Erro ao atualizar o endereço no BD: " + ex);
                JOptionPane.showMessageDialog(null, "Erro ao atualizar o endereço no BD: " + ex);
            }
        } else {
            System.out.println("O objeto apontado não é um Endereco.");
            JOptionPane.showMessageDialog(null, "O objeto apontado não é um Endereco.");
        }
    }
    @Override
    public List getall() {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM Endereco";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            List ls = new ArrayList();
            while (rs.next()) {
                Endereco e = new Endereco();
                e.setcodigo(rs.getInt("codigo"));
                e.setcep(rs.getString("cep").toCharArray());
                e.setnumero(rs.getInt("numero"));
                e.settelFixo(rs.getString("telfixo").toCharArray());
                ls.add(e);
            }
            return ls;
        } catch (Exception e) {
            System.out.println("Erro ao recuperar os dados de Endereco " + e);
            JOptionPane.showMessageDialog(null, "Erro ao recuperar os dados de Endereco\n" + e);
            return null;
        }
    }
}
