/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import DAO.BD_2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vito_
 */
public class AbreAlteraCadastro implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("ListaPessoa", BD_2.getAllPessoa());
        return "AlterarCadastro.jsp";
    }
    
}
