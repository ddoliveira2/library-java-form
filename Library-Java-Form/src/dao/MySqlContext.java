/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Home
 */
public  class MySqlContext {
    private static final String URL = "jdbc:mysql://localhost:3306/library_mysql";
    private static final String USUARIO = "root";
    private static final String SENHA = "123456";
    
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    public void ExecutaSql(String sql){
        try{           
            conectar().prepareStatement(sql).execute();
        }
        catch(SQLException e){
            System.out.println("Ocorreu um sql erro: " + e.getMessage());
        }
    }

     // Executar SELECT e retornar os resultados
    public  ResultSet Consultar(String sql){
        try{            
            return conectar().prepareStatement(sql).executeQuery();
        }
        catch(SQLException e){
            System.out.println("Ocorreu um sql erro: " + e.getMessage());
            return null;
        }        
    }
}
