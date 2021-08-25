package com.learn.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.learn.jdbc.util.DataAccessObject;

public class OrderDAO extends DataAccessObject<Order> {

	private static final String FIND_BY_ID = "SELECT c.first_name, c.last_name, c.email, "
			+ "o.order_id, o.creation_date, o.total_due, o.status,"
			+ "s.first_name, s.last_name, s.email,"
			+ "oi.quantity, p.code, p.name, p.size, p.variety, p.price "
			+ " FROM orders o "
			+ " join customer c on o.customer_id = c.customer_id "
			+ " join salesperson s on o.salesperson_id = s.salesperson_id "
			+ " join order_item oi on oi.order_id = o.order_id "
			+ " join product p on oi.product_id = p.product_id "
			+ " WHERE o.order_id = ? ";
	
	private static final String GET_FOR_CUST = " SELECT * FROM get_orders_by_customer(?)";
	public OrderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public Order findById(long id) {
		Order order = null;
		List<OrderItem> items = null;
		try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
			statement.setLong(1, id);
			ResultSet results = statement.executeQuery();
			while (results.next()) {
				if (order == null) {
					order = new Order();
					order.setId(results.getLong(4));
					order.setCustomerFirstName(results.getString(1));
					order.setCustomerLastName(results.getString(2));
					order.setCustomerEmail(results.getString(3));
					order.setCreationDate(results.getDate(5));
					order.setTotalDue(results.getBigDecimal(6));
					order.setStatus(results.getString(7));
					order.setSalespersonFirstName(results.getString(8));
					order.setSalespersonLastName(results.getString(9));
					order.setSalespersonEmail(results.getString(10));
				}
				if (items == null) {
					items = new ArrayList<>();
					order.setOrderItems(items);
				}
				OrderItem item = new OrderItem();
				item.setQuantity(results.getInt(11));
				item.setProductCode(results.getString(12));
				item.setProductName(results.getString(13));
				item.setProductSize(results.getInt(14));
				item.setProductVariety(results.getString(15));
				item.setProductPrice(results.getBigDecimal(16));
				items.add(item);
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
	
	public List<Order> getOrdersForCustomer(long customerId) {
		List<Order> orders = new ArrayList<>();
		try (PreparedStatement statement = this.connection.prepareStatement(GET_FOR_CUST)) {
			statement.setLong(1, customerId);
			ResultSet results = statement.executeQuery();
			Order order = null;
			int orderId = 0;
			List<OrderItem> items = null;
			while (results.next()) {
				long id = results.getLong(4);
				if (orderId != id) {
					order = new Order();
					orders.add(order);
					order.setId(results.getLong(4));
					order.setCustomerFirstName(results.getString(1));
					order.setCustomerLastName(results.getString(2));
					order.setCustomerEmail(results.getString(3));
					order.setCreationDate(results.getDate(5));
					order.setTotalDue(results.getBigDecimal(6));
					order.setStatus(results.getString(7));
					order.setSalespersonFirstName(results.getString(8));
					order.setSalespersonLastName(results.getString(9));
					order.setSalespersonEmail(results.getString(10));
					items = null;
				}
				if (items == null) {
					items = new ArrayList<>();
					order.setOrderItems(items);
				}
				OrderItem item = new OrderItem();
				item.setQuantity(results.getInt(11));
				item.setProductCode(results.getString(12));
				item.setProductName(results.getString(13));
				item.setProductSize(results.getInt(14));
				item.setProductVariety(results.getString(15));
				item.setProductPrice(results.getBigDecimal(16));
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return orders;
	}

}
