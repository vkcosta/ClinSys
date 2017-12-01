/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vito_
 */
public class Logoff implements Logica{

    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sessao = request.getSession();
        sessao.removeAttribute("mensagem");        
        sessao = null;
        return "index.jsp"; 
    }
    
}
