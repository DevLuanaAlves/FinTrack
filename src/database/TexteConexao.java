/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.SQLException;

public class TexteConexao {
    public static void main(String[] args) {
        
        try(Connection conexao = Conexao.conectar()) {
            System.out.println("Conexao realizada!");
        }
        catch(SQLException erro) {
            System.out.println("Erro ao conectar");
            erro.printStackTrace();
        }
    }
}
