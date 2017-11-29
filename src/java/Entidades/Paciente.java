/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author mateus garcia
 */
public class Paciente extends PessoaFisica {
    public Paciente(){}
    
    public Paciente(PessoaFisica pf) {
        this.setcelular(pf.getcelular());
        this.setcpf(pf.getcpf());
        this.setdataNasc(pf.getdataNascDate());
        this.setemail(pf.getemail());
        this.setendereco(pf.getendereco());
        this.setidPessoa(pf.getidPessoa());
        this.setnome(pf.getnome());
        this.setrg(pf.getrg());
        this.setsexo(pf.getsexo());
        this.setstatus(pf.getstatus());
    }
    

    private Pessoa RespFin;
    
    
    public void setRespFin(Pessoa p) {
        RespFin = p;
    }
    
    public Pessoa getRespFin() {
        return RespFin;
    }
    
}
