/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import DAO.BD_2;
import Entidades.Endereco;
import Entidades.Paciente;
import Entidades.Pessoa;
import Entidades.PessoaFisica;
import Entidades.RespFinFisico;
import Entidades.RespFinJuridico;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vitor.costa
 */
public class CadastraPaciente implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {

        HttpSession sessao = request.getSession();
        Paciente p = new Paciente();
        Date dataNasc = new Date();
        Pessoa responsavel;
        request.setAttribute("listaRespFin", BD_2.getAllRespFin());

        //Recupeção dos dados digitados no formulário
        int id = Integer.parseInt(request.getParameter("mostraID"));

        //Validação do ID do Resp. Financeiro
        if (id != 0) {
            //Recupera o Resp. Fin selecionado
            Pessoa identificador = BD_2.getRespFin(id);
            if (identificador != null) {
                if (identificador instanceof RespFinFisico) {
                    responsavel = new RespFinFisico();
                    responsavel = identificador;
                } else {
                    responsavel = new RespFinJuridico();
                    responsavel = identificador;
                }
            } else {
                sessao.setAttribute("msgErro", "Erro ao selecionar o Responsável Financeiro!");
                return "NovoPaciente.jsp";
            }
        } else {
            sessao.setAttribute("msgErro", "Selecione um Responsável Financeiro");
            return "NovoPaciente.jsp";

        }

        String nome = request.getParameter("nome");
        String dia = request.getParameter("dia");
        String mes = request.getParameter("mes");
        String ano = request.getParameter("ano");
        String cpf = request.getParameter("cpf");
        String rg = request.getParameter("rg");
        char sexo = request.getParameter("sexo").charAt(0);
        String cep = request.getParameter("cep");
        int numero = Integer.parseInt(request.getParameter("numero"));
        String email = request.getParameter("email");
        String telFixo = request.getParameter("telFixo");
        String telCelular = request.getParameter("telCelular");
        String data = dia + "/" + mes + "/" + ano;
        try {
            dataNasc = PessoaFisica.FormatarData(data);
        } catch (ParseException ex) {
            Logger.getLogger(CadastraRespFinFisico.class.getName()).log(Level.SEVERE, null, ex);
            sessao.setAttribute("msgErro", "Erro ao selecionar a Data de Nascimento!");
            return "NovoPaciente.jsp";
        }
        //Validações dos compos do formulário        
        if (nome == null) {
            sessao.setAttribute("msgErro", "Nome não pode ser vazio!");
            return "NovoPaciente.jsp";
        }

        if (cpf == null || cpf.length() != 11) {
            sessao.setAttribute("msgErro", "CPF Inválido!");
            return "NovoPaciente.jsp";
        } else {
            int verificaCPF = BD_2.getidPessoa(cpf);
            PessoaFisica pf = BD_2.getPessoaFisica(verificaCPF);
            //Verifica se o CPF informado já existe no banco de dados
            if (verificaCPF != 0) {
                sessao.setAttribute("msgErro", "Já existe um cadastro com esse CPF!");
                return "NovoPaciente.jsp";
            }
        }

        if (rg.length() != 9) {
            sessao.setAttribute("msgErro", "RG não pode ser vazio!");
            return "NovoPaciente.jsp";
        }
        if (sexo == 0) {
            sessao.setAttribute("msgErro", "Sexo não pode ser vazio!");
            return "NovoPaciente.jsp";
        }
        if (cep.length() != 8) {
            sessao.setAttribute("msgErro", "CEP Inválido!");
            return "NovoPaciente.jsp";
        }
        if (email == null) {
            sessao.setAttribute("msgErro", "Email Inválido!");
            return "NovoPaciente.jsp";
        }
        if (telCelular.length() == 0 && telCelular == null) {
            sessao.setAttribute("msgErro", "Telefone Celular Inválido!");
            return "NovoPaciente.jsp";
        }
        //Cria e insere um novo Objeto do tipo endereço no banco de dados
        Endereco e = new Endereco();
        if (telFixo.equals("")) {
            e = new Endereco(cep, numero, null);
        } else {
            e = new Endereco(cep, numero, telFixo);
        }

        try {
            BD_2.add(e);
        } catch (Exception ex) {
            sessao.setAttribute("msgErro", "Erro ao cadastrar o Endereço!");
            return "NovoPaciente.jsp";
        }

        //Pegar o código do endereço cadastrado anteriormente
        e.setcodigo(BD_2.getCodEndereco(new String(e.getcep()), e.getnumero()));

        p.setRespFin(responsavel);
        p.setnome(nome);
        p.setdataNasc(dataNasc);
        p.setcpf(cpf);
        p.setrg(rg);
        p.setsexo(sexo);
        p.setendereco(e);
        p.setemail(email);
        p.setcelular(telCelular);
        BD_2.add(p);

        sessao.setAttribute("msgSucesso", "Cadastro Realizado com Sucesso!");
        return "NovoPaciente.jsp";

    }
}
