/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dal.AuthorDal;
import dto.AuthorDto;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Home
 */
public class AuthorBll extends MensagensDto implements BllInterface<AuthorDto> {

    private AuthorDto _objAuthor;
    private AuthorDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _description;
    private String _status;
    
    
    public AuthorBll(String id, String name,String description, String status) {
        this._objAuthor = new AuthorDto();
        this._objDal = new AuthorDal();
        this._id = id;
        this._name = name;
        this._description = description;
        this._status = status;
    }
    
    @Override
    public AuthorDto GetOne() {        
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objAuthor.setId(id);
                this. rs = this._objDal.GetOne(this._objAuthor);
                if(rs.getRow() > 0 ){
                    _objAuthor.setId(rs.getInt("id"));
                    _objAuthor.setName(rs.getString("name"));
                    _objAuthor.setStatus(rs.getBoolean("status"));                    
                return _objAuthor;
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
    public List<AuthorDto> GetLista() {        
        List<AuthorDto> list = new ArrayList<>();
        
        try{
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objAuthor,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objAuthor,null);
            }    
            
            AuthorDto insert;
            while(this.rs.next()){
                insert = new AuthorDto();
                
                insert.setId(this.rs.getInt("id"));
                insert.setName(this.rs.getString("name"));
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
    public AuthorDto CreateUpdate() {
        
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }       
        this._objAuthor.setName(this._name);
        this._objAuthor.setDescription(this._description);
        
        int id = 0;
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objAuthor.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objAuthor.setStatus(true);
            }
            else                    
            {
                this._objAuthor.setStatus(false);
            }

            this._objDal.Update(_objAuthor);
            this.setMessage("Autor atualizado com sucesso!");
            return this._objAuthor;
        }
        else{            
            this._objAuthor.setStatus(true);            
            id = this._objDal.Create(_objAuthor);            
            this._objAuthor.setId(id);            
            this.setMessage("Autor cadastrado com sucesso!");
            return this._objAuthor;
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
                this._objAuthor.setStatus(false);
            }            
            else{
                this._objAuthor.setStatus(true);            
            }
                        
            this._objAuthor.setId(id);                                    
            this._objDal.Update(_objAuthor);
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
            this._objAuthor.setId(id);            
            this._objDal.Delete(_objAuthor);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
}
