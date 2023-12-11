package com.wide.pos.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Sale {
	private int saleNumber;
	private Date transDate;
	private Cashier cashier;
	private List<SaleItem> salesItem = new ArrayList<SaleItem>();
	private Payment payment;
	
	public Sale(int saleNumber, Date transDate, Cashier cashier) {
		this.saleNumber = saleNumber;
		this.transDate = transDate;
		this.cashier = cashier;
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void addSaleItem(Item item, int quantity) {
		SaleItem si = new SaleItem(item, quantity);
		
		salesItem.add(si);	
	}
	
	public List<SaleItem> getSaleItems() {
        List<SaleItem> clonedList = new ArrayList<SaleItem>();
        clonedList.addAll(salesItem);
        return clonedList;
	}
	
	public int totalPrice() {
		int totalPrice = 0;
		
		Iterator<SaleItem> itSaleItem = salesItem.iterator();
		
		while(itSaleItem.hasNext()) {
			SaleItem si = itSaleItem.next();
			totalPrice = totalPrice + si.totalPrice();
		}
		
		return totalPrice;
	}
	
	public int calculateTax() {
		int itemTax = 0;
		
		Iterator<SaleItem> itSaleItem = salesItem.iterator();
		
		while(itSaleItem.hasNext()) {
			SaleItem si = itSaleItem.next();
			if(si.getItem().getTaxable()) {
				itemTax = itemTax + ((int) (si.totalPrice() * Tax.tax));
			}
		}
		
		return itemTax;
	}
	
	public boolean finish() {
		if(this.getPayment().validate()) {
			return true;
		}
		else return false;
	}
}
