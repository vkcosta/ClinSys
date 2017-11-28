package logica;

import DAO.BD_2;
import Entidades.Endereco;
import Entidades.Pessoa;
import Entidades.PessoaFisica;
import Entidades.RespFinFisico;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class CadastraRespFinFisico implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        RespFinFisico rff = new RespFinFisico();
        Date dataNasc = new Date();
        //Recupeção dos dados digitados no formulário
        String nome = request.getParameter("nome");
        //String dia = request.getParameter("dia");
        // String mes = request.getParameter("mes");
        // String ano = request.getParameter("ano");
        String data = request.getParameter("data");
        String cpf = request.getParameter("cpf");
        String rg = request.getParameter("rg");
        char sexo = request.getParameter("sexo").charAt(0);
        String cep = request.getParameter("cep");
        int numero = Integer.parseInt(request.getParameter("numero"));
        String email = request.getParameter("email");
        String telFixo = request.getParameter("telFixo");
        String telCelular = request.getParameter("telCelular");
        //String data = dia + mes + ano;
        try {
            dataNasc = PessoaFisica.FormatarData(data);
        } catch (ParseException ex) {
            Logger.getLogger(CadastraRespFinFisico.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro ao selecionar a Data de Nascimento!");
            sessao.setAttribute("erro", "Erro ao selecionar a Data de Nascimento!");
        }     
     
        
           
        
        //Validações dos compos do formulário
        if (nome == null) {
            sessao.setAttribute("msgErro", "Nome não pode ser vazio!");
        }
        if (cpf == null || cpf.length() != 11) {
            sessao.setAttribute("msgErro", "CPF Inválido!");
        }
        if (rg.length() != 9) {
            sessao.setAttribute("msgErro", "RG não pode ser vazio!");
        }
        if (sexo == 0) {
            sessao.setAttribute("msgErro", "Sexo não pode ser vazio!");
        }
        if (cep.length() != 8) {
            sessao.setAttribute("msgErro", "CEP Inválido!");
        }
        if (email == null) {
            sessao.setAttribute("msgErro", "Email Inválido!");
        }
        if (telCelular.length() == 0 && telCelular == null) {
            sessao.setAttribute("msgErro", "Telefone Celular Inválido!");
        }
        //Cria e insere um novo Objeto do tipo endereço no banco de dados
        Endereco e = new Endereco(cep, numero, telFixo);
        try {
            BD_2.add(e);
        } catch (Exception ex) {
            sessao.setAttribute("msgErro", "Erro ao cadastrar o Endereço!");
        }
        //Variáveis necessárias para verificar o CPF e RG já estão cadastrados        
        int verificaCPF = BD_2.getidPessoa(cpf);
        PessoaFisica pf = BD_2.getPessoaFisica(verificaCPF);
        //Verifica se o CPF informado já existe no banco de dados
        if (verificaCPF != 0) {
            sessao.setAttribute("msgErro", "Já existe um cadastro com esse CPF!");
            return "CadastroRespFinFisica.jsp"; 
        }        
        //Pegar o código do endereço cadastrado anteriormente
        e.setcodigo(BD_2.getCodEndereco(new String(e.getcep()), e.getnumero()));
        rff.setendereco(e);
        rff.setnome(nome);
        rff.setdataNasc(dataNasc);
        rff.setcpf(cpf);
        rff.setrg(rg);
        rff.setsexo(sexo);
        rff.setemail(email);
        rff.setcelular(telCelular);
        rff.setendereco(e);
        BD_2.add(rff);
        sessao.setAttribute("msgSucesso", "Cadastro Realizado com Sucesso!");
        return "CadastroRespFinFisica.jsp";
    }
}
