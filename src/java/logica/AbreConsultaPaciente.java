
package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vito_
 */
public class AbreConsultaPaciente implements Logica{
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        return "ConsultaCadastroPaciente.jsp";
    }
}
