/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Home
 */
public class BaseDto {
    private int _id;
    private String _name;
    private boolean _status;
    
    public int getId(){
        return this._id;
    }
    
    public void setId(int id){
        this._id = id;
    }
    
    public String getName(){
        return this._name;
    }
    
    public void setName(String name){
        this._name =  name;
    }
    
    public boolean getStatus(){
        return this._status;
    }
    
    public void setStatus(boolean status){
        this._status = status;
    }
    
    public String getStatusDescription(){
        return _status == true?"Ativo":"Inativo";
    }    
}
