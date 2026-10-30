/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.sql.ResultSet;

/**
 *
 * @author Home
 * @param <T>
 */
public interface DalInterface<T> {
    
    public ResultSet GetOne(T obj);
    
    public ResultSet GetList(T obj,Boolean status);
    
    public int Create(T obj);
    
    public void Update(T obj);
    
    public void Delete(T obj);
    
    public boolean Exists(T obj);
        
}
