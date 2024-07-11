package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements UserDAO {

    Connection conn = null;
    final String selectByNameQuery="select * from users where name=?";
    final String selectByIdQuery="select * from users where id=?";
    final String insert="insert into users(name,password) values(?,?)";

    public UserService(){
        this.conn=Connector.getConn();
    }

    @Override
    public User findById(int id) {
        try {
            PreparedStatement getUserQuery=conn.prepareStatement(selectByIdQuery);
            getUserQuery.setInt(1,id);

            ResultSet rs=getUserQuery.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1),rs.getString(2),rs.getString(3));
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User findByName(String name) {
        try {
            PreparedStatement getUserQuery=conn.prepareStatement(selectByNameQuery);
            getUserQuery.setString(1,name);

            ResultSet rs=getUserQuery.executeQuery();
            if(rs.next()){
               User user=new User(rs.getInt(1),rs.getString(2),rs.getString(3));
               if(user.getName().equals(name)){
                   return user;
               }else{
                   return null;
               }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public boolean  insert(User user) {
        boolean userCreated=false;
        try{
            PreparedStatement insertUserQuery=conn.prepareStatement(insert);
            insertUserQuery.setString(1,user.getName());
            insertUserQuery.setString(2,user.getPassword());
            insertUserQuery.executeUpdate();
            userCreated=true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return userCreated;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(String name) {

    }




}
