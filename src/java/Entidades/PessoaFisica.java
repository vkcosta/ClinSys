package Entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mateus garcia
 */
public class PessoaFisica extends Pessoa {

    private Date dataNasc;
    private char[] rg;
    private char[] cpf;
    private char sexo;
    private char[] celular;

    //..
    public void setrg(String doc) {
        char[] rg = doc.toCharArray();
        if (rg.length == 9) {
            this.rg = rg;
        } else {
            System.out.println("rg INVALIDO");
        }
    }

    public void setcpf(String doc) {
        char[] cpf = doc.toCharArray();
        if (cpf.length == 11) {
            this.cpf = cpf;
        } else {
            System.out.println("cpf INVALIDO");
        }
    }
    
    public String getcpf() {
        String CPF = new String(cpf);
        return CPF;
    }
    
    public String getrg() {
        String RG = new String(this.rg);
        return RG;
    }
    

    public void setcelular(String num) {
        if (num != null) {
        char[] cel = num.toCharArray();
        if (cel.length == 11) {
            this.celular = cel;
        } else {
            System.out.println("Celular invalido");
        }
        } else this.celular = null;
    }

    public String getcelular() {
        if (this.celular != null) {
        String cel = new String(celular);
        return cel;
        } else return null;
    }
    
    
    public void setsexo(char s) {
        if (s == 'F' || s == 'M') {
            this.sexo = s;
        } else {
            System.out.println("Sexo INVALIDO");
        }
    }
    
    public char getsexo() {
        return sexo;
    }
    
    //retorna a data em formato String
    public String getdataNasc() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return (df.format(dataNasc));
    }
    
    //retorna o objeto Date
    public Date getdataNascDate() {
        return this.dataNasc;
    }
    
    public java.sql.Date getdataNascDateObj() {
        java.sql.Date data = new java.sql.Date(dataNasc.getTime());
        return data;
    }

    public void setdataNasc(Date d) {
        dataNasc = d;
    }
    //metodo estatico que recebe uma string no formato dd-MM-yyyy e devolve uma data
    public static Date FormatarData(String data_s) throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");        
        Date d = df.parse(data_s);
        return d;
    }
    

}
