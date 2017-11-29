
/**
 * Author:  Mateus Garcia
 * Created: 05/10/2017
 */

DELIMITER $$

CREATE PROCEDURE getRespFinJuridico (IN id INT)
BEGIN
    SELECT endereco,nome,email,status,cnpj,cep,numero,telFixo
    FROM Pessoa JOIN RespFinJuridico
        ON Pessoa.id = RespFinJuridico.id JOIN Endereco
            ON endereco = Endereco.codigo
    WHERE Pessoa.id = id;
END $$

DELIMITER $$;