package com.wide.pos.usecase;

import java.util.Date;

import com.wide.pos.domain.Cashier;
import com.wide.pos.domain.Item;
import com.wide.pos.domain.Payment;
import com.wide.pos.domain.Sale;
import com.wide.pos.repository.ItemRepository;
import com.wide.pos.repository.ItemRepositoryDummy;
import com.wide.pos.repository.SaleRepository;
import com.wide.pos.repository.SalesRepositoryDummy;

public class ProcessSaleUseCase {
	
	private Sale sale;
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	private Payment payment;
	
	public ProcessSaleUseCase() {
		itemRepository = new ItemRepositoryDummy();
		saleRepository = new SalesRepositoryDummy();
	}
	
	public void createNewSale(int saleNumber, Cashier cashier) {
		this.sale = new Sale(saleNumber, new Date(), cashier);
	}
	
	public void addSaleItem(String itemCode, int quantity) {
		
		Item item = itemRepository.findByCode(itemCode);
		
		this.sale.addSaleItem(item, quantity);
	}
	
	public boolean makePayment(Payment payment) {
		this.sale.setPayment(payment);
		return this.sale.finish();
	}
	
	public Sale getSale() {
		return this.sale;
	}
	
	public Sale finishSale() {
		if(this.sale.getPayment().validate()) {
			saleRepository.save(this.sale);
		}
		return saleRepository.findByNumber(this.sale.getSaleNumber());
	}
}
