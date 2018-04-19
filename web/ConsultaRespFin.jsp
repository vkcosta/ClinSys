<%-- 
    Document   : ConsultaRespFin
    Created on : 24/11/2017, 16:19:09
    Author     : vitor.costa
--%>

<%@page import="Entidades.RespFinFisico"%>
<%@page import="Entidades.Paciente"%>
<%@page import="Entidades.Usuario"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    RespFinFisico responsavel = new RespFinFisico();
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
        <title>Consultar Resp. Finaneiro</title>
    </head>
    <body>
        <!--Importação do Menu-->
        <c:import url="menu.jsp"/>
        <h2>Consultar Resp. Financeiro</h2>
        <br>
        <!--Formulário para consulta do paciente-->
        <form action="Controle">
            <fieldset>
                <legend>Consultar Responsável Financeiro</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="idRespFin"> ID do Responsável Financeiro: </label>
                        </td>
                        <td align="left">
                            <input type="text" pattern="^\d{}$" title="Somente número" name="idRespFin" required autofocus >
                        </td>
                        <td>
                            <input type="hidden" name="logica" value="PesquisaRespFin"/>
                            <button >Pesquisar</button> 
                        </td>
                    </tr>
                </table>
            </fieldset>           
        </form>

        <br>
        <% responsavel = (RespFinFisico) sessao.getAttribute("rff");
        %>
        <%if (responsavel != null) {%>

        <fieldset>
            <legend>Dados Pessoais</legend>
            <table cellspacing="10" >
                <tr>
                    <td>
                        <label for="id">ID: </label>
                    </td>
                    <td width="20px" >                        
                        <input class="form-control" id="disabledInput" type="text" value="<%=responsavel.getidPessoa()%>" disabled="">
                    </td>
                    <td></td>
                    <td>
                        <label for="nome">Status: </label>
                    </td>
                    <td width="30px">
                        <% String status;
                            if (responsavel.getstatus()) {
                                status = "Ativo";
                            } else {
                                status = "Inativo";
                            }
                        %>
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="id" value="<%=status%>">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="nome">Nome: </label>
                    </td>
                    <td align="left" width="40px">
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="nome" placeholder="Nome" value="<%=responsavel.getnome()%>">
                    </td>                        
                    <td></td>

                    <td width="50px">
                        <label>Nascimento: </label>
                    </td>

                    <td align="rigth">
                        <select name="dia" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 31; k++) {%>
                            <% if (k == (responsavel.getdataNascDate().getDay() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                             }%>
                        </select> 
                        <select name="mes" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 12; k++) {%>
                            <% if (k == (responsavel.getdataNascDate().getMonth() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                             }%>
                        </select> 
                        <select name="ano" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1960; k <= 2010; k++) {%>
                            <% if (k == (responsavel.getdataNascDate().getDate() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                             }%>
                        </select>

                    </td>                        
                </tr>
                <tr>
                    <td>
                        <label>CPF:</label>
                    </td>
                    <td align="left">
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="cpf" size="11" maxlength="11" placeholder="CPF" value="<%=responsavel.getcpf()%>">

                    </td>
                    <td>
                    </td>
                    <td >
                        <label for="rg">RG: </label>
                    </td>
                    <td align="left">
                        <input  class="form-control" id="disabledInput" disabled="" pattern="[a-zA-Z0-9]+" title="Formato: 1234567UF" required type="text" name="rg" size="9" maxlength="9" placeholder="RG" value="<%=responsavel.getrg()%>">                         
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Sexo:</label>
                    </td>

                    <td align="left">
                        <select class="form-control" id="disabledInput" disabled="" name="sexo" pattern="^\d{}$" title="Sexo" readonly="readonly" value="<%=responsavel.getsexo()%>">
                            <% if (responsavel.getsexo() == 'M') { %>
                            <option value="M">Masculino</option> 
                            <%} else {%>
                            <option value="F">Feminino</option>                              
                            <%}%>
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
                        <label for="cep">CEP: </label>
                    </td>
                    <td align="left">
                        <input class="form-control" id="disabledInput" disabled="" pattern="^\d{8}$" title="Somente números" required type="text" name="cep" size="8" maxlength="8" placeholder="CEP" readonly="readonly" value="<%=responsavel.getendereco().getcep()%>">
                    </td>

                    <td>
                        <label for="numero">Numero:</label>
                    </td>
                    <td align="left">
                        <input class="form-control" id="disabledInput" disabled="" pattern="^\d{}$" title="Somente números" required type="text" name="numero" size="4" maxlength="7" placeholder="Número" readonly="readonly" value="<%=responsavel.getendereco().getnumero()%>">
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
                        <input class="form-control" id="disabledInput" disabled="" type="email" name="email" placeholder="Email" value="<%=responsavel.getemail()%>">
                    </td>

                    <td>
                        <label for="telFixo">Telefone Fixo:</label>
                    </td>
                    <td>
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="telFixo" value="<%=responsavel.getendereco().gettelFixo()%>">                            
                    </td>

                    <td>
                        <label for="celular">Telefone Celular: </label>
                    </td>
                    <td align="left">
                        <input  class="form-control" id="disabledInput" disabled="" pattern="^\d{11}$" title="Somente números" required type="text" name="telCelular" maxlength="11" placeholder="Celular" value="<%=responsavel.getcelular()%>">
                    </td>
                </tr>
            </table>
        </fieldset> 
        <%} else {%>
        <% String erroRespFin = (String) sessao.getAttribute("erroRespFin"); %>

        <%if (erroRespFin != null) {%>
        <script>
            var erroRespFin = "<%= erroRespFin%>";
            alert(erroRespFin);
        </script>
        <%}%>
        <%}%>           
        <%sessao.removeAttribute("rff"); %>        
        <%sessao.removeAttribute("erroRespFin");%>        
    </body>
</html>

