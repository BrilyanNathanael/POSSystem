package com.wide.pos.domain;

public interface SaleRepository {
	void save(Sale sale);
	Sale findByNumber(int number);
}
