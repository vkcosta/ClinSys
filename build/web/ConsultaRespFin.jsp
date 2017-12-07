<%-- 
    Document   : ConsultaRespFin
    Created on : 24/11/2017, 16:19:09
    Author     : vitor.costa
--%>

<%@page import="Entidades.RespFinJuridico"%>
<%@page import="DAO.BD_2"%>
<%@page import="Entidades.RespFinFisico"%>
<%@page import="Entidades.Paciente"%>
<%@page import="Entidades.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    RespFinFisico rff = new RespFinFisico();
    RespFinJuridico rfj = new RespFinJuridico();
    HttpSession sessao = request.getSession();
    user = (Usuario) sessao.getAttribute("usuario");
    String login = user.getlogin();

    if (sessao == null) {
        sessao.setAttribute("mensagem", "sessao vazia");
        response.sendRedirect("index.jsp");
    }
%>

<script>
    //Função javascript para 'pegar' o nome do Resp. selecionadao e exibir o Resp. ao lado.
    function pegaRespFin() {
        var id = document.getElementById("respFin").value;
        var responsavel = document.getElementById("respFin").options[document.getElementById("respFin").selectedIndex].text;
        document.getElementById("mostraID").value = id;
        document.getElementById("mostraRespFin").value = responsavel;
    }

