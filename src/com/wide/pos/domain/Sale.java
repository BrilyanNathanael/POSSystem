package com.wide.pos.domain;

import java.util.Arrays;
import java.util.Date;

public class Sale {
	private int saleNumber;
	private Date transDate;
	private Cashier cashier;
	private SaleItem[] saleItems = new SaleItem[3];
	
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

	public void addSaleItem(SaleItem saleItem) {
		for(int i=0;i<this.saleItems.length;i++) {
			if(this.saleItems[i] == null) {
				this.saleItems[i] = saleItem;
				break;
			}
		}
	}
	
	public SaleItem[] getSaleItems() {
		return Arrays.copyOf(saleItems, saleItems.length);
	}
	
	public int totalPrice() {
		int totalPrice = 0;
		
		for(SaleItem si : this.saleItems) {
			if(si != null) {
				totalPrice = totalPrice + si.totalPrice();
			}
		}
		
		return totalPrice;
	}
}
