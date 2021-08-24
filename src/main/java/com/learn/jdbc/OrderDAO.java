package com.learn.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.learn.jdbc.util.DataAccessObject;

public class OrderDAO extends DataAccessObject<Order> {

	private static final String FIND_BY_ID = "SELECT c.first_name, c.last_name, c.email, "
			+ "o.order_id, o.creation_date, o.total_date, o.status,"
			+ "s.first_name, s.last_name, s.email,"
			+ "oi.quantity, p.code, p.name, p.size, p.variety, p.price "
			+ " FROM orders o "
			+ " join customer c on o.customer_id = c.customer_id "
			+ " join salesperson s on o.salesperson_id = s.salesperson_id "
			+ " join order_item oi on oi.order_id = o.order_id "
			+ " join product p on oi.product_id = p.product_id "
			+ " WHERE o.order_id = ? ";
	public OrderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public Order findById(long id) {
		Order order = null;
		try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
			statement.setLong(1, id);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				if (order == null) {
					order = new Order();
					order.setId(results.getLong("order_id"));
					order.setCustomerFirstName(results.getString("first_name"));
					order.setCustomerLastName(results.getString("last_name"));
					order.setCustomerEmail(results.getString("email"));
					order.setCreationDate(results.getDate("creation_date"));
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return order;
	}

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order update(Order dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order create(Order dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

}
