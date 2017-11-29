<%@page import="Entidades.Paciente"%>
<%@page import="Entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    Paciente p = new Paciente();
    HttpSession sessao = request.getSession();
    user = (Usuario) sessao.getAttribute("usuario");
    String login = user.getlogin();

    if (sessao == null) {
        sessao.setAttribute("mensagem", "sessao vazia");
        response.sendRedirect("index.jsp");
    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Paciente</title>
    </head>
    <body>
        <!--Importação do Menu-->
        <c:import url="menu.jsp"/>
        <h2>Consultar Paciente</h2>
        <br>
        <!--Formulário para consulta do paciente-->
        <form action="Controle">
            <fieldset>
                <legend>Consultar Paciente</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="idRespFin"> CPF do Paciente: </label>
                        </td>
                        <td align="left">                            
                            <input pattern="^\d{11}$" title="Somente números" required type="text" name="cpfPaciente" size="11" maxlength="11" placeholder="CPF">
                        </td>
                        <td>
                            <input type="hidden" name="logica" value="ConsultaPaciente"/>
                            <button >Pesquisar</button> 
                        </td>
                    </tr>
                </table>
            </fieldset>           
        </form>

        <br>
            <%
                 p = (Paciente) request.getAttribute("paciente");                
                if( p != null){    
            %>
            
            
    
    <% } else{%>
    paciente não existe
    <% } %>
    </body>
</html>
