<%-- 
    Document   : AlteraSenha
    Created on : 01/12/2017, 17:17:29
    Author     : vitor.costa
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <fieldset>
        <legend>Nova Senha</legend>
        <table cellspacing="10">        
            <tr>
                <td algn="left">
                    Digite a nova Senha:
                </td>
                <td align="left">
                    <input required type="text" name="novaSenha" size="25" placeholder="Nova Senha">
                </td>
            </tr>
        </table>
    </fieldset>
    </body>
</html>
