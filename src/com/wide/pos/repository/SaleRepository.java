package com.wide.pos.repository;

import com.wide.pos.domain.Sale;

public interface SaleRepository {
	void save(Sale sale);
	Sale findByNumber(int number);
}
