package com.wide.pos.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wide.pos.domain.Database;
import com.wide.pos.domain.Item;
import com.wide.pos.repository.ItemRepository;
import com.wide.pos.repository.RepositoryException;

public class ItemRepositoryMySQL implements ItemRepository {
	private List<Item> findAll() throws RepositoryException {
		List<Item> listItem = new ArrayList<Item>();
		
		try {
//			Load Driver
			Class.forName(Database.driver);
			
//			Get connection
			Connection conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			
			String query = "SELECT * FROM item";
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				String itemCode = rs.getString("item_code");
				int price = rs.getInt("price");
				String description = rs.getString("description");
				String type = rs.getString("type");
				
				boolean taxable;
				if(rs.getInt("taxable") == 0) {
					taxable = true;
				}
				else {
					taxable = false;
				}
				
				listItem.add(new Item(itemCode, price, description, type, taxable));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Class tidak ditemukan");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Terdapat kesalahan pada SQL Query");
		}
		
		return listItem;
	}
	
	public Item findByCode(String code) throws RepositoryException {
		// TODO Auto-generated method stub
		
		List<Item> listItem = findAll();
		
		Iterator<Item> it = listItem.iterator();
		
		while(it.hasNext()) {
			Item item = it.next();
			if(item.getItemCode().equals(code)) {
				return item;
			}
		}
		
		return null;
	}
}
