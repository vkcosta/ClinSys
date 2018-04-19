package logica;

import DAO.BD_2;
import Entidades.Paciente;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConsultaPaciente implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        String cpf = request.getParameter("cpfPaciente");

        HttpSession sessao = request.getSession();
        
       Paciente p = BD_2.getPaciente(cpf);

        
       sessao.setAttribute("paciente", p);
        
        
    return "ConsultaCadastroPaciente.jsp";
    }
}
