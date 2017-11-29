package Entidades;

/**
 *
 * @author mateus garcia
 */
public class RespFinJuridico extends Pessoa {
    private char[] cnpj;
    
    public RespFinJuridico(){};
    
    public RespFinJuridico(String nome, String email, String cnpj, Endereco e, int id) {
        setnome(nome);
        setemail(email);
        this.cnpj = cnpj.toCharArray();
        setendereco(e);
        setidPessoa(id);
    }
    
    
    public void setcnpj(char[] c) {
        cnpj = c;
    }
    public char[] getcnpj() {
        return cnpj;
    }
}
