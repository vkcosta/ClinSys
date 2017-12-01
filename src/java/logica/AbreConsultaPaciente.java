
package logica;

import DAO.BD_2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vito_
 */
public class AbreConsultaPaciente implements Logica{
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("listaPaciente", BD_2.getAllPaciente());
        return "ConsultaCadastroPaciente.jsp";
    }
}
