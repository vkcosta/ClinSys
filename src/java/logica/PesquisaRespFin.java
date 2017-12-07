package logica;

import DAO.BD_2;
import Entidades.Pessoa;
import Entidades.RespFinFisico;
import Entidades.RespFinJuridico;
import java.util.List;
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
        List responsavel = BD_2.getAllRespFin();
        sessao.setAttribute("listaRespFin", responsavel);

        if (id == "") {
            sessao.setAttribute("erroRespFin", "Selecione um Responsável Financeiro!");
            return "ConsultaRespFin.jsp";
        }
        int idRespFin = Integer.parseInt(id);

        //Diferencia o Responsavel financeiro, se é fisico ou juridico
        Pessoa rf = new Pessoa();
        if (idRespFin != 0) {
            for (int k = 0; k < responsavel.size(); k++) {
                rf = (Pessoa) responsavel.get(k);
                if (rf.getidPessoa() == idRespFin) {
                    try {
                        if (rf instanceof RespFinFisico) {
                            rf = (RespFinFisico) rf;
                            sessao.setAttribute("rf", rf);
                            return "ConsultaRespFin.jsp";
                        } else {
                            rf = (RespFinJuridico) rf;
                            sessao.setAttribute("rf", rf);
                            return "ConsultaRespFin.jsp";
                        }
                    } catch (Exception e) {
                        System.out.println("Erro no casting");
                    };
                }
            }
        } else {
            sessao.setAttribute("erroRespFin", "Selecione um Responsável Financeiro!");
            return "ConsultaRespFin.jsp";
        }

        return "ConsultaRespFin.jsp";

    }
}
