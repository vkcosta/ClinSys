<%-- 
    Document   : AlterarCadastro
    Created on : 01/12/2017, 21:10:48
    Author     : vito_
--%>

<%@page import="java.util.List"%>
<%@page import="Entidades.Pessoa"%>
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
    List<Pessoa> lista = (List) sessao.getAttribute("ListaPessoa");
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

                    <% for (int k = 0; k < lista.size(); k++) { %>
                    <tr>
                        <td>
                            
                        </td>
                    </tr>
                    <%}%>

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