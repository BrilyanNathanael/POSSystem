package com.wide.pos.usecase;

import java.util.Date;

import com.wide.pos.domain.Cashier;
import com.wide.pos.domain.Item;
import com.wide.pos.domain.Payment;
import com.wide.pos.domain.Sale;
import com.wide.pos.repository.ItemRepository;
import com.wide.pos.repository.RepositoryException;
import com.wide.pos.repository.SaleRepository;
import com.wide.pos.repository.impl.ItemRepositoryDummy;
import com.wide.pos.repository.impl.ItemRepositoryFile;
import com.wide.pos.repository.impl.ItemRepositoryMySQL;
import com.wide.pos.repository.impl.SalesRepositoryDummy;

public class ProcessSaleUseCase {
	
	private Sale sale;
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	private Payment payment;
	
	public ProcessSaleUseCase() {
		itemRepository = new ItemRepositoryMySQL();
		saleRepository = new SalesRepositoryDummy();
	}
	
	public void createNewSale(int saleNumber, Cashier cashier) {
		this.sale = new Sale(saleNumber, new Date(), cashier);
	}
	
	public void addSaleItem(String itemCode, int quantity) throws UseCaseException {
		
		Item item;
		try {
			item = itemRepository.findByCode(itemCode);
			this.sale.addSaleItem(item, quantity);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			throw new UseCaseException(e.getMessage());
		}
		
		
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
