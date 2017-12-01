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
    String msgSucesso = (String)sessao.getAttribute("msgSucesso");
    String msgErro = (String)sessao.getAttribute("msgErro");

%>

<!--Alerta via JavaScript-->
<%if(msgSucesso != null){ %>
         <script>
             var sucesso = "<%=msgSucesso%>";
             
                 alert(sucesso);
             
    </script>
    <%}
    sessao.removeAttribute("msgSucesso");
    sessao.removeAttribute("msgErro");
    %>
   
    <%if(msgErro != null){ %>
         <script>
             var erro = "<%=msgErro%>";             
                 alert(erro);             
        </script>
    <%}%> 
    
<!--Fim dos Scritps--> 

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
                            <label for="nome">*Razão Social: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="razaoSocial" placeholder="Razão Social" required>
                        </td>                      


                    </tr>
                    <tr>
                        <td>
                            <label>*CNPJ:</label>
                        </td>
                        <td align="left">
                            <input type="text" name="cnpj" size="14" maxlength="14" placeholder="CNPJ" pattern="^\d{14}$" title="Somente números" required>
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
                            <label for="cep">*CEP: </label>
                        </td>
                        <td align="left">
                            <input pattern="^\d{8}$" title="Somente números" required type="text" name="cep" size="8" maxlength="8" placeholder="CEP">
                        </td>

                        <td>
                            <label for="numero">*Numero:</label>
                        </td>
                        <td align="left">
                            <input pattern="^\d{}$" title="Somente números" required type="text" name="numero" size="4" maxlength="7" placeholder="Número">
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
                            <label for="email">*E-mail: </label>
                        </td>
                        <td align="left">
                            <input type="email" name="email" placeholder="Email" maxlength="255" required>
                        </td>
                        <td>
                            <label for="celular">*Telefone Fixo: </label>
                        </td>
                        <td align="left">
                            <input  pattern="^\d{10}$" title="Somente números" required type="text" name="telFixo" maxlength="10" placeholder="Telefone Fixo">
                            <font size="2">Formato: DD+Número</font>
                        </td>
                    </tr>
                </table>
            </fieldset>            
            <h5><i>* Preenchimento obrigatório</i></h5>
            <br />
            <center>         

                <input type="hidden" name="logica" value="CadastraRespFinJuridico"/>

                <button >Salvar</button>
                <input type="reset" value="Limpar"></center>
        </form>
    </body>
</html>
