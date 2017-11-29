/*
Esta classe de metodos estaticos vai substituir a classe original BD
na aplicação após a conclusão dos testes. o objetivo é implementar 
o banco de dados real MySQL.
 */
package DAO;

import Entidades.*;
import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

/**
 *
 * @author Mateus Garcia
 */
public class BD_2 {

    /*#####################################################################
###########################################################################
###############################INSERÇÕES NO MYSQL##########################
###########################################################################
#########################################################################*/
    //INSERÇÕES DE ENDEREÇOS - teste ok
    public static void add(Endereco e) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Endereco (cep,numero,telfixo) VALUES (?,?,?)";
        try {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, new String(e.getcep()));
                stmt.setInt(2, e.getnumero());
                stmt.setString(3, new String(e.gettelFixo()));
                stmt.execute();
            }
            con.close();

        } catch (SQLException ex) {
            //System.out.println("Erro ao inserir endereço no BD: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao inserir endereço no BD: " + ex);
        }
    }

    //INSERÇÕES DE Pessoa - teste ok
    public static void add(PessoaFisica p) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Pessoa (endereco,nome,email,status) "
            + "values (?,?,?,?)";
        try {
            con.setAutoCommit(false); //precavendo possiveis merdas aqui...
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, p.getendereco().getcodigo());
                stmt.setString(2, p.getnome());
                stmt.setString(3, p.getemail());

                int stats = p.getstatus() ? 1 : 0;

                stmt.setInt(4, stats);
                stmt.execute();
                con.commit();
            }
            con.close();
            System.out.println("Pessoa gravada. aguardando a tabela de PF..");
            try {
                try (Connection con2 = new ConnectionFactory().getConnection()) {
                    con2.setAutoCommit(false); //Acertado aqui
                    String sql2 = "INSERT INTO PessoaFisica "
                        + "(id, dataNasc, rg, cpf, sexo, celular) "
                        + "values (?, ?, ?, ?, ?, ?)";
                    int id = BD_2.getidPessoa(p.getnome(), p.getemail());
                    java.sql.Date nasc = p.getdataNascDateObj();
                    String rg = p.getrg();
                    String cpf = p.getcpf();
                    String sexo = "" + p.getsexo();
                    String cel = p.getcelular();

                    try (PreparedStatement stmt2 = con2.prepareStatement(sql2)) {
                        stmt2.setInt(1, id);
                        stmt2.setDate(2, nasc);
                        stmt2.setString(3, rg);
                        stmt2.setString(4, cpf);
                        stmt2.setString(5, sexo);
                        stmt2.setString(6, cel);
                        stmt2.execute();
                        con2.commit();
                    }
                }
                System.out.println("PessoaFisica Armazenada");
            } catch (SQLException ex) {
                System.out.println("Erro ao registrar na tabela pessoaFisica.: " + ex);
                //JOptionPane.showMessageDialog(null, "Erro ao registrar na tabela pessoaFisica\n" + ex);
            }

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Erro ao registrar dados da Pessoa.: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao registrar dados da Pessoa.\n " + ex);
            try {
                con.rollback(); //deu merda
            } catch (SQLException ex1) {
                Logger.getLogger(BD_2.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    //INSERÇÕES DE RespFinFisico - teste ok
    public static void add(RespFinFisico rf) {
        Connection con = new ConnectionFactory().getConnection();
        add((PessoaFisica) rf); //reaproveitar codigo ne...
        PessoaFisica p = (PessoaFisica) rf;
        int id = BD_2.getidPessoa(rf.getcpf());
        String sql = "INSERT INTO RespFinFisico (id) VALUES (?)";
        try {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.execute();
            }
            con.close();
            System.out.println("RespFinFisico gravado na ID.: " + id);
            //JOptionPane.showMessageDialog(null, "Responsável Registrado com Sucesso!"
            //      + "\nID do Responsável: " + id + "\n"
            //      + "Còdigo do Endereco: " + rf.getendereco().getcodigo());
        } catch (HeadlessException | SQLException ex) {
            System.out.println("Falha ao gravar em RespFinFisico.: " + ex);
            //JOptionPane.showMessageDialog(null, "Falha ao gravar em RespFinFisico.: " + ex);
        }

    }

    //INSERÇÕES DE PACIENTE - teste ok
    public static void add(Paciente p) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Paciente (id, respfin) VALUES (?, ?)";

        try {
            //armazeno as tabelas Pessoa e PessoaFisica pelo metodo add
            add((PessoaFisica) p);
            //capturo o ID gerado pelo MySQL
            int id = BD_2.getidPessoa(p.getcpf());
            try ( //informo à statement os IDs envolvidos.
                PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                //dentro do objeto p há um outro objeto pessoa q é o respfin. aqui eu capturo o seu id
                stmt.setInt(2, p.getRespFin().getidPessoa());
                stmt.execute();
            }
            con.close();
            System.out.println("Paciente armazenado. ID: " + id);
            //JOptionPane.showMessageDialog(null, "Paciente registrado com sucesso\n"
            //   + "ID do Paciente: " + id + "\n"
            //    + "Codigo do Endereco: " + p.getendereco().getcodigo());
        } catch (HeadlessException | SQLException e) {
            System.out.println("Falha ao gravar Paciente.: " + e);
            //JOptionPane.showMessageDialog(null, "Falha ao gravar Paciente.: \n" + e);
        }
    }

    //INSERÇÕES DE RESPFIN JURIDICO - teste ok
    public static void add(RespFinJuridico pj) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Pessoa (Endereco,nome,email,status) "
            + "values (?,?,?,?)";
        try {
            con.setAutoCommit(false);
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, pj.getendereco().getcodigo());
                stmt.setString(2, pj.getnome());
                stmt.setString(3, pj.getemail());

                int stats = pj.getstatus() ? 1 : 0;

                stmt.setInt(4, stats);
                stmt.execute();
                con.commit();
            }
            con.close();
            System.out.println("Pessoa gravada. aguardando a tabela de RespFinJuridico..");
        } catch (SQLException ex) {
            System.out.println("Erro ao registrar dados da Pessoa.: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao registrar dados da Pessoa.\n " + ex);
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BD_2.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        Connection con2 = new ConnectionFactory().getConnection();
        String sql2 = "INSERT INTO RespFinJuridico (id, cnpj) VALUES (?,?)";
        try {
            con2.setAutoCommit(false);
            int id;
            try (PreparedStatement ps = con2.prepareStatement(sql2)) {
                id = BD_2.getidPessoa(pj.getnome(), pj.getemail());
                String cnpj = new String(pj.getcnpj());
                ps.setInt(1, id);
                ps.setString(2, cnpj);
                ps.execute();
            }
            con2.commit();
            con2.close();
            System.out.println("RespFinJuridico gravado na ID.: " + id);
            //JOptionPane.showMessageDialog(null, "Pessoa Juridica armazenada com sucesso!\n"
            //      + "ID do Responsável: " + id + "\n"
            //       + "Código do endereco: " + pj.getendereco().getcodigo());

        } catch (HeadlessException | SQLException e) {
            System.out.println("Erro ao gravar respfin.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao gravar respfin\n " + e);
            try {
                con2.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(BD_2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //INSERÇÕES DE NOVOS USUÁRIOS DO SISTEMA - teste ok
    public static void add(Usuario u) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Usuario (id,login,senha) VALUES (?,?,?)";
        try {
            add((PessoaFisica) u);
            int id = BD_2.getidPessoa(u.getcpf());
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, u.getlogin());
            ps.setString(3, u.getsenha());
            ps.execute();
            System.out.println("Usuário armazenado.");
            //JOptionPane.showMessageDialog(null, "Usuário armazenado com sucesso.");

        } catch (HeadlessException | SQLException e) {
            System.out.println("Erro ao cadastrar Usuario.: " + e);
            // JOptionPane.showMessageDialog(null, "Erro ao cadastrar Usuario\n " + e);
        }

    }

    //metodo para adicionar um paciente cujo ID já exista como um respfin - teste ok
    public static void add(Paciente p, int id) {
        PessoaFisica pf = BD_2.getPessoaFisica(id);
        Paciente pac = new Paciente(pf);
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO Paciente (id, respfin) VALUES (?, ?)";
        try {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, id);
                ps.execute();
            }
            con.close();
            System.out.println("Paciente armazenado com sucesso na ID: " + id);
            // JOptionPane.showMessageDialog(null, "Paciente armazenado com sucesso\nID: " + id);
        } catch (HeadlessException | SQLException e) {
            System.out.println("Erro ao registrar paciente.:" + e);
            //JOptionPane.showMessageDialog(null, "Erro ao registrar paciente\n" + e);
        }

    }

    //Inserção do log de acesso
    public static void loga(Usuario u, String datetime) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "INSERT INTO LogAcessos (userid,username,data) VALUES (?,?,?)";
        try {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, u.getidPessoa());
                ps.setString(2, u.getlogin());
                ps.setString(3, datetime);
                ps.execute();
            }
            con.close();
        } catch (SQLException e) {

        }
    }

    /*#####################################################################
###############################UPDATES DAS TABELAS#########################
#########################################################################*/
    //UPDATES DE ENDEREÇO
    public static void update(Endereco e) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "UPDATE Endereco SET "
            + "cep = " + e.getcep() + ", "
            + "numero = " + e.getnumero() + ", "
            + "telfixo = " + e.gettelFixo()
            + " WHERE codigo = " + e.getcodigo();

        try {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.executeUpdate(sql);
            }
            con.close();

        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar o endereço no BD: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao atualizar o endereço no BD: " + ex);
        }
    }

    /*#########################################################################
###############################SELECTS NAS TABELAS##########################
##########################################################################*/
    //CAPTURAR LISTA DE TODOS OS ENDEREÇOS NO BANCO
    public static List<Endereco> getEnderecos() {
        List<Endereco> lista = new ArrayList();
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM Endereco";
        try {
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Endereco en = new Endereco();
                    en.setcep(rs.getString("cep").toCharArray());
                    en.setcodigo(rs.getInt("codigo"));
                    en.setnumero(rs.getInt("numero"));
                    en.settelFixo(rs.getString("telfixo").toCharArray());
                    lista.add(en);
                }
            }
            con.close();
            return lista;

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar obter Lista de Endereços: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao tentar obter Lista de Endereços\n" + ex);
            return null;
        }
    }

    //CAPTURAR UM ENDEREÇO PELO SEU CODIGO
    public static Endereco getEndereco(int codigo) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM Endereco WHERE codigo = " + codigo;
        try {
            Endereco en;
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                en = new Endereco();
                while (rs.next()) {
                    en.setcep(rs.getString("cep").toCharArray());
                    en.setcodigo(rs.getInt("codigo"));
                    en.setnumero(rs.getInt("numero"));
                    en.settelFixo(rs.getString("telfixo").toCharArray());
                }
            }
            con.close();
            if (en.getcodigo() != 0) {
                return en;
            } else {
                System.out.println("Nenhum Endereço corresponde ao código informado");
                //   JOptionPane.showMessageDialog(null, "Endereço não encontrado");
                return null;
            }

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Erro ao tentar obter Lista de Endereços: " + ex);
            // JOptionPane.showMessageDialog(null, "Erro ao tentar obter Lista de Endereços\n" + ex);
            return null;
        }
    }

    //CAPTURAR UMA ID DE PESSOAFISICA PELO SEU CPF
    public static int getidPessoa(String cpf) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT id FROM PessoaFisica WHERE cpf = " + cpf;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            if (id != 0) {
                return id;
            } else {
                //System.out.println("ID não encontrado");
                //JOptionPane.showMessageDialog(null, "ID não encontrado");
                return 0;
            }

        } catch (HeadlessException | SQLException ex) {
            // System.out.println("Erro ao obter ID de Pessoa.: " + ex);
            //JOptionPane.showMessageDialog(null, "Erro ao obter ID de Pessoa\n" + ex);
            return 0;
        }
    }

    //CAPTURAR UMA ID DE PESSOA PELO SEU NOME E EMAIL
    public static int getidPessoa(String nome, String email) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT id FROM Pessoa WHERE nome = '" + nome
            + "' AND email = '" + email + "'";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            if (id != 0) {
                return id;
            } else {
                System.out.println("ID não encontrado");
                //   JOptionPane.showMessageDialog(null, "ID não encontrado");
                return 0;
            }

        } catch (HeadlessException | SQLException ex) {
            System.out.println("Erro ao obter ID de Pessoa.: " + ex);
            // JOptionPane.showMessageDialog(null, "Erro ao obter ID de Pessoa\n" + ex);
            return 0;
        }
    }

    //CAPTURA UMA PESSOAfisica PELO SEU ID
    public static PessoaFisica getPessoaFisica(int id) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM Pessoa join PessoaFisica on "
            + "Pessoa.id = PessoaFisica.id "
            + "WHERE Pessoa.id = (?);";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            PessoaFisica pf = new RespFinFisico();
            while (rs.next()) {
                String cel = rs.getString("celular");
                String cpf = rs.getString("cpf");
                java.util.Date nasc = rs.getDate("dataNasc");
                String email = rs.getString("email");
                int codigoEndereco = rs.getInt("endereco");
                Endereco e = getEndereco(codigoEndereco);
                int idPessoa = rs.getInt("id");
                String rg = rs.getString("rg");
                char sexo = rs.getString("sexo").charAt(0);
                int stats = rs.getInt("status");
                boolean status = (stats != 0);
                String nome = rs.getString("nome");

                pf.setcelular(cel);
                pf.setcpf(cpf);
                pf.setdataNasc(nasc);
                pf.setemail(email);
                pf.setendereco(e);
                pf.setidPessoa(id);
                pf.setnome(nome);
                pf.setrg(rg);
                pf.setsexo(sexo);
                pf.setstatus(status);

            }
            if (pf.getidPessoa() != 0) {
                return pf;
            } else {
                System.out.println("A Busca não encontrou um RespFin com o ID " + id);
                // JOptionPane.showMessageDialog(null, "A Busca não encontrou um RespFin com o ID " + id);
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar o RespFinFisico.: " + ex);
            // JOptionPane.showMessageDialog(null, "Erro ao buscar o RespFinFisico.: " + ex);
            return null;
        }
    }

    public static Pessoa getRespFin(int id) {
        Connection con = new ConnectionFactory().getConnection();
        try {
            CallableStatement cs = con.prepareCall("CALL getRespFinFisico(?)");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                RespFinFisico rf = new RespFinFisico();
                rf.setcelular(rs.getString("celular"));
                rf.setcpf(rs.getString("cpf"));
                rf.setdataNasc(rs.getDate("dataNasc"));
                rf.setemail(rs.getString("email"));

                Endereco en = new Endereco();
                en.setcep(rs.getString("cep").toCharArray());
                en.setcodigo(rs.getInt("endereco"));
                en.setnumero(rs.getInt("numero"));
                en.settelFixo(rs.getString("telFixo").toCharArray());
                rf.setendereco(en);

                rf.setidPessoa(id);
                rf.setnome(rs.getString("nome"));
                rf.setrg(rs.getString("rg"));
                rf.setsexo(rs.getString("sexo").charAt(0));
                rf.setstatus((rs.getInt("status") != 0));
                cs.close();
                con.close();
                return rf;
            } else {
                String sql2 = "CALL getRespFinJuridico(?)";
                CallableStatement cs2 = con.prepareCall(sql2);
                cs2.setInt(1, id);
                ResultSet rs2 = cs2.executeQuery();
                if (rs2.next()) {
                    RespFinJuridico pj = new RespFinJuridico();
                    pj.setcnpj(rs2.getString("cnpj").toCharArray());
                    pj.setemail(rs2.getString("email"));

                    Endereco en2 = new Endereco();
                    en2.setcep(rs2.getString("cep").toCharArray());
                    en2.setcodigo(rs2.getInt("endereco"));
                    en2.setnumero(rs2.getInt("numero"));
                    en2.settelFixo(rs2.getString("telFixo").toCharArray());
                    pj.setendereco(en2);

                    pj.setidPessoa(id);
                    pj.setnome(rs2.getString("nome"));
                    pj.setstatus((rs2.getInt("status") != 0));
                    cs2.close();
                    con.close();
                    return pj;
                } else {
                    //System.out.println("O ID informado não pertence a nenhum responsável Financeiro");
                    //JOptionPane.showMessageDialog(null, "O ID informado não pertence a nenhum responsável Financeiro");
                    return null;
                }
            }
        } catch (HeadlessException | SQLException e) {
            //System.out.println("Erro ao buscar respfin.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao buscar Responsáveis.\n" + e);

        }
        return null;
    }

    public static List<Pessoa> getAllRespFin() {
        Connection con = new ConnectionFactory().getConnection();
        List<Pessoa> lista = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Pessoa.id,endereco,nome,email,status,dataNasc,rg,cpf,sexo,celular,cep,numero,telFixo\n"
                + "    FROM Pessoa JOIN PessoaFisica\n"
                + "        ON Pessoa.id = PessoaFisica.id JOIN RespFinFisico\n"
                + "            ON RespFinFisico.id = Pessoa.id JOIN Endereco\n"
                + "                ON Endereco.codigo = endereco");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RespFinFisico rf = new RespFinFisico();
                rf.setcelular(rs.getString("celular"));
                rf.setcpf(rs.getString("cpf"));
                rf.setdataNasc(rs.getDate("dataNasc"));
                rf.setemail(rs.getString("email"));

                Endereco en = new Endereco();
                en.setcep(rs.getString("cep").toCharArray());
                en.setcodigo(rs.getInt("endereco"));
                en.setnumero(rs.getInt("numero"));
                en.settelFixo(rs.getString("telFixo").toCharArray());
                rf.setendereco(en);

                rf.setidPessoa(rs.getInt("id"));
                rf.setnome(rs.getString("nome"));
                rf.setrg(rs.getString("rg"));
                rf.setsexo(rs.getString("sexo").charAt(0));
                rf.setstatus((rs.getInt("status") != 0));
                lista.add(rf);
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println("Erro ao buscar respfin.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao buscar Responsáveis.\n" + e);

        }
        return lista;
    }

    //CAPTURA RESPFINJURIDICO PELO CNPJ
    public static Pessoa getpj(String cnpj) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT id FROM RespFinJuridico WHERE cnpj = '" + cnpj + "'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getRespFin(rs.getInt(1));
            } else {
                //  JOptionPane.showMessageDialog(null, "Nenhum registro encontrado");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar respfin.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao buscar respfin\n" + e);
        }
        return null;
    }

    //CAPTURA RESPFINFISICO PELO CPF
    public static Pessoa getrf(String cpf) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT id FROM PessoaFisica WHERE cpf = '" + cpf + "'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getRespFin(rs.getInt(1));
            } else {
                //   JOptionPane.showMessageDialog(null, "Nenhum registro encontrado");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar respfin.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao buscar respfin\n" + e);
        }
        return null;
    }

    //CAPTURA USUARIO
    public static Usuario getUsuario(String login) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT endereco,nome,email,status,dataNasc,rg,cpf,sexo,celular,cep,numero,telFixo,login,senha,Pessoa.id as ID"
            + "    FROM Pessoa JOIN PessoaFisica"
            + "        ON Pessoa.id = PessoaFisica.id JOIN Usuario"
            + "            ON Usuario.id = Pessoa.id JOIN Endereco"
            + "                ON Endereco.codigo = endereco"
            + "    WHERE Pessoa.id = (SELECT id FROM Usuario WHERE Usuario.login = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setcelular(rs.getString("celular"));
                u.setcpf(rs.getString("cpf"));
                u.setdataNasc(rs.getDate("dataNasc"));
                u.setemail(rs.getString("email"));

                Endereco en = new Endereco();
                en.setcep(rs.getString("cep").toCharArray());
                en.setcodigo(rs.getInt("endereco"));
                en.setnumero(rs.getInt("numero"));
                if (!rs.getString("telFixo").equals("")) {
                    en.settelFixo(rs.getString("telFixo").toCharArray());
                }

                u.setendereco(en);
                u.setidPessoa(rs.getInt("ID"));
                u.setlogin(login);
                u.setnome(rs.getString("nome"));
                u.setrg(rs.getString("rg"));
                u.setsenha(rs.getString("senha"));
                u.setsexo(rs.getString("sexo").charAt(0));
                //u.setstatus((rs.getInt("status")) != 0);
                u.setstatus(rs.getBoolean("status"));
                return u;
            } else {
                return null;
            }
        } catch (HeadlessException | SQLException e) {
            return null;
        }

    }

    //CAPTURA O CODIGO(ID) DE UM ENDERECO
    public static int getCodEndereco(String cep, int num) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT codigo FROM Endereco WHERE"
            + " cep = ? AND numero = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cep);
            ps.setInt(2, num);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("codigo");
            } else {
                //System.out.println("Nenhum Endereco encontrado");
                //JOptionPane.showMessageDialog(null, "Nenhum Endereco encontrado");
                return 0;
            }
        } catch (HeadlessException | SQLException e) {
            //System.out.println("Erro ao tentar obter o codigo do Endereco.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao tentar obter o codigo do Endereco\n" + e);

        }
        return 0;
    }

    //CAPTURA UM PACIENTE PELO SEU ID - teste ok
    public static Paciente getPaciente(int ID) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT respfin FROM Paciente WHERE Paciente.id = " + ID;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PessoaFisica pf = BD_2.getPessoaFisica(ID);
                Paciente pa = new Paciente(pf);
                Pessoa respfin = BD_2.getRespFin(rs.getInt("respfin"));
                pa.setRespFin(respfin);
                return pa;
            } else {
                //System.out.println("Nenhum Paciente Encontrado");
                return null;
            }

        } catch (SQLException e) {
            //System.out.println("Erro ao buscar Paciente.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao buscar Paciente\n" + e);
        }
        return null;
    }

    //CAPTURA UM PACIENTE PELO SEU CPF - teste ok
    public static Paciente getPaciente(String cpf) {
        int ID = BD_2.getidPessoa(cpf);
        if (ID != 0) {
            return BD_2.getPaciente(ID);

        } else {
            //System.out.println("ID não reconhecido");
            //JOptionPane.showMessageDialog(null, "ID não existe no banco de dados");
        }
        return null;
    }

    //CAPTURA UMA PESSOA PELO ID - teste ok
    public static Pessoa getPessoa(int id) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT endereco,nome,email,status,cep,numero,telFixo FROM Pessoa "
            + "JOIN Endereco ON Pessoa.endereco = Endereco.codigo "
            + "WHERE Pessoa.id = " + id;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pessoa p = new Pessoa();
                Endereco e = new Endereco();
                p.setemail(rs.getString("email"));
                p.setidPessoa(id);
                p.setnome(rs.getString("nome"));
                p.setstatus((rs.getInt("status") != 0));

                e.setcep(rs.getString("cep").toCharArray());
                e.setcodigo(rs.getInt("endereco"));
                e.setnumero(rs.getInt("numero"));
                e.settelFixo(rs.getString("telFixo").toCharArray());

                p.setendereco(e);

                return p;

            } else {
                System.out.println("A busca pela Pessoa não encontrou resultados");
                // JOptionPane.showMessageDialog(null, "A busca pela Pessoa não encontrou resultados");
            }
        } catch (HeadlessException | SQLException ex) {
            System.out.println("Erro ao buscar Pessoa no banco de dados.: " + ex);
            // JOptionPane.showMessageDialog(null, "Erro ao buscar Pessoa no banco de dados\n" + ex);
        }
        return null;
    }

    //CAPTURA UMA respfin PELO NOME
    public static Pessoa getrespfin(String nome) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT id FROM Pessoa WHERE nome = '" + nome + "'";
        try {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int ID = rs.getInt("id");
                    ps.close();
                    con.close();
                    return BD_2.getRespFin(ID);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Pessoa.: " + e);
            //  JOptionPane.showMessageDialog(null, "Erro ao buscar Pessoa\n" + e);
        }

        return null;
    }

    //retorna a data do ultimo login feito por um usuario
    public static String lastLogin(Usuario u) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT data FROM LogAcessos "
            + "WHERE userid = " + u.getidPessoa();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //pegar o penultimo registro de acesso para retornar 
            if (rs.next()) {
                if (rs.last() && rs.previous()) {
                    return rs.getString(1);

                } else {
                    return "Primeiro acesso ao sistema, Bem vindo " + u.getnome() + " !";
                }
            }
        } catch (Exception e) {
            return "Erro ao recuperar data de acesso.:" + e;
        }
        return "desconhecido. contate o desenvolvedor";
    }


    /*#########################################################################
###############################UPDATES NAS TABELAS#########################
##########################################################################*/
    //atualizar a tabela Pessoa - teste ok
    public static void updatePessoa(Pessoa p) {
        if (p.getidPessoa() != 1) {
            Connection con = new ConnectionFactory().getConnection();
            String sql = "UPDATE Pessoa SET "
                + "nome = ?, "
                + "email = ?, "
                + "status = ? "
                + "WHERE Pessoa.id = " + p.getidPessoa();

            String sql2 = "UPDATE Endereco SET "
                + "cep = ?, "
                + "numero = ?, "
                + "telFixo = ? "
                + "WHERE Endereco.codigo = " + p.getendereco().getcodigo();
            try {
                con.setAutoCommit(false); // precavendo possiveis merdas aqui
                PreparedStatement ps1 = con.prepareStatement(sql);
                ps1.setString(1, p.getnome());
                ps1.setString(2, p.getemail());
                ps1.setInt(3, (p.getstatus() == true ? 1 : 0));
                System.out.println(ps1.toString());//debug
                if (ps1.executeUpdate() > 0) {
                    System.out.println("Tabela Pessoa atualizada. aguardando commit...");
                } else {
                    System.out.println("Nenhum valor foi alterado nesta Pessoa");
                }
                try {
                    PreparedStatement ps2 = con.prepareStatement(sql2);
                    ps2.setString(1, new String(p.getendereco().getcep()));
                    ps2.setInt(2, p.getendereco().getnumero());
                    ps2.setString(3, new String(p.getendereco().gettelFixo()));

                    if (ps2.executeUpdate() > 0) {
                        System.out.println("Tabela Endereco atualizada. aguardando commit...");
                    } else {
                        System.out.println("Nenhum valor foi alterado neste Endereco");
                    }
                    try { //os finalmentes
                        con.commit();
                        ps1.close();
                        ps2.close();
                        con.close();
                        System.out.println("Update comitado.");
                        //   JOptionPane.showMessageDialog(null, "Update concluído");
                    } catch (HeadlessException | SQLException e) {
                        System.out.println("Erro ao comitar o update.: " + e);
                        //  JOptionPane.showMessageDialog(null, "Erro ao comitar o update\n" + e);
                    }
                } catch (HeadlessException | SQLException e) {
                    System.out.println("Erro ao tentar atualizar Endereco.: " + e);
                    //JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar Endereco\n" + e);
                }
            } catch (HeadlessException | SQLException e) {
                System.out.println("Erro ao tentar atualizar Pessoa.: " + e);
                // JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar pessoa\n" + e);
            }
        } else {
            // JOptionPane.showMessageDialog(null, "você não pode modificar o usuario ROOT!!!");
        }
    }

    //atualizar senha de usuario
    public static boolean updateSenha(int id, String senha) {
        Connection con = new ConnectionFactory().getConnection();
        String sql = "UPDATE Usuario SET senha = " + "'" + senha + "'" + " WHERE Usuario.id = " + id;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            System.out.println(ps.toString());
            if (ps.executeUpdate(sql) > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao tentar atualizar a tabela Usuario.: " + e);
            //JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar a tabela Usuario\n" + e);
        }
        return false;
    }

    //Foi necessário acrescentar
    public static ArrayList LogsAcesso() {
        ArrayList<registro> ls = new ArrayList();
        Connection con = new ConnectionFactory().getConnection();
        String sql = "SELECT userid,username,data FROM LogAcessos";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                registro r = new registro();
                r.id = rs.getInt("userid");
                r.username = rs.getString("username");
                r.data = rs.getString("data");
                ls.add(r);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Erro " + e);
        }
        return ls;
    }

}
