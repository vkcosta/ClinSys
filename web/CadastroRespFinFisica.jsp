<%-- 
    Document   : CadastroRespFinFisica
    Created on : 22/11/2017, 13:59:16
    Author     : vitor.costa
--%>

<%@page import="Entidades.RespFinFisico"%>
<%@page import="Entidades.Endereco"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    String msgSucesso = (String) sessao.getAttribute("msgSucesso");
    String msgErro = (String) sessao.getAttribute("msgErro");

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
        <title>Responsável Fisico</title>
    </head>
    <body>
     
                
       
        <c:import url="menu.jsp"/>
        <br/>
        <h2>Novo Responsável Financeiro Físico</h2>
        <br>
        <form action="Controle">
            <fieldset>
                <legend>Dados Pessoais</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="nome">*Nome: </label>
                        </td>
                        <td align="left" width="40px">
                            <input type="text" name="nome" placeholder="Nome" required autofocus>
                        </td>                        
                        <td></td>

                        <td width="50px">
                            <label>*Nascimento: </label>
                        </td>
                            
                        <td align="rigth">
                            <select name="dia" pattern="^\d{2}$" title="Dia" required>
                                <option value="" placeholder="Dia"/> </option>
                                <% for (int k = 1; k <= 31; k++) {%>
                                <option value="<%=k%>"/><%=k%> </option>
                                <%}%>
                            </select> 
                            <select name="mes" pattern="^\d{2}$" title="Mês" required>
                                <option value=""/> </option>
                                <% for (int k = 1; k <= 12; k++) {%>
                                <option value="<%=k%>"/><%=k%> </option>
                                <%}%>
                            </select> 
                            <select name="ano" pattern="^\d{4}$" title="Ano" required>
                                <option value=""/> </option>
                                <% for (int k = 1960; k <= 2010; k++) {%>
                                <option value="<%=k%>"/><%=k%> </option>
                                <%}%>
                            </select>

                        </td>                     
                    </tr>
                    <tr>
                        <td>
                    <label>*CPF:</label>
                    </td>
                    <td align="left">
                        <input pattern="^\d{11}$" title="Somente números" required type="text" name="cpf" size="11" maxlength="11" placeholder="CPF">

                    </td>
                    <td>
                    </td>
                    <td >
                        <label for="rg">*RG: </label>
                    </td>
                    <td align="left">
                        <input  pattern="[a-zA-Z0-9]+" title="Formato: 1234567UF" required type="text" name="rg" size="9" maxlength="9" placeholder="RG"> 

                        <font size="2">Formato: 1234567UF</font>
                    </td>
                    </tr>
                    <tr>
                        <td>
                            <label>*Sexo:</label>
                        </td>

                        <td align="left">
                            <select name="sexo" pattern="^\d{}$" title="Sexo" required>
                                <option value=""></option> 
                                <option value="M">Masculino</option> 
                                <option value="F">Feminino</option>                              

                            </select>
                        </td>
                        <td>
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
                            <input type="email" name="email" placeholder="Email" required>
                        </td>

                        <td>
                            <label for="telFixo">Telefone Fixo:</label>
                        </td>
                        <td>
                            <input type="text" name="telFixo" placeholder="Telefone Fixo">                            
                        </td>

                        <td>
                            <label for="celular">*Telefone Celular: </label>
                        </td>
                        <td align="left">
                            <input  pattern="^\d{11}$" title="Somente números" required type="text" name="telCelular" maxlength="11" placeholder="Celular">
                            <font size="2">Formato: DD+Número</font>
                        </td>
                    </tr>
                </table>
            </fieldset>            
            <h5><i>* Preenchimento obrigatório</i></h5>
            <br />
            <center>
                <input type="hidden" name="logica" value="CadastraRespFinFisico"/>
                <button >Salvar</button>
                <input type="reset" value="Limpar"></center>
        </form>
    </body>
</html>
