/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bll;

import Interfaces.BllInterface;
import dto.BookDto;
import dal.BookDal;
import dto.MensagensDto;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Home
 */
public class BookBll extends MensagensDto implements BllInterface<BookDto>{

    private BookDto _objBook;
    private BookDal _objDal;
    private ResultSet rs;
    
    private String _id;
    private String _name;
    private String _authorId;
    private String _categoryId;
    private String _editorId;
    private String _publisherId;
    private String _status;
    
    
    public BookBll(String id, String name,String editorId,String authorId,String categoryId,String publisherId, String status) {
        this._objBook = new BookDto();
        this._objDal = new BookDal();
        this._id = id;
        this._name = name;
        this._editorId = editorId;
        this._authorId = authorId;
        this._categoryId = categoryId;
        this._publisherId = publisherId;
        this._status = status;
    }    

    @Override
    public BookDto GetOne() {
        try
        {            
            if(this._id == null || this._id.isEmpty()){
                setMessage("Não foi informado o id!");
                return null;
            }
                
            int id;
            try{
                id = Integer.parseInt(_id);
                this._objBook.setId(id);
                this. rs = this._objDal.GetOne(this._objBook);
                if(rs.getRow() > 0 ){
                    _objBook.setId(rs.getInt("id"));
                    _objBook.setName(rs.getString("name"));                    
                    _objBook.setAuthorId(rs.getInt("authorId"));
                    _objBook.setEditorId(rs.getInt("editorId"));
                    _objBook.setCategoryId(rs.getInt("categoryId"));
                    _objBook.setPublisherId(rs.getInt("publisherId"));                    
                    _objBook.setStatus(rs.getBoolean("status"));                    
                return _objBook;
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
    public List<BookDto> GetLista() {
        List<BookDto> list = new ArrayList<>();
        
        try{
            Boolean statusConsulta = false;
            if(this._status.trim().length()>0){
                if(this._status.toUpperCase().trim().equals("ATIVO")){
                    statusConsulta =  true;
                }
                this.rs = this._objDal.GetList(this._objBook,statusConsulta);
            }
            else{
                this.rs = this._objDal.GetList(this._objBook,null);
            }    
            
            BookDto insert;
            while(this.rs.next()){
                insert = new BookDto();
                
                insert.setId(this.rs.getInt("id"));
                insert.setName(this.rs.getString("name"));
                insert.setAuthorId(rs.getInt("authorId"));
                insert.setEditorId(rs.getInt("editorId"));
                insert.setCategoryId(rs.getInt("categoryId"));
                insert.setPublisherId(rs.getInt("publisherId"));
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
    public BookDto CreateUpdate() {
        
        if(this._name.isBlank() || this._name.isEmpty()){
            this.setMessage("Nome não foi informado");
            return null;
        }  
        
        if(this._categoryId.isBlank() || this._categoryId.isEmpty()){
            this.setMessage("Categoria não foi informado");
            return null;
        } 

        if(this._publisherId.isBlank() || this._publisherId.isEmpty()){
            this.setMessage("Publicadora não foi informado");
            return null;
        } 

        if(this._editorId.isBlank() || this._editorId.isEmpty()){
            this.setMessage("Editora não foi informado");
            return null;
        } 

        if(this._authorId.isBlank() || this._authorId.isEmpty()){
            this.setMessage("Autor não foi informado");
            return null;
        } 
        
        this._objBook.setName(this._name);
        
        int author;
        
        try
        {
            author = Integer.parseInt(this._authorId);
        }
        catch(NumberFormatException ex)
        {
            author = 0;
        }
        
        if(author == 0){
        
            this.setMessage("Autor informado é invalido!");
            return null;
        }

        this._objBook.setAuthorId(author);
        
        int editor;
        
        try
        {
            editor = Integer.parseInt(this._editorId);
        }
        catch(NumberFormatException ex)
        {
            editor = 0;
        }
        
        if(editor == 0){
        
            this.setMessage("Editor informado é invalido!");
            return null;
        }

        this._objBook.setEditorId(editor);
        
        int publisher;
        
        try
        {
            publisher = Integer.parseInt(this._publisherId);
        }
        catch(NumberFormatException ex)
        {
            publisher = 0;
        }
        
        if(publisher == 0){
        
            this.setMessage("Publicadora informado é invalido!");
            return null;
        }

        this._objBook.setPublisherId(publisher);
        
        int category;
        
        try
        {
            category = Integer.parseInt(this._categoryId);
        }
        catch(NumberFormatException ex)
        {
            category = 0;
        }
        
        if(category == 0){
        
            this.setMessage("Categoria informado é invalido!");
            return null;
        }

        this._objBook.setCategoryId(category);                      
        
        int id = 0;
        
        
        if(this._id.trim().length() > 0){
            try
            {
                id = Integer.parseInt(this._id);
                this._objBook.setId(id); 
            }
            catch(NumberFormatException ex)
            {
                this.setMessage(ex.getMessage());
                return null;
            }
            
            if(this._status.trim().equals("ATIVO"))
            {
                this._objBook.setStatus(true);
            }
            else                    
            {
                this._objBook.setStatus(false);
            }

            this._objDal.Update(_objBook);
            this.setMessage("Livro atualizado com sucesso!");
            return this._objBook;
        }
        else{            
            this._objBook.setStatus(true);            
            id = this._objDal.Create(_objBook);            
            this._objBook.setId(id);            
            this.setMessage("Livro cadastrado com sucesso!");
            return this._objBook;
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
                this._objBook.setStatus(false);
            }            
            else{
                this._objBook.setStatus(true);            
            }
                        
            this._objBook.setId(id);                                    
            this._objDal.Update(_objBook);
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
            this._objBook.setId(id);            
            this._objDal.Delete(_objBook);
            return true;
        }
        catch(NumberFormatException ex){
            this.setMessage(ex.getMessage());
        }
        
        return false;
    }
    
    
}
