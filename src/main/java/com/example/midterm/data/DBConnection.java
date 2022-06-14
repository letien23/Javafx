package com.example.midterm.data;



import com.example.midterm.data.models.Purpose;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    public Connection con;
    public DBConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/finalexam","root", "");
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<Purpose> getPurpose(){
        ArrayList<Purpose> list = new ArrayList<>();
        String sql = "SELECT * FROM manage";
        try {
            ResultSet results = con.prepareStatement(sql).executeQuery();
            while (results.next()){
                System.out.println("Id: "+ results.getInt("id"));
                System.out.println("Purpose: " + results.getString("purpose") );
                System.out.println("Cost: " + results.getFloat("cost") );
                System.out.println("Date: " + results.getString("date") );
                Purpose purpose = new Purpose(results.getInt("id"),results.getString("purpose"),results.getFloat("cost"),results.getString("date"));
                list.add(purpose);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public void insertPurpose(Purpose pur){
        String sql = "INSERT INTO  manage (purpose, cost, date) VALUES ('"+pur.purpose+"', "+pur.cost+", '"+pur.date+"')";
        System.out.println(sql);
        try {
            con.prepareStatement(sql).executeUpdate();
            System.out.println("Inserted purpose");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void updatePurpose(Purpose pur){
        String sql = "UPDATE manage SET purpose = '" + pur.purpose+ "', cost = "+pur.cost+", date = '" + pur.date+ "' WHERE id ="+pur.id;
        System.out.println(sql);
        try {
            con.prepareStatement(sql).executeUpdate();
            System.out.println("Updated a purpose");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void deletePurpose(int id){
        String sql = "DELETE FROM manage WHERE id ="+id;
        System.out.println(sql);
        try {
            con.prepareStatement(sql).executeUpdate();
            System.out.println("Deleted a purpose");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
