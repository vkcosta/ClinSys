package Entidades;

/**
 *
 * @author mateus garcia
 */
public class Pessoa {
    private int idPessoa;
    private Endereco endereco;
    private String nome;
    private String email;
    private boolean status;
    //..
    
    public int getidPessoa() {
        return this.idPessoa;
    }
    
    public Endereco getendereco() {
        return this.endereco;
    }
    
    public String getnome() {
        return this.nome;
    }
    
    public String getemail() {
        return this.email;
    }
    
    public void setidPessoa(int id) {
        this.idPessoa = id;
    }
    
    public void setnome(String nome) {
        this.nome = nome;
    }
    
    public void setemail(String email) {
        this.email = email;
    }
    
    public void setendereco(Endereco e) {
        this.endereco = e;
    }
    public boolean getstatus() {
        return status;
    }
    public void setstatus(boolean s) {
        status = s;
    }
    
}
