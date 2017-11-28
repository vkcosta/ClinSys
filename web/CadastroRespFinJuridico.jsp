<%-- 
    Document   : CadastroRespFinFisica
    Created on : 22/11/2017, 13:59:16
    Author     : vitor.costa
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Entidades.Pessoa"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    HttpSession sessao = request.getSession();
    user = (Usuario) sessao.getAttribute("usuario");
    if (user == null) {
        response.sendRedirect("index.jsp");
    }
    String login = user.getlogin();


%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Responsável Jurídico</title>
    </head>
    <body>
        <c:import url="menu.jsp"/>
        <br/>
<h2>Novo Responsável Financeiro Jurídico</h2>
<br>
        <form action="Controle">
            <fieldset>
                <legend>Dados Pessoais</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="nome">Razao Social: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="razaoSocial" placeholder="Razao Social">
                        </td>                      


                    </tr>
                    <tr>
                        <td>
                            <label>CNPJ:</label>
                        </td>
                        <td align="left">
                            <input type="text" name="cnpj" size="14" maxlength="14" placeholder="CPF">
                        </td>                        
                    </tr>                    
                </table>
            </fieldset>

            <br />
            <!-- ENDEREÇO -->
            <fieldset>
                <legend>Dados de Endereço</legend>
                <table cellspacing="10">        
                    <tr>
                        <td>
                            <label for="cep">CEP: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="cep" size="5" maxlength="5"> - <input type="text" name="cep2" size="3" maxlength="3">
                        </td>

                        <td>
                            <label for="numero">Numero:</label>
                        </td>
                        <td align="left">
                            <input type="text" name="numero" size="4" placeholder="Número">
                        </td>
                    </tr>
                </table>
            </fieldset>
            <br />

            <!-- CONTATO -->
            <fieldset>
                <legend>Contato</legend>
                <table cellspacing="10">
                    <tr>
                        <td>
                            <label for="email">E-mail: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="email" placeholder="Email">
                        </td>

                        <td>
                            <label for="telFixo">Telefone Fixo:</label>
                        </td>
                        <td>
                            <input type="text" name="telFixo" placeholder="Telefone Fixo">
                        </td>                       
                    </tr>
                </table>
            </fieldset>
            <br />
            <center>
                <input type="hidden" name="logica" value=""/>
                <button >Cadastrar</button>
                <input type="reset" value="Limpar"></center>
        </form>
    </body>
</html>
