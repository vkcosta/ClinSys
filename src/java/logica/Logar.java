package logica;

import DAO.BD_2;
import Entidades.Usuario;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vito_
 */
public class Logar implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        HttpSession sessao = request.getSession();

        Usuario user = BD_2.getUsuario(login);

        if (user != null) {
            if (user.getlogin().equals(login) && user.getsenha().equals(senha)) {
                if (user.getstatus()) {

                    sessao.setAttribute("usuario", user);
                    sessao.setAttribute("ultimoLogin", BD_2.lastLogin(user));
                    return "principal.jsp";

                } else {
                    sessao.setAttribute("mensagem", "Usuário Inativo!");
                    return "index.jsp";

                }
            } else {
                sessao.setAttribute("mensagem", "Login ou Senha Incorretos!");
                return "index.jsp";
            }
        } else {
            sessao.setAttribute("mensagem", "Usuário não Cadastrado!");
            return "index.jsp";

        }
    }
}
