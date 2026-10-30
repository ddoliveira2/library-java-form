/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Interfaces.DalInterface;
import dao.MySqlContext;
import dto.AuthorDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author Home
 */
public class AuthorDal implements DalInterface<AuthorDto> {
    
    private String sql;
    private final MySqlContext _conn;
    
    public AuthorDal(){
        this._conn = new MySqlContext();
    }

    @Override
    public ResultSet GetOne(AuthorDto obj) {
        sql = String.format("select * from author c where c.id = %s", obj.getId());
        return _conn.Consultar(sql); 
    }

    @Override
    public ResultSet GetList(AuthorDto obj, Boolean status) {
        if(status != null){
            sql = String.format("select * from author c where c.name like '%%%s%%'", obj.getName());
            return _conn.Consultar(sql);          
        }
        else{
            sql = String.format("select * from author c where c.name like '%%%s%%' and c.status = %b", obj.getName(),status);
            return _conn.Consultar(sql);        
        }
    }

    @Override
    public int Create(AuthorDto obj) {
        int id = 0;
        ResultSet rs;
        try{
                sql = String.format("insert into author (name,description,status) values ('%s','%s',true);", obj.getName(),obj.getDescription());
                _conn.ExecutaSql(sql);

               
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
    public void Update(AuthorDto obj) {
        sql = String.format("update author c set c.name = '%s', c.description = '%s', c.status=%b where c.id =%d;", obj.getName(),obj.getDescription(),obj.getStatus(),obj.getId());
        _conn.ExecutaSql(sql);
    }

    @Override
    public void Delete(AuthorDto obj) {
        sql = String.format("delete from author c where c.id =%d", obj.getId());
        _conn.ExecutaSql(sql);
    }

    @Override
    public boolean Exists(AuthorDto obj) {
        ResultSet rs;
        boolean exists = true;
        try
        {
            sql = String.format("select COUNT(c.id) exist from author c where c.id <> %d and c.name ='%s';", obj.getId(),obj.getName());
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
