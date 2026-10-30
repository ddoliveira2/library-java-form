/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dal.CategoryDal;
import dto.CategoryDto;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Home
 */
public class CategoryBll extends MensagensDto implements BllInterface<CategoryDto>{
    private CategoryDto _objCategory;
    private CategoryDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _description;
    private String _status;
    
    
    public CategoryBll(String id, String name,String description, String status) {
        this._objCategory = new CategoryDto();
        this._objDal = new CategoryDal();
        this._id = id;
        this._name = name;
        this._description = description;
        this._status = status;
    }
    @Override
    public CategoryDto GetOne() {
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objCategory.setId(id);
                this. rs = this._objDal.GetOne(this._objCategory);
                if(rs.getRow() > 0 ){
                    _objCategory.setId(rs.getInt("id"));
                    _objCategory.setName(rs.getString("name"));
                    _objCategory.setStatus(rs.getBoolean("status"));                    
                return _objCategory;
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
    public List<CategoryDto> GetLista() {
        List<CategoryDto> list = new ArrayList<>();
        
        try{
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objCategory,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objCategory,null);
            }    
            
            CategoryDto insert;
            while(this.rs.next()){
                insert = new CategoryDto();
                
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
    public CategoryDto CreateUpdate() {
        
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }       
        this._objCategory.setName(this._name);
        this._objCategory.setDescription(this._description);
        
        int id = 0;
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objCategory.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objCategory.setStatus(true);
            }
            else                    
            {
                this._objCategory.setStatus(false);
            }

            this._objDal.Update(_objCategory);
            this.setMessage("Categoria atualizado com sucesso!");
            return this._objCategory;
        }
        else{            
            this._objCategory.setStatus(true);            
            id = this._objDal.Create(_objCategory);            
            this._objCategory.setId(id);            
            this.setMessage("Categoria cadastrado com sucesso!");
            return this._objCategory;
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
                this._objCategory.setStatus(false);
            }            
            else{
                this._objCategory.setStatus(true);            
            }
                        
            this._objCategory.setId(id);                                    
            this._objDal.Update(_objCategory);
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
            this._objCategory.setId(id);            
            this._objDal.Delete(_objCategory);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
}
