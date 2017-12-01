<%@page import="Entidades.RespFinJuridico"%>
<%@page import="Entidades.RespFinFisico"%>
<%@page import="Entidades.PessoaFisica"%>
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

<script>
    //Função javascript para 'pegar' o nome do Resp. selecionadao e exibir o Resp. ao lado.
    function pegaPaciente() {
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
        <title>Consultar Paciente</title>
    </head>
    <body>
        <!--Importação do Menu-->
        <c:import url="menu.jsp"/>
        <h2>Consultar Paciente</h2>
        <br>
        <!--Formulário para consulta do paciente-->
        <form >            
            <!--Dados Pessoais-->
            <fieldset>
                <legend>Dados para Consulta</legend>
                <table cellspacing="10" >
                    <tr>
                        <td>
                            <label for="nome"> Nome: </label>
                        </td>
                        <td align="left">                            
                            <select id="respFin">
                                <c:forEach var="paciente" items="${listaPaciente}">
                                    <option value="${paciente.idPessoa}" selected="selected">${paciente.nome}</option>
                                </c:forEach>
                            </select>    
                        </td>
                        <td>
                            <input type="button" onclick="pegaPaciente()" value="Selecionar">
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
                <input type="hidden" name="logica" value="PesquisaPaciente"/>
                <button >Exibir Dados</button>
            </center>
        </form>         
        <!-- Recupera o Paciente da sessão -->
        <% p = (Paciente) sessao.getAttribute("pa"); %>

        <% if (p != null) {%>

        <!--Dados do Rersp. Financeiro-->
        <fieldset>
            <legend>Responsável Financeiro</legend>
            <table cellspacing="10" >
                <tr>
                    <td>
                        <label for="idRespFin"> ID: </label>
                    </td>
                    <td align="left" width="22px">
                        <input type="text" name="nome" value="<%=p.getRespFin().getidPessoa()%>">
                    </td>  
                    <td></td>
                    <td>
                        <label for="nome">Nome: </label>
                    </td>
                    <td align="left" width="40px">
                        <input type="text" name="nome" value="<%=p.getRespFin().getnome() %>">
                    </td> 
                </tr>
            </table>
        </fieldset>
                    <br>
        <!--Dados do Pessoais-->      
        <fieldset>
            <legend>Dados Pessoais</legend>
            <table cellspacing="10" >
                <tr>
                    <td>
                        <label for="nome">Nome: </label>
                    </td>
                    <td align="left" width="40px">
                        <input type="text" name="nome" value="<%=p.getnome()%>">
                    </td>                        
                    <td></td>

                    <td width="50px">
                        <label>Nascimento: </label>
                    </td>

                    <td align="rigth">
                        <select name="dia" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 31; k++) {%>
                            <% if (k == (p.getdataNascDate().getDay() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                                }%>
                        </select> 
                        <select name="mes" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1; k <= 12; k++) {%>
                            <% if (k == (p.getdataNascDate().getMonth() + 1)) {%>
                            <option value="<%=k%>" selected="selected"/><%=k%> </option>
                            <%} else {%>
                            <option value="<%=k%>"/><%=k%> </option>
                            <%}
                                }%>
                        </select> 
                        <select name="ano" class="form-control" id="disabledInput" disabled="">
                            <% for (int k = 1960; k <= 2010; k++) {%>
                            <% if (k == (p.getdataNascDate().getDate() + 1)) {%>
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
                        <input type="text" name="cpf" size="11"  placeholder="CPF" value="<%=p.getcpf()%>">
                    </td>
                    <td>
                    </td>
                    <td >
                        <label for="rg">RG: </label>
                    </td>
                    <td align="left">
                        <input type="text" name="rg" size="9"  placeholder="RG" value="<%=p.getrg()%>"> 
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Sexo:</label>
                    </td>

                    <td align="left">
                        <select class="form-control" id="disabledInput" disabled="" name="sexo" readonly="readonly" value="<%=p.getsexo()%>">
                            <% if (p.getsexo() == 'M') { %>
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
                        <input type="text" name="cep" size="8" placeholder="CEP" value="<%=p.getendereco().getcep()%>">
                    </td>

                    <td>
                        <label for="numero">Numero:</label>
                    </td>
                    <td align="left">
                        <input required type="text" name="numero" size="4" placeholder="Número" value="<%=p.getendereco().getnumero()%>">
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
                        <input type="email" name="email" placeholder="Email" value="<%=p.getemail()%>">
                    </td>

                    <td>
                        <label for="telFixo">Telefone Fixo:</label>
                    </td>
                    <td>
                        <input type="text" name="telFixo" placeholder="Telefone Fixo" value="<%=p.getendereco().gettelFixo()%>">                            
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


        <%} else {%>
        <% String erroPaciente = (String) sessao.getAttribute("erroPaciente"); %>

        <%if (erroPaciente != null) {%>
        <script>
            var erroRespFin = "<%= erroPaciente%>";
            alert(erroRespFin);
        </script>
        <%}%>
        <%}%>

        <%sessao.removeAttribute("pa");%> 
        <%sessao.removeAttribute("erroPaciente");%> 
    </body>
</html>
