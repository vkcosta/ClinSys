<%-- 
    Document   : Ajuda
    Created on : 15/11/2017, 18:19:03
    Author     : vito_
--%>
<%-- 
    Document   : principal
    Created on : 11/11/2017, 14:59:15
    Author     : vito_
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="DAO.BD_2"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% Usuario user = new Usuario();
    HttpSession sessao = request.getSession();
    user = (Usuario) sessao.getAttribute("usuario");
    String login = user.getlogin();
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

        
        <p><strong>Logar:</strong></p><br>
O usuario deve inserir o Login e Senha nos campos indicados e clicar no botão LOGIN.

<p><strong>Criar Responsavel financeiro:</strong></p><br>
Para realizar um novo cadastro de Responsável, Menu Cadastros -> Responsavel Financeiro -> Escolha entre Físico ou jurídica
Preencha o formulário e clique em Salvar.

<p><strong>Criar Paciente:</strong></p><br>

Para realizar um novo cadastro de Paciente, Menu Cadastros -> Novo Paciente -> Primeiro pesquise o Resposável Financeiro.
Após, preencha o formulario e clique em Salvar.

<p><strong>Ativa\Reativar cadastro:</strong></p><br>
Para realizar uma Ativação\Inativação de um Cadastro, Menu Cadastros -> Ativar\Inativar Cadastros -> Digite o ID e clique em OK.

<p><strong>SAIR:</strong></p><br>
No Menu haverá uma opção com o seu login -> clique Sair.
   
</body>
</html>