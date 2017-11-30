package logica;

import DAO.BD_2;
import Entidades.Endereco;
import Entidades.PessoaFisica;
import Entidades.RespFinJuridico;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vitor.costa
 */
public class CadastraRespFinJuridico implements Logica {
    
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        RespFinJuridico rfj = new RespFinJuridico();
        Endereco e = new Endereco();
        //Recupeção dos dados digitados no formulário
        String razaoSocial = request.getParameter("razaoSocial");        
        char[] cnpj = request.getParameter("cnpj").toCharArray();
        String cep = request.getParameter("cep");
        int numero = Integer.parseInt(request.getParameter("numero"));
        String email = request.getParameter("email");
        String telFixo = request.getParameter("telFixo");

        //Validações dos compos do formulário
        if (razaoSocial == null) {
            sessao.setAttribute("msgErro", "Razão Social não pode ser vazio!");
            return "CadastroRespFinJuridico.jsp";
        }
        if (cnpj.length != 14 && cnpj.length != 0 || cnpj.length == 0) {
            sessao.setAttribute("msgErro", "CNPJ Inválido!");
            return "CadastroRespFinJuridico.jsp";
        }        
        
        if (cep.length() != 8) {
            sessao.setAttribute("msgErro", "CEP Inválido!");
            return "CadastroRespFinJuridico.jsp";
        }
        if (email == null) {
            sessao.setAttribute("msgErro", "Email Inválido!");
            return "CadastroRespFinJuridico.jsp";
        }

        //Cria e insere um novo Objeto do tipo endereço no banco de dados
        if (telFixo.equals("")) {
            e = new Endereco(cep, numero, null);
        } else {
            e = new Endereco(cep, numero, telFixo);
        }        
        try {
            BD_2.add(e);
        } catch (Exception ex) {
            sessao.setAttribute("msgErro", "Erro ao cadastrar o Endereço!");
            return "CadastroRespFinJuridico.jsp";
        }
        /*
        //Variáveis necessárias para verificar o CPF e RG já estão cadastrados        
        int verificaCPF = BD_2.getidPessoa(cpf);
        PessoaFisica pf = BD_2.getPessoaFisica(verificaCPF);
        //Verifica se o CPF informado já existe no banco de dados
        if (verificaCPF != 0) {
            sessao.setAttribute("msgErro", "Já existe um cadastro com esse CPF!");
            return "CadastroRespFinFisica.jsp";
        }
         */

        //Pegar o código do endereço cadastrado anteriormente
        e.setcodigo(BD_2.getCodEndereco(new String(e.getcep()), e.getnumero()));
        rfj.setendereco(e);
        rfj.setnome(razaoSocial);
        rfj.setemail(email);
        rfj.setstatus(true);
        rfj.setcnpj(cnpj);
        BD_2.add(rfj);
        sessao.setAttribute("msgSucesso", "Cadastro Responsável Jurídico Realizado com Sucesso!");
        return "CadastroRespFinJuridico.jsp";
    }
    
}
