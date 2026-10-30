/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Home
 */
public class PublisherDto extends BaseDto{
   private String _description;
   
   public String getDescription(){
       return this._description;
   }
   
   public void setDescription(String description){
       this._description = description;
   }    
}
