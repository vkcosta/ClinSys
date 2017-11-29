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
        int idRespFin = Integer.parseInt(request.getParameter("idRespFin"));
        HttpSession sessao = request.getSession();
        RespFinFisico rff = new RespFinFisico();        
        //Verifica se o ID informado pelo usuário é diferente de 0
        if (idRespFin != 0) {            
            rff = (RespFinFisico) BD_2.getRespFin(idRespFin);
            if (rff != null) { //Verifica se existe um Responsável financeior físico cadastrado no BD.
                sessao.setAttribute("rff", rff);
            } else {
                sessao.setAttribute("erroRespFin", "Responsável Financeiro não Cadastrado!");
            }
        }else{
            sessao.setAttribute("erroRespFin", "O ID não pode ser 0");
        }      
        
        return "ConsultaRespFin.jsp";
        
    }
}
