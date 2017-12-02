<%-- 
    Document   : AlterarCadastro
    Created on : 01/12/2017, 21:10:48
    Author     : vito_
--%>

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

<script>
    //Função javascript para 'pegar' o nome do Resp. selecionadao e exibir o Resp. ao lado.
    function pegaCadastro() {
        var id = document.getElementById("respFin").value;
        var responsavel = document.getElementById("respFin").options[document.getElementById("respFin").selectedIndex].text;
        
        document.getElementById("mostraID").value = id;
        document.getElementById("mostraRespFin").value = responsavel;
    }
//Terminar aqui

</script>
<!--Fim dos Scritps--> 



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alterar Cadastro</title>
    </head>
    <body>

        <c:import url="menu.jsp"/>

        <br>
        <br>
        <form action="Controle">
            <fieldset>
                <legend>Dados Cadastrados</legend>
                <table cellspacing="10">        
                    <tr>
                    <select id="listaCadastro">
                        <c:forEach var="resp" items="${listaPessoa}">
                            <option value="${resp.idPessoa}" selected="selected">${resp.nome}</option>
                        </c:forEach>
                    </select>
                    <td>
                        <input type="button" onclick="pegaCadastro()" value="Selecionar">
                    </td>
                    <td></td>
                    <td align="center">
                        ID: <input type="text" id="id" name="mostraID" value="" class="form-control" id="disabledInput" disabled="" style="width:25px;" required autofocus>
                    </td>
                    <td>   
                        Nome: <input type="text" id="nome" name="mostraRespFin" value="" class="form-control" id="disabledInput" disabled="" style="width:300px;" >
                    </td>
                    <td>
                        Status <td align="left">
                        <select class="form-control" id="disabledInput" disabled="" name="status"  value="${ListaPessoa.status}">
                            
                            <option value="1">Ativo</option> 
                            
                            <option value="0">Inativo</option>                              
                            
                        </select>
                    </td>
                    </td>

                    </tr>
                </table>
            </fieldset>
            <br>
            <center>
                <input type="hidden" name="logica" value="#"/>

                <button>Alterar Senha</button>
            </center>
        </form>
    </body>
</html>