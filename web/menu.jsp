<%-- 
    Document   : menu_
    Created on : 16/11/2017, 21:00:47
    Author     : Aluno
--%>

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

<link rel="stylesheet" type="text/css" href="menu.css" />
<link rel="shortcut icon" href="favicon.ico" />

<div id="logo" >
    <img src="logo.png" alt="Logo" width="300" heigth="400">    
</div>

<nav>
    <ul>
        <% if (user.getlogin().equals("admin")) { %>
        <li><a href="#">Adm. do Sistema</a>
            <ul>
                <li><a href="#">Novo Usuário</a></li>
                <li><a href="principal.jsp">Logs de acesso</a></li>
            </ul>
        </li>
        <% }%>
        <li><a href="#">Cadastros</a>
            <ul>
                <li><a href="Controle?logica=AbreCadastroPaciente">Novo Paciente</a></li>
                <li><a href="#">Novo Responsável Financeiro</a>
                    <ul>
                        <li><a href="Controle?logica=AbreCadastroRespFinFisica">Físico</a></li>                            
                        <li><a href="Controle?logica=AbreCadastroRespFinJuridico">Jurídico</a></li>
                    </ul>
                </li>
                <li><a href="#">Ativar/ Inativar Cadastros</a></li>
                <li><a href="Controle?logica=AbreAlteraSenha">Alterar Senha de Acesso</a></li>
                <!--<li><a href="#">Visualizar todos os cadastros</a></li>-->
            </ul>                   
        </li>
        <li><a href="#">Consultas</a>
            <ul>
                <li><a href="Controle?logica=AbreConsultaPaciente">Paciente</a></li>                            
                <li><a href="Controle?logica=AbreConsultaRespFin">Responsável Financeiro</a></li>
            </ul>
        </li>
        <li><a href="#">Ajuda</a>
            <ul>
                <li><a href="Controle?logica=Manual">Manual</a></li>
                <li><a href="Controle?logica=Sobre">Sobre o ClinSys</a></li>
            </ul>
        </li>
        <li><a ><%= user.getnome()%></a>
            <ul>                    

                <li><a href="Controle?logica=Logoff"> Sair</a></li>

            </ul>
        </li>
    </ul>
</nav>

