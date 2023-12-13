package com.wide.pos.repository;

import com.wide.pos.domain.Item;

public interface ItemRepository {
	Item findByCode(String code) throws RepositoryException;
}
