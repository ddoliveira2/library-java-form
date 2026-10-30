/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Interfaces.DalInterface;
import dao.MySqlContext;
import dto.UserDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author Home
 */
public class UserDal implements DalInterface<UserDto>{

    private String sql = "";
    private final MySqlContext _conn;
    
    public UserDal(){
        this._conn = new MySqlContext();
    }

    @Override
    public ResultSet GetOne(UserDto obj) {
        sql = String.format("select * from user c where c.id = %d", obj.getId());
        return this._conn.Consultar(sql);
    }

    @Override
    public ResultSet GetList(UserDto obj, Boolean status) {
        if(status != null){
            sql = String.format("select * from user c where c.name like '%%%s%%' and c.login like '%%%s%%' and c.email like '%%%s%%' and c.status = %b", obj.getName(),obj.getLogin(),obj.getEmail(),obj.getStatus());
            return this._conn.Consultar(sql);
        }
        else{
            sql = String.format("select * from user c where c.name like '%%%s%%' and c.login like '%%%s%%' and c.email like '%%%s%%'", obj.getName(),obj.getLogin(),obj.getEmail());
            return this._conn.Consultar(sql);        
        }
    }

    @Override
    public int Create(UserDto obj) {
        int id = 0;
        ResultSet rs;
                
        try{
            sql = String.format("insert into user (name,login,password,email,status) values ('%s','%s','%s','%s',true)",obj.getName(),obj.getLogin(),obj.getPassword(),obj.getEmail());
            this._conn.ExecutaSql(sql);

            sql = "SELECT LAST_INSERT_ID() id";
            rs = _conn.Consultar(sql);
            while(rs.next()){
                id = rs.getInt("id");
            }        
        }catch(SQLException ex){
        
        }
         return id;
    }

    @Override
    public void Update(UserDto obj) {
        sql = String.format("update user c set c.name = '%s', c.login = '%s', c.password = '%s', c.email = '%s', c.status=%b where c.id =%d;", obj.getName(),obj.getLogin(),obj.getPassword(),obj.getEmail(),obj.getStatus(),obj.getId());
        this._conn.ExecutaSql(sql);
    }

    @Override
    public void Delete(UserDto obj) {
        sql = String.format("delete from user c where c.id =%d;", obj.getId());
        this._conn.ExecutaSql(sql);
    }

    @Override
    public boolean Exists(UserDto obj) {
        ResultSet rs;
        boolean exists = true;
        try
        {
            sql = String.format("select COUNT(c.id) exist from user c where c.id <> %d and c.login ='%s' and c.email = '%s'", obj.getId(),obj.getLogin(),obj.getEmail());
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
