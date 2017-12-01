package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vitor.costa
 */
public class AbreAlteraSenha implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        return "AlteraSenha.jsp";
       }
    
}
