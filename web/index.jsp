<%-- 
    Document   : index
    Created on : 13/11/2017, 10:47:11
    Author     : vitor.costa (Full Stack) e Mateus Garcia (Back-end original da versão Desktop)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Clinsys</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="index.css">
        <link rel="stylesheet" type="text/js" href="index.js">
    </head>
    <body>       
        <div class="login-page">
            <div class="form">
                <h1>CLINSYS</h1>
                <p> <h4>Cadastro de paciente</h4></p>            

                <form class="login-form" method="post" action="Controle">
                    <input type="text" name="login" placeholder="Login" required autofocus/>
                    <input type="password" name="senha" placeholder="Senha" required autofocus/>
                    <input type="hidden" name="logica" value="Logar"/>
                    <button >login</button> 

                    <h4>
                        ${mensagem}
                    </h4>

                </form>
            </div>
        </div>
    </body>
</html>
