package com.wide.pos.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.wide.pos.domain.CashPayment;
import com.wide.pos.domain.Cashier;
import com.wide.pos.domain.Database;
import com.wide.pos.domain.Item;
import com.wide.pos.domain.Payment;
import com.wide.pos.domain.QrisPayment;
import com.wide.pos.domain.Sale;
import com.wide.pos.domain.SaleItem;
import com.wide.pos.repository.RepositoryException;
import com.wide.pos.repository.SaleRepository;

public class SalesRepositoryMySQL implements SaleRepository {

	@Override
	public void save(Sale sale) throws RepositoryException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		try {
			Class.forName(Database.driver);
//			
			conn = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			conn.setAutoCommit(false);
			
//			Memasukkan ke Sale
			String sqlQuerySale = "INSERT INTO sale (sale_number, trans_date, cashier, payment, cash_in_hand) values (?,?,?,?,?)";
			
			PreparedStatement stm = conn.prepareStatement(sqlQuerySale);
			
			String payment;
			int cashInHand = 0;
			if(sale.getPayment() instanceof CashPayment) {
				payment = "Cash";
				cashInHand = sale.getPayment().getCashInHand();
			}else {
				payment = "Qris";
			}
			
			stm.setInt(1, sale.getSaleNumber());
			stm.setString(2, sale.getTransDate());
			stm.setString(3, sale.getCashier().getName());
			stm.setString(4, payment);
			stm.setInt(5, cashInHand);
			
			int result = stm.executeUpdate();
			
			if(result > 0) {
				
				String querySelectId = "SELECT sale_number FROM sale ORDER BY id DESC LIMIT 1";
				PreparedStatement stm2 = conn.prepareStatement(querySelectId);
				
				ResultSet result2 = stm2.executeQuery();
				
				if(result2.next()) {
					int sale_number = result2.getInt("sale_number");
					
					for(SaleItem si : sale.getSaleItems()) {
//						Memasukkan data Sale Item
						String sqlQuery = "INSERT INTO sale_item (sale_number, item_id, quantity, price) values (?,?,?,?)";
						
						PreparedStatement stm3 = conn.prepareStatement(sqlQuery);
						
						stm3.setInt(1, sale_number);
						stm3.setString(2, si.getItem().getItemCode());
						stm3.setInt(3, si.getQuantity());
						stm3.setInt(4, si.getPrice());
						
						int result3 = stm3.executeUpdate();
					}
				}
			}
			conn.commit();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(conn != null) {
				try {
					conn.rollback();
					throw new RepositoryException("Roll back!");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					throw new RepositoryException("Kesalahan pada SQL Save Sales Repository!");
				}				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Class tidak ditemukan!");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RepositoryException("Kesalahan koneksi pada Save Sales Repository!");
			}
		}
		
	}

	@Override
	public Sale findByNumber(int number) throws RepositoryException {
		// TODO Auto-generated method stub
		Connection conns = null;
		
		try {
			Class.forName(Database.driver);
			
			conns = DriverManager.getConnection(Database.jdbcUrl, Database.username, Database.password);
			
//			conn.setAutoCommit(true);
			
			String querySale = "SELECT * FROM sale WHERE sale_number=?";
			PreparedStatement stm = conns.prepareStatement(querySale);
			
			stm.setInt(1, number);
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()) {
				Cashier c = new Cashier(rs.getString("cashier"));
				Sale s = new Sale(rs.getInt("sale_number"), rs.getString("trans_date"), c);
				
				String query = "SELECT * FROM sale_item JOIN item ON sale_item.item_id = item.item_code WHERE sale_item.sale_number=?";
				PreparedStatement stm2 = conns.prepareStatement(query);
				
				stm2.setInt(1, number);
				ResultSet rs2 = stm2.executeQuery();
				
				int totalPrice = 0;
				
				while(rs2.next()) {
					boolean tax;
					if(rs2.getInt("taxable") == 0) {
						tax = true;
					}
					else {
						tax = false;
					}
					
					int subPrice = rs2.getInt("quantity") * rs2.getInt("price");
					totalPrice = totalPrice + subPrice;
					
					Item itm = new Item(rs2.getString("item_code"), rs2.getInt(5), rs2.getString("description"), rs2.getString("type"), tax);
					
					s.addSaleItem(itm, rs2.getInt("quantity"));
					
				}
				
				Payment p;
				int taxCalculate = s.calculateTax();
				if(rs.getString("payment").equals("Cash")) {
					p = new CashPayment(totalPrice + taxCalculate);
					p.setCashInHand(rs.getInt("cash_in_hand"));
				}
				else {
					p = new QrisPayment(totalPrice + taxCalculate);
				}
				
				s.setPayment(p);
				
				return s;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Sales tidak ketemu!");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("Class tidak ditemukan!");
		}
		return null;
	}
	
}
