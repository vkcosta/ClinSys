/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import DAO.BD_2;
import Entidades.Paciente;
import Entidades.Pessoa;
import Entidades.RespFinFisico;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aluno
 */
public class PesquisaPaciente implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        String id = request.getParameter("mostraID");
        sessao.setAttribute("listaRespFin", BD_2.getAllPaciente());
        Paciente pa = new Paciente(); 
        
        if(id == ""){
            sessao.setAttribute("erroPaciente", "Selecione um Paciente!");
            return "ConsultaCadastroPaciente.jsp"; 
        }
        int idPaciente = Integer.parseInt(id);        
            
               
        //Verifica se o ID informado pelo usuário é diferente de 0
       
        if (idPaciente != 0) {      
            pa = BD_2.getPaciente(idPaciente);
            pa.setRespFin((Pessoa)BD_2.getRespFin(idPaciente));
            if (pa != null) { //Verifica se existe um Responsável financeior físico cadastrado no BD.
                sessao.setAttribute("pa", pa);
            } else {
                sessao.setAttribute("erroPaciente", "Paciente não Cadastrado!");
            }
        }else{
            sessao.setAttribute("erroPaciente", "Selecione um Paciente!");
            return "ConsultaCadastroPaciente.jsp";            
        }      
        
        return "ConsultaCadastroPaciente.jsp";
    }
    
}
