package com.wide.pos.test;

import java.util.Date;

import com.wide.pos.domain.Cashier;
import com.wide.pos.domain.Item;
import com.wide.pos.domain.Sale;
import com.wide.pos.domain.SaleItem;

public class SaleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Point of Sale System");
		System.out.println("====================");
		
// 		Cashier Masuk
		Cashier c = new Cashier("Bobby");
		
//		Input Item
		Item item1 = new Item("1", 50000, "Buku Tulis A4", "ATK");
		Item item2 = new Item("2", 100000, "Dunia Shopie", "Novel");
		Item item3 = new Item("3", 40000, "Buku Gambar", "ATK");
		Item item4 = new Item("4", 5000, "Pensil", "ATK");
		Item item5 = new Item("5", 5000, "Penghapus", "ATK");
		
//		Masuk ke SaleItem
		SaleItem si1 = new SaleItem(item1);
		si1.setQuantity(4);
		
		SaleItem si2 = new SaleItem(item2);
		si2.setQuantity(2);
		
//		Masuk ke Sale
		Sale sl1 = new Sale(1, new Date(), c);
		
		sl1.addSaleItem(si1);
		sl1.addSaleItem(si2);
		
		printSales(sl1);
		
//		Masuk ke SaleItem
		SaleItem si3 = new SaleItem(item3);
		si3.setQuantity(1);
		
		SaleItem si4 = new SaleItem(item4);
		si4.setQuantity(2);
		
//		Masuk ke Sale
		Sale sl2 = new Sale(2, new Date(), c);
		
		sl2.addSaleItem(si3);
		sl2.addSaleItem(si4);
		
		printSales(sl2);
	}
	
	public static void printSales(Sale sl) {
		System.out.println("Sale Number: #" + sl.getSaleNumber());
		System.out.println("Cashier: " + sl.getCashier().getName());
		System.out.println("Date: " + sl.getTransDate());
		System.out.println("Item:");
		System.out.println("------------------------------");
		for(SaleItem s : sl.getSaleItems()) {
			if(s != null) {
				System.out.println("Description: " + s.getItem().getDescription());	
				System.out.println("Type: " + s.getItem().getType());
				System.out.println("Quantity: " + s.getQuantity());
				System.out.println("Price: " + s.getPrice());
				System.out.println("Total Sub Price: " + s.totalPrice());
				System.out.println();
			}
		}
		System.out.println("-----------------------------");
		System.out.println("Total Grand Price: " + sl.totalPrice());
		System.out.println();
	}

}
