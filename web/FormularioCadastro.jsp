<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Entidades.RespFinFisico"%>
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

    RespFinFisico responsavel = (RespFinFisico) sessao.getAttribute("rff");
    String msg = (String) sessao.getAttribute("msg");
%>

<form action="Controle">
    <fieldset>
        <legend>Responsável Financeiro</legend>
        <table cellspacing="10" >
            <tr>
                <td>
                    <label for="idRespFin"> Responsável Financeiro: </label>
                </td>
                <td align="left">               
                    
                    <select>
                    <c:forEach var="resp" items="${listaRespFin}">
                        <option value="${resp.idPessoa}">${resp.nome}</option>
                    </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="hidden" name="logica" value="PesquisaRespFin"/>
                    <button >Selecionar</button>
                </td>
                <% if(responsavel != null){ %>
                <td>
                    <input class="form-control" id="disabledInput" disabled="" type="text" name="nome" placeholder="Nome" value="<%=responsavel.getnome()%>">
                </td>
                 <%}%>
            </tr>
            
        </table>
    </fieldset>
</form>

<br/>

<form action="Controle">
    <fieldset>
        <legend>Dados Pessoais</legend>
        <table cellspacing="10" >
            <tr>
                <td>
                    <label for="nome">Nome: </label>
                </td>
                <td align="left">
                    <input type="text" name="nome" placeholder="Nome">
                </td>                        

                <td>
                    <label>Nascimento: </label>
                </td>
                <td align="left">
                    <select name="dia" >
                        <option value="" placeholder="Dia"/> </option>
                        <% for (int k = 1; k <= 31; k++) {%>
                        <option value="<%=k%>"/><%=k%> </option>
                        <%}%>
                    </select> 
                    <select name="mes">
                        <option value=""/> </option>
                        <% for (int k = 1; k <= 12; k++) {%>
                        <option value="<%=k%>"/><%=k%> </option>
                        <%}%>
                    </select> 
                    <select name="ano">
                        <option value=""/> </option>
                        <% for (int k = 1960; k <= 2010; k++) {%>
                        <option value="<%=k%>"/><%=k%> </option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label>CPF:</label>
                </td>
                <td align="left">
                    <input type="text" name="cpf" size="11" maxlength="11" placeholder="CPF">
                </td>
                <td>
                    <label for="rg">RG: </label>
                </td>
                <td align="left">
                    <input type="text" name="rg" size="13" maxlength="13" placeholder="RG"> 
                </td>
            </tr>
            <tr>
                <td>
                    <label>Sexo:</label>
                </td>

                <td align="left">
                    <select name="sexo" placeholder="Sexo"> 
                        <option value=""></option> 
                        <option value="M">Masculino</option> 
                        <option value="F">Feminino</option> 
                        <option value="o">Outros</option> 

                    </select>
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

                <td>
                    <label for="celular">Telefone Celular: </label>
                </td>
                <td align="left">
                    <input type="text" name="telCelular" placeholder="Celular">
                </td>
            </tr>
        </table>
    </fieldset>
    <br />
    <center>
        <input type="hidden" name="logica" value="CadastraPaciente"/>
        <button >Cadastrar</button>
        <input type="reset" value="Limpar"></center>
</form>