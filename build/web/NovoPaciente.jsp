<%-- 
    Document   : NovoPaciente
    Created on : 15/11/2017, 13:28:26
    Author     : vito_
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="DAO.BD_2"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    HttpSession sessao = request.getSession();
    user = (Usuario) sessao.getAttribute("usuario");
    String login = user.getlogin();

    if (sessao == null) {
        response.sendRedirect("index.jsp");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CLINSYS - Cadastro de Pacient</title>

        <link rel="stylesheet" type="text/css" href="menu.css" />

    </head>
    <body>
        <!--Importa a página do menu-->
        <c:import url="menu.jsp"/>
        <br/>
        <h2>Novo Paciente</h2>
        <br>
        <!--FORMULÁRIO PARA NOVO PACIENTE-->           
        <c:import url="FormularioCadastro.jsp"/>

    </body>
</html>