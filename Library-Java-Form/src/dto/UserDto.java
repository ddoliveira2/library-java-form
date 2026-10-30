/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Home
 */
public class UserDto extends BaseDto{
    private String _login;
    private String _email;
    private String _password;
    
    public String getLogin(){
        return this._login;
    }
    
    public void setLogin(String login){
        this._login = login;
    }
    
    public String getEmail(){
        return this._email;
    }
    
    public void setEmail(String email){
        this._email = email;
                
    }
    
    public String getPassword(){
        return this._password;
    }
    
    public void setPassword(String password){
        this._password = password;
    }
}
