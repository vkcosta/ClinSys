/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import DAO.BD_2;
import Entidades.Paciente;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aluno
 */
public class PesquisaPaciente implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        String id = request.getParameter("mostraID");
        List paciente = BD_2.getAllPaciente();
        sessao.setAttribute("listaPaciente", paciente);
        Paciente pa = new Paciente();

        if (id.equals("")) {
            sessao.setAttribute("erroPaciente", "Selecione um Paciente!");
            return "ConsultaCadastroPaciente.jsp";
        }
        int idPaciente = Integer.parseInt(id);

        //Verifica se o ID informado pelo usuário é diferente de 0
        if (idPaciente != 0) {
            //Verificar se o paciente selecionado existe
            for (int k = 0; k < paciente.size(); k++) {
                pa = (Paciente) paciente.get(k);
                if (pa != null) {
                    if (pa.getidPessoa() == idPaciente) {
                        sessao.setAttribute("pa", pa);
                        return "ConsultaCadastroPaciente.jsp";
                    }
                }
            } 

        } else {
            sessao.setAttribute("erroPaciente", "Selecione um Paciente!");
            
        }

        return "ConsultaCadastroPaciente.jsp";
    }

}
