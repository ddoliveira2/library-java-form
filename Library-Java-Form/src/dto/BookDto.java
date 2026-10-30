/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Home
 */
public class BookDto extends BaseDto{
    private int _authorId;
    private int _categoryId;
    private int _editorId;
    private int _publisherId;
    
    public int getAuthorId(){
        return this._authorId;
    }
    
    public void setAuthorId(int authorId){
        this._authorId = authorId;
    }
    
    public int getCategoryId(){
        return this._categoryId;
    }
    
    public void setCategoryId(int categoryId){
        this._categoryId = categoryId;
    }
    
    
    public int getEditorId(){
        return this._editorId;
    }
    
    public void setEditorId(int editorId){
        this._editorId = editorId;
    }
    
    public int getPublisherId(){
        return this._publisherId;
    }
    
    public void setPublisherId(int publisherId){
        this._publisherId = publisherId;
    }
}
