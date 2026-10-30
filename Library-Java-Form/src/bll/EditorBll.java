/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dal.EditorDal;
import dto.EditorDto;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Home
 */
public class EditorBll extends MensagensDto implements BllInterface<EditorDto>{
    private EditorDto _objEditor;
    private EditorDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _description;
    private String _status;
    
    
    public EditorBll(String id, String name,String description, String status) {
        this._objEditor = new EditorDto();
        this._objDal = new EditorDal();
        this._id = id;
        this._name = name;
        this._description = description;
        this._status = status;
    }
    @Override
    public EditorDto GetOne() {
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objEditor.setId(id);
                this. rs = this._objDal.GetOne(this._objEditor);
                if(rs.getRow() > 0 ){
                    _objEditor.setId(rs.getInt("id"));
                    _objEditor.setName(rs.getString("name"));
                    _objEditor.setStatus(rs.getBoolean("status"));                    
                return _objEditor;
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
    public List<EditorDto> GetLista() {
        List<EditorDto> list = new ArrayList<>();
        
        try{
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objEditor,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objEditor,null);
            }    
            
            EditorDto insert;
            while(this.rs.next()){
                insert = new EditorDto();
                
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
    public EditorDto CreateUpdate() {
        
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }       
        this._objEditor.setName(this._name);
        this._objEditor.setDescription(this._description);
        
        int id = 0;
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objEditor.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objEditor.setStatus(true);
            }
            else                    
            {
                this._objEditor.setStatus(false);
            }

            this._objDal.Update(_objEditor);
            this.setMessage("Editora atualizado com sucesso!");
            return this._objEditor;
        }
        else{            
            this._objEditor.setStatus(true);            
            id = this._objDal.Create(_objEditor);            
            this._objEditor.setId(id);            
            this.setMessage("Editora cadastrado com sucesso!");
            return this._objEditor;
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
                this._objEditor.setStatus(false);
            }            
            else{
                this._objEditor.setStatus(true);            
            }
                        
            this._objEditor.setId(id);                                    
            this._objDal.Update(_objEditor);
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
            this._objEditor.setId(id);            
            this._objDal.Delete(_objEditor);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
    
}
