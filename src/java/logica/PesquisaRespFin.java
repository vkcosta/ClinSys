package logica;

import DAO.BD_2;
import Entidades.RespFinFisico;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vitor.costa
 */
public class PesquisaRespFin implements Logica {

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        String id = request.getParameter("mostraID");
        sessao.setAttribute("listaRespFin", BD_2.getAllRespFin());
        RespFinFisico rff = new RespFinFisico(); 
        
        if(id == ""){
            sessao.setAttribute("erroRespFin", "Selecione um Responsável Financeiro!");
            return "ConsultaRespFin.jsp"; 
        }
        int idRespFin = Integer.parseInt(id);        
            
               
        //Verifica se o ID informado pelo usuário é diferente de 0
       
        if (idRespFin != 0) {            
            rff = (RespFinFisico) BD_2.getRespFin(idRespFin);
            if (rff != null) { //Verifica se existe um Responsável financeior físico cadastrado no BD.
                sessao.setAttribute("rff", rff);
            } else {
                sessao.setAttribute("erroRespFin", "Responsável Financeiro não Cadastrado!");
            }
        }else{
            sessao.setAttribute("erroRespFin", "Selecione um Responsável Financeiro!");
            return "ConsultaRespFin.jsp";            
        }      
        
        return "ConsultaRespFin.jsp";
        
    }
}
