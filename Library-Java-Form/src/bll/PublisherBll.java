/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dal.PublisherDal;
import dto.PublisherDto;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;;

/**
 *
 * @author Home
 */
public class PublisherBll extends MensagensDto implements BllInterface<PublisherDto>{

    private PublisherDto _objPublisher;
    private PublisherDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _description;
    private String _status;
    
    
    public PublisherBll(String id, String name,String description, String status) {
        this._objPublisher = new PublisherDto();
        this._objDal = new PublisherDal();
        this._id = id;
        this._name = name;
        this._description = description;
        this._status = status;
    }  
    
    @Override
    public PublisherDto GetOne() {
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objPublisher.setId(id);
                this. rs = this._objDal.GetOne(this._objPublisher);
                if(rs.getRow() > 0 ){
                    _objPublisher.setId(rs.getInt("id"));
                    _objPublisher.setName(rs.getString("name"));
                    _objPublisher.setStatus(rs.getBoolean("status"));                    
                return _objPublisher;
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
    public List<PublisherDto> GetLista() {
        List<PublisherDto> list = new ArrayList<>();
        
        try{
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objPublisher,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objPublisher,null);
            }    
            
            PublisherDto insert;
            while(this.rs.next()){
                insert = new PublisherDto();
                
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
    public PublisherDto CreateUpdate() {
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }       
        this._objPublisher.setName(this._name);
        this._objPublisher.setDescription(this._description);
        
        int id = 0;
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objPublisher.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objPublisher.setStatus(true);
            }
            else                    
            {
                this._objPublisher.setStatus(false);
            }

            this._objDal.Update(_objPublisher);
            this.setMessage("Publicadora atualizado com sucesso!");
            return this._objPublisher;
        }
        else{            
            this._objPublisher.setStatus(true);            
            id = this._objDal.Create(_objPublisher);            
            this._objPublisher.setId(id);            
            this.setMessage("Publicadora cadastrado com sucesso!");
            return this._objPublisher;
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
                this._objPublisher.setStatus(false);
            }            
            else{
                this._objPublisher.setStatus(true);            
            }
                        
            this._objPublisher.setId(id);                                    
            this._objDal.Update(_objPublisher);
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
            this._objPublisher.setId(id);            
            this._objDal.Delete(_objPublisher);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
}
