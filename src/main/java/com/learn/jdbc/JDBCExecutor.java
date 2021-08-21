package com.learn.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

    public static void main(String... args){
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try{
            Connection connection = dcm.getConnection();
            
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();

			customer.setFirstName("John");
			customer.setLastName("Adams");
			customer.setEmail("jadams.wh.gov");
			customer.setPhone("555 555 555");
			customer.setAddress("1234 Main St");
			customer.setCity("Arlington");
			customer.setState("VA");
			customer.setZipCode("88998");
			
			Customer dbCustomer = customerDAO.create(customer);
			System.out.println(dbCustomer);
			dbCustomer = customerDAO.findById(dbCustomer.getId());
			System.out.println(dbCustomer);
			dbCustomer.setEmail("john.adam@gov");
			dbCustomer = customerDAO.update(dbCustomer);
			System.out.println(dbCustomer);
			customerDAO.delete(dbCustomer.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
