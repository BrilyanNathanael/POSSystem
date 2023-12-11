package com.wide.pos.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemRepositoryDummy implements ItemRepository {

	private List<Item> addItemDummy() {
		List<Item> listItem = new ArrayList<Item>();
		listItem.add(new Item("1", 50000, "Buku Tulis A4", "ATK", false));
		listItem.add(new Item("2", 100000, "Dunia Shopie", "Novel", true));
		listItem.add(new Item("3", 40000, "Buku Gambar", "ATK", false));
		listItem.add(new Item("4", 5000, "Pensil", "ATK", true));
		listItem.add(new Item("5", 5000, "Penghapus", "ATK", false));
		
		return listItem;
	}
	
	@Override
	public Item findByCode(String code) {
		// TODO Auto-generated method stub
		
		List<Item> listItem = addItemDummy();
		
		Iterator<Item> it = listItem.iterator();
		
		while(it.hasNext()) {
			Item item = it.next();
			if(item.getItemCode().equals(code)) {
				return item;
			}
		}
		
		return null;
	}
	
}
