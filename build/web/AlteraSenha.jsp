<%-- 
    Document   : AlteraSenha
    Created on : 01/12/2017, 17:17:29
    Author     : vitor.costa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sessao = request.getSession();
    String resposta = (String) sessao.getAttribute("resposta");
%>

<%if (resposta != null) {%>
<script>
    var sucesso = "<%=resposta%>";
    alert(sucesso);
</script>
<%}%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Senha de Acesso</title>
    </head>
    <body>

        <c:import url="menu.jsp"/>

        <br>
        <br>
        <form action="Controle">
        <fieldset>
            <legend>Nova Senha</legend>
            <table cellspacing="10">        
                <tr>
                    <td algn="left">
                        Digite a nova Senha:
                    </td>
                    <td align="left">
                        <input type="password" name="novaSenha" size="25" placeholder="Nova Senha" required autofocus>
                    </td>
                </tr>
            </table>
        </fieldset>
        <br>
    <center>
        <input type="hidden" name="logica" value="AlterarSenha"/>

        <button>Alterar Senha</button>
    </center>
        </form>
</body>
</html>

<% sessao.removeAttribute("resposta");%>