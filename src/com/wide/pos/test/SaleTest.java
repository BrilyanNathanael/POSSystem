package com.wide.pos.test;

import java.util.Date;
import java.util.Iterator;

import com.wide.pos.domain.CashPayment;
import com.wide.pos.domain.Cashier;
import com.wide.pos.domain.Item;
import com.wide.pos.domain.Payment;
import com.wide.pos.domain.QrisPayment;
import com.wide.pos.domain.Sale;
import com.wide.pos.domain.SaleItem;
import com.wide.pos.domain.Tax;
import com.wide.pos.usecase.ProcessSaleUseCase;
import com.wide.pos.usecase.UseCaseException;

public class SaleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Point of Sale System");
		System.out.println("====================");
		
// 		Cashier Masuk
		Cashier c = new Cashier("Bobby");
		
		try {
			ProcessSaleUseCase saleUseCase = new ProcessSaleUseCase();
			
			boolean checkPayment;
			int totalGrandPrice, saleTax;
			CashPayment cashPayment;
			QrisPayment qrisPayment;
			
//			SALE #1
			saleUseCase.createNewSale(1, c);
			saleUseCase.addSaleItem("2", 2);
			saleUseCase.addSaleItem("3", 4);
			saleUseCase.addSaleItem("4", 3);
			
			System.out.println();
			System.out.println("=======================");
			System.out.println("BEFORE PAYMENT SALE #" + saleUseCase.getSale().getSaleNumber());
			System.out.println("=======================");
			printBeforeSales(saleUseCase.getSale());
			
			totalGrandPrice = saleUseCase.getSale().totalPrice();
			saleTax = saleUseCase.getSale().calculateTax();
			
			cashPayment = new CashPayment((totalGrandPrice + saleTax));
			
			cashPayment.setCashInHand(500_000);
			
			if(saleUseCase.makePayment(cashPayment)) {
				Sale sale = saleUseCase.finishSale();
				
				System.out.println("=======================");
				System.out.println("AFTER PAYMENT SALE #" + + saleUseCase.getSale().getSaleNumber());
				System.out.println("=======================");
				
				printAfterSales(sale);
			}
			
//			SALE #2
			saleUseCase.createNewSale(2, c);
			saleUseCase.addSaleItem("1", 1);
			saleUseCase.addSaleItem("4", 2);
			
			System.out.println();
			System.out.println("=======================");
			System.out.println("BEFORE PAYMENT SALE #" + saleUseCase.getSale().getSaleNumber());
			System.out.println("=======================");
			printBeforeSales(saleUseCase.getSale());
			
			totalGrandPrice = saleUseCase.getSale().totalPrice();
			saleTax = saleUseCase.getSale().calculateTax();
			
			qrisPayment = new QrisPayment((totalGrandPrice + saleTax));
			
			if(saleUseCase.makePayment(qrisPayment)) {
				Sale sale = saleUseCase.finishSale();
				
				System.out.println("=======================");
				System.out.println("AFTER PAYMENT SALE #" + + saleUseCase.getSale().getSaleNumber());
				System.out.println("=======================");
				
				printAfterSales(sale);
			}
			
//			SALE #3
			saleUseCase.createNewSale(3, c);
			saleUseCase.addSaleItem("2", 4);
			saleUseCase.addSaleItem("5", 1);
			
			System.out.println();
			System.out.println("=======================");
			System.out.println("BEFORE PAYMENT SALE #" + saleUseCase.getSale().getSaleNumber());
			System.out.println("=======================");
			printBeforeSales(saleUseCase.getSale());
			
			totalGrandPrice = saleUseCase.getSale().totalPrice();
			saleTax = saleUseCase.getSale().calculateTax();
			
			cashPayment = new CashPayment((totalGrandPrice + saleTax));
			
			cashPayment.setCashInHand(500_000);
			
			if(saleUseCase.makePayment(cashPayment)) {
				Sale sale = saleUseCase.finishSale();
				
				System.out.println("=======================");
				System.out.println("AFTER PAYMENT SALE #" + + saleUseCase.getSale().getSaleNumber());
				System.out.println("=======================");
				
				printAfterSales(sale);
			}
			
		} catch (UseCaseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void printBeforeSales(Sale sl) {
		System.out.println("Sale Number: #" + sl.getSaleNumber());
		System.out.println("Cashier: " + sl.getCashier().getName());
		System.out.println("Date: " + sl.getTransDate());
		System.out.println("Item:");
		System.out.println("------------------------------");
		
		Iterator<SaleItem> itSaleItem = sl.getSaleItems().iterator();
		
		while(itSaleItem.hasNext()) {
			SaleItem saleItem = itSaleItem.next();
			System.out.println("Description: " + saleItem.getItem().getDescription());	
			System.out.println("Type: " + saleItem.getItem().getType());
			System.out.println("Quantity: " + saleItem.getQuantity());
			System.out.println("Price: " + saleItem.getPrice());
			System.out.println("Total Sub Price: " + saleItem.totalPrice());
			System.out.println();
		}
		System.out.println("-----------------------------");
		
		int totalGrandPrice = sl.totalPrice();
		int saleTax = sl.calculateTax();
		
		System.out.println("Total Grand Price: " + sl.totalPrice());
		System.out.println("Tax (" + (int) (Tax.tax * 100) + "%): " + sl.calculateTax());
		System.out.println("Total Price + Tax: " + (totalGrandPrice + saleTax));
		
		System.out.println();
	}
	
	public static void printAfterSales(Sale sl) {
		printBeforeSales(sl);
		
		System.out.println("-----------------------------");
		if(sl.getPayment() instanceof CashPayment) {
			CashPayment cashPayment = (CashPayment) sl.getPayment();
			System.out.println("Payment (Cash) : " + cashPayment.getCashInHand());
			System.out.println("Change : " + cashPayment.change());
		}
		else {
			System.out.println("Payment (Qris) : " + sl.getPayment().getAmount());
		}
		System.out.println();
		
	}

}
