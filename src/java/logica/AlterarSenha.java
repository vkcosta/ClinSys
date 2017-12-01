/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import DAO.BD_2;
import Entidades.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vito_
 */
public class AlterarSenha implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        String senha = request.getParameter("novaSenha");
        Usuario user = (Usuario) sessao.getAttribute("usuario");

        if (senha == null) {
            sessao.setAttribute("resposta", "O campo senha não pode ser vazio! Digite uma senha.");
            return "AlteraSenha.jsp";
        }
        if (senha.length() > 255) {
            sessao.setAttribute("resposta", "Senha digitada inválida! Digite uma nova senha.");
            return "AlteraSenha.jsp";
        }
        if (user == null) {
            sessao.setAttribute("resposta", "Problemas com a sessão do usuário. Faça login novamente!");
            return "AlteraSenha.jsp";
        }

        try {

            if (BD_2.updateSenha(user.getidPessoa(), senha)) {
                sessao.setAttribute("resposta", "Senha alterada com sucesso!");
                return "AlteraSenha.jsp";
            }
            
        } catch (Exception e) {
            sessao.setAttribute("resposta", "Erro ao alterar a senha!");
            System.out.println("Não foi possível alterar a senha no Banco de Dados");
            return "AlteraSenha.jsp";
        }
        
        return "AlteraSenha.jsp";
    }
}

