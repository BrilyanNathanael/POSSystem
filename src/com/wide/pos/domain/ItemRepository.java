package com.wide.pos.domain;

public interface ItemRepository {
	Item findByCode(String code);
}
