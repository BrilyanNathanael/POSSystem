package com.wide.pos.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalesRepositoryDummy implements SaleRepository {
	
	public static List<Sale> sales = new ArrayList<Sale>();
 
	@Override
	public void save(Sale sale) {
		// TODO Auto-generated method stub
		sales.add(sale);
	}

	@Override
	public Sale findByNumber(int number) {
		// TODO Auto-generated method stub
		
		Iterator<Sale> it = sales.iterator();
		
		while(it.hasNext()) {
			Sale sl = it.next();
			if(sl.getSaleNumber() == number) {
				return sl;
			}
		}
		
		return null;
	}
	

}
