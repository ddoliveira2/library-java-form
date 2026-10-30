/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.util.List;

/**
 *
 * @author Home
 * @param <T>
 */
public interface BllInterface<T> {
 
    public T GetOne();
    public List<T> GetLista();
    public T CreateUpdate();
    public boolean EnableDisable();
    public boolean Delete();
}