</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Resp. Financeiro</title>
    </head>
    <body>
        <!--Importação do Menu-->
        <c:import url="menu.jsp"/>
        <h2>Consultar Resp. Financeiro</h2>
        <br>
        <!--Formulário para consulta do paciente-->
        <form action="Controle" >
            <fieldset>
                <legend>Dados para Consulta</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="idRespFin"> Nome: </label>
                        </td>
                        <td align="left">               

                            <select id="respFin">
                                <c:forEach var="resp" items="${listaRespFin}">
                                    <option value="${resp.idPessoa}" selected="selected">${resp.nome}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input type="button" onclick="pegaRespFin()" value="Selecionar">

                        </td>
                        <td></td>
                        <td align="center">
                            ID: <input type="text" id="mostraID" name="mostraID" style="width:22px;" readonly="readonly">                            
                        </td>

                        <td>   
                            Nome: <input type="text" id="mostraRespFin"  name="mostraRespFin" class="form-control" id="disabledInput" disabled="" style="width:250px;" >
                        </td>
                    </tr>            
                </table>
            </fieldset>  
            <br />
            <center>
                <input type="hidden" name="logica" value="PesquisaRespFin"/>
                <button >Exibir Dados</button>
            </center>
        </form>

        <br>
        <!--Realiza o casing para responsavel financeiro Fidico e exibe os dados-->
        <% try {
                rff = (RespFinFisico) sessao.getAttribute("rf");
            } catch (Exception e) {
                rff = null;
                
            };
        %>

        <%if (rff != null) {
        %>


        <h2>Responsável Financeiro Físico</h2>
        <fieldset>
            <legend>Dados Pessoais</legend>
            <table cellspacing="10" >
                <tr>
                    <td>
                        <label for="id">ID: </label>
                    </td>
                    <td width="20px" >                        
                        <input class="form-control" id="disabledInput" type="text" value="<%=rff.getidPessoa()%>" disabled="">
                    </td>
                    <td></td>
                    <td>
                        <label for="nome">Status: </label>
                    </td>
                    <td width="30px">
                        <% String status;
                            if (rff.getstatus()) {
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
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="nome" placeholder="Nome" value="<%=rff.getnome()%>">
                    </td>                        
                    <td></td>

                    <td width="50px">
                        <label>Nascimento: </label>
                    </td>

                    <td align="rigth">
                        <select name="dia" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 31; k++) {%>
                            <% if (k == (rff.getdataNascDate().getDay() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                                }%>
                        </select> 
                        <select name="mes" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 12; k++) {%>
                            <% if (k == (rff.getdataNascDate().getMonth() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                                }%>
                        </select> 
                        <select name="ano" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1960; k <= 2010; k++) {%>
                            <% if (k == (rff.getdataNascDate().getDate() + 1)) {%>
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
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="cpf" size="11" maxlength="11" placeholder="CPF" value="<%=rff.getcpf()%>">

                    </td>
                    <td>
                    </td>
                    <td >
                        <label for="rg">RG: </label>
                    </td>
                    <td align="left">
                        <input  class="form-control" id="disabledInput" disabled="" pattern="[a-zA-Z0-9]+" title="Formato: 1234567UF" required type="text" name="rg" size="9" maxlength="9" placeholder="RG" value="<%=rff.getrg()%>">                         
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Sexo:</label>
                    </td>

                    <td align="left">
                        <select class="form-control" id="disabledInput" disabled="" name="sexo" pattern="^\d{}$" title="Sexo" readonly="readonly" value="<%=rff.getsexo()%>">
                            <% if (rff.getsexo() == 'M') { %>
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
                        <input class="form-control" id="disabledInput" disabled="" pattern="^\d{8}$" title="Somente números" required type="text" name="cep" size="8" maxlength="8" placeholder="CEP" readonly="readonly" value="<%=rff.getendereco().getcep()%>">
                    </td>

                    <td>
                        <label for="numero">Numero:</label>
                    </td>
                    <td align="left">
                        <input class="form-control" id="disabledInput" disabled="" pattern="^\d{}$" title="Somente números" required type="text" name="numero" size="4" maxlength="7" placeholder="Número" readonly="readonly" value="<%=rff.getendereco().getnumero()%>">
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
                        <input class="form-control" id="disabledInput" disabled="" type="email" name="email" placeholder="Email" value="<%=rff.getemail()%>">
                    </td>

                    <td>
                        <label for="telFixo">Telefone Fixo:</label>
                    </td>
                    <td>
                        <input class="form-control" id="disabledInput" disabled="" type="text" name="telFixo" value="<%=rff.getendereco().gettelFixo()%>">                            
                    </td>

                    <td>
                        <label for="celular">Telefone Celular: </label>
                    </td>
                    <td align="left">
                        <input  class="form-control" id="disabledInput" disabled="" pattern="^\d{11}$" title="Somente números" required type="text" name="telCelular" maxlength="11" placeholder="Celular" value="<%=rff.getcelular()%>">
                    </td>
                </tr>
            </table>
        </fieldset> 
        <%}%>
        
        
        <!--Realiza o casing para responsavel financeiro juridico e exibe os dados-->
        <%
       
            try {
                    rfj = (RespFinJuridico) sessao.getAttribute("rf");
                } catch (Exception ex) {
                    rfj = null;
                };
        
        %>
        
        <%if (rfj != null) {%>
        <!-- Formulário de responsavel juridico-->

        <br/>
        <h2>Responsável Financeiro Jurídico</h2>
        <br>
        <form action="Controle">
            <fieldset>
                <legend>Dados Pessoais</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="nome">Razão Social: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="razaoSocial" placeholder="Razão Social" value="<%=rfj.getnome()%>" disabled="">
                        </td>    
                    </tr>
                    <tr>
                        <td>
                            <label>CNPJ:</label>
                        </td>
                        <td align="left">
                            <input type="text" name="cnpj" size="14" placeholder="CNPJ" value="<%=rfj.getcnpj()%>" disabled="">
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
                            <input type="text" name="cep" size="8" placeholder="CEP" value="<%=rfj.getendereco().getcep()%>" disabled="">
                        </td>

                        <td>
                            <label for="numero">Numero:</label>
                        </td>
                        <td align="left">
                            <input type="text" name="numero" size="4"placeholder="Número" value="<%=rfj.getendereco().getnumero()%>" disabled="">
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
                            <input type="email" name="email" placeholder="Email" value="<%=rfj.getemail()%>" disabled="">
                        </td>
                        <td>
                            <label for="celular">Telefone Fixo: </label>
                        </td>
                        <td align="left">
                            <input type="text" name="telFixo" placeholder="Telefone Fixo" value="<%=rfj.getendereco().gettelFixo()%>" disabled="">

                        </td>
                    </tr>
                </table>
            </fieldset> 
            <%}%>

            <% String erroRespFin = (String) sessao.getAttribute("erroRespFin"); %>

            <%if (erroRespFin != null) {%>
            <script>
                var erroRespFin = "<%=erroRespFin%>";
                alert(erroRespFin);
            </script>
            <%}%>



            <%sessao.removeAttribute("rff"); %>        
            <%sessao.removeAttribute("rfj"); %>        
            <%sessao.removeAttribute("erroRespFin");%>    
    </body>
</html>

