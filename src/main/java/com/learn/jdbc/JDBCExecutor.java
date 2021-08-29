package com.learn.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCExecutor {

    public static void main(String... args){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try{
            Connection connection = dcm.getConnection();
            
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllPaged(20, 1).forEach(System.out::println);
            System.out.println("*****************");
            customerDAO.findAllPaged(20, 2).forEach(System.out::println);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
