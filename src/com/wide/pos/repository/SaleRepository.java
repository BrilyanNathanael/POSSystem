package com.wide.pos.repository;

import com.wide.pos.domain.Sale;

public interface SaleRepository {
	void save(Sale sale) throws RepositoryException;
	Sale findByNumber(int number) throws RepositoryException;
}
