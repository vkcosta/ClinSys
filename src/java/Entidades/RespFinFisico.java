package Entidades;

import java.util.Date;

/**
 *
 * @author mateus garcia
 */
public class RespFinFisico extends PessoaFisica {

    public RespFinFisico() {}
    
    //apenas para os testes
    public RespFinFisico (Endereco e, String nome, String email, String rg, String cpf, char sexo, String celular, int id) {
        setendereco(e);
        setnome(nome);
        setemail(email);
        setdataNasc(new Date());
        setrg(rg);
        setcpf(cpf);
        setsexo(sexo);
        setcelular(celular);
        setidPessoa(id);
    }
}
