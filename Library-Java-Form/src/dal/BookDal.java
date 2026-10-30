/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Interfaces.DalInterface;
import dao.MySqlContext;
import dto.BookDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author Home
 */
public class BookDal implements DalInterface<BookDto>{

    private String sql = "";
    private final MySqlContext _conn;
    
    public BookDal(){
        this._conn = new MySqlContext();
    }

    @Override
    public ResultSet GetOne(BookDto obj) {
        sql = String.format("select * from book c where c.id = %d", obj.getId());
        return this._conn.Consultar(sql);
    }

    @Override
    public ResultSet GetList(BookDto obj, Boolean status) {
        if(status != null){
            sql = String.format("select * from book c where c.name like '%%%s%%' and c.status = %b", obj.getName(),obj.getStatus());
            return this._conn.Consultar(sql);            
        }
        else{
            sql = String.format("select * from book c where c.name like '%%%s%%'", obj.getName());
            return this._conn.Consultar(sql);            
        } 
    }

    @Override
    public int Create(BookDto obj) {
        ResultSet rs;
        int id = 0;
        try{
        
            sql = String.format("insert into book (name,status) values ('%s','%s',true)", obj.getName());
            this._conn.ExecutaSql(sql);

            sql = "SELECT LAST_INSERT_ID() id";
            rs = _conn.Consultar(sql);
            while(rs.next()){
                id = rs.getInt("id");
            }
             return id;        
        
        }
        catch(SQLException ex){
        
        }
        return id;        
    }

    @Override
    public void Update(BookDto obj) {
        sql = String.format("update book c set c.name = '%s',, c.status=%b where c.id =%d;", obj.getName(),obj.getStatus(),obj.getId());
        _conn.ExecutaSql(sql);        
    }

    @Override
    public void Delete(BookDto obj) {
        sql = String.format("delete from book c where c.id =%d", obj.getId());
        _conn.ExecutaSql(sql);        
    }

    @Override
    public boolean Exists(BookDto obj) {
        ResultSet rs;
        boolean exists = true;
        try
        {
            sql = String.format("select COUNT(c.id) exist from book c where c.id <> %d and c.name ='%s';", obj.getId(),obj.getName());
            rs = _conn.Consultar(sql);
            while(rs.next()){
                if(rs.getInt("exist") == 0)
                    exists = false;
            }
        }
        catch(SQLException ex){
        }
        return exists;        
    }
    
    
}
