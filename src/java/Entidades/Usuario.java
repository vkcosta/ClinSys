package Entidades;

import java.sql.Connection;
import DAO.ConnectionFactory;

/**
 *
 * @author mateus garcia
 */
public class Usuario extends PessoaFisica {

    private String login;
    private String senha;

    public Usuario() {}

    public void setlogin(String s) {
        login = s;
    }

    public void setsenha(String s) {
        senha = s;
    }

    public String getlogin() {
        return login;
    }

    public String getsenha() {
        return senha;
    }

    
    //só pra testes em telaLOGIN
    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
    
    public boolean equals(Usuario u) {
        if (u == null) {
            return false;
        }
        else if( u.getlogin().equals(this.getlogin()) && u.getsenha().equals(this.getsenha())) {
            return true;
        }
        return false;
    }
    
    
}
