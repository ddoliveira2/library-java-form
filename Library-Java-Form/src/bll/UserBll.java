/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dal.UserDal;
import dto.UserDto;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Home
 */
public class UserBll extends MensagensDto implements BllInterface<UserDto>{
    
    private UserDto _objUser;
    private UserDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _login;
    private String _email;
    private String _status;
    
    
    public UserBll(String id, String name,String login,String email, String status) {
        this._objUser = new UserDto();
        this._objDal = new UserDal();
        this._id = id;
        this._name = name;
        this._login = login;
        this._email = email;
        this._status = status;
    }    

    @Override
    public UserDto GetOne() {
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objUser.setId(id);
                this. rs = this._objDal.GetOne(this._objUser);
                if(rs.getRow() > 0 ){
                    _objUser.setId(rs.getInt("id"));
                    _objUser.setName(rs.getString("name"));
                    _objUser.setLogin(rs.getString("login"));
                    _objUser.setEmail(rs.getString("email"));                    
                    _objUser.setStatus(rs.getBoolean("status"));                    
                return _objUser;
                }
            }
            catch(NumberFormatException ex)
            {
                setMessage(ex.getMessage());
            }                                
        }
        catch(SQLException ex){
            setMessage(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<UserDto> GetLista() {
        List<UserDto> list = new ArrayList<>();
        
        try{
            this._objUser.setName(this._name);
            this._objUser.setLogin(this._login);
            this._objUser.setEmail(this._email);
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objUser,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objUser,null);
            }    
            
            UserDto insert;
            while(this.rs.next()){
                insert = new UserDto();
                
                insert.setId(this.rs.getInt("id"));
                insert.setName(this.rs.getString("name"));
                insert.setLogin(rs.getString("login"));
                insert.setEmail(rs.getString("email")); 
                insert.setStatus(this.rs.getBoolean("status"));
                
                list.add(insert);
            }
            return list;
        }
        catch(SQLException ex){
            this.setMessage(ex.getMessage());
            return list;
        }
    }

    @Override
    public UserDto CreateUpdate() {
        
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }   
        
        if(this._login.isBlank() || this._login.isEmpty()){
            this.setMessage("Login não foi informado");
            return null;
        } 

        if(this._email.isBlank() || this._email.isEmpty()){
            this.setMessage("Email não foi informado");
            return null;
        } 
        
        this._objUser.setName(this._name);
        
        int id = 0;
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objUser.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objUser.setStatus(true);
            }
            else                    
            {
                this._objUser.setStatus(false);
            }

            this._objDal.Update(_objUser);
            this.setMessage("Usuário atualizado com sucesso!");
            return this._objUser;
        }
        else{            
            this._objUser.setStatus(true);            
            id = this._objDal.Create(_objUser);            
            this._objUser.setId(id);            
            this.setMessage("Usuário cadastrado com sucesso!");
            return this._objUser;
        }
    }

    @Override
    public boolean EnableDisable() {
        if(this._id.isBlank() || this._id.isEmpty() || this._id.trim().length() == 0){
            this.setMessage("Id não foi informado");
            return false;
        }
        
        if(this._status.isBlank() || this._status.isEmpty() || this._status.trim().length() == 0){
            this.setMessage("Status não foi informado");
            return false;
        }            
        
        try
        {
            int id = Integer.parseInt(this._id);
            
            if(this._status.trim().toUpperCase().equals("ATIVO")){
                this._objUser.setStatus(false);
            }            
            else{
                this._objUser.setStatus(true);            
            }
                        
            this._objUser.setId(id);                                    
            this._objDal.Update(_objUser);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean Delete() {
        if(this._id.isBlank() || this._id.isEmpty() || this._id.trim().length() == 0){
            this.setMessage("Id não foi informado");
            return false;
        }
        
        try
        {
            int id = Integer.parseInt(this._id);
            this._objUser.setId(id);            
            this._objDal.Delete(_objUser);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
}
