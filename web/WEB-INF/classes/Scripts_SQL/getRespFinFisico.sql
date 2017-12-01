/**
 * Author:  Mateus Garcia
 * Created: 05/10/2017
 */
DELIMITER $$

CREATE PROCEDURE getRespFinFisico (IN id INT)
BEGIN
    SELECT endereco,nome,email,status,dataNasc,rg,cpf,sexo,celular,cep,numero,telFixo
    FROM Pessoa JOIN PessoaFisica
        ON Pessoa.id = PessoaFisica.id JOIN RespFinFisico
            ON RespFinFisico.id = Pessoa.id JOIN Endereco
                ON Endereco.codigo = endereco
    WHERE Pessoa.id = id;
END $$

DELIMITER $$;