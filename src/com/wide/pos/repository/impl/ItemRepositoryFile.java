package com.wide.pos.repository.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wide.pos.domain.Item;
import com.wide.pos.repository.ItemRepository;
import com.wide.pos.repository.RepositoryException;

public class ItemRepositoryFile implements ItemRepository {
	private List<Item> findAll() throws RepositoryException {
		List<Item> listItem = new ArrayList<Item>();
		
		try {	
			FileReader fileReader = new FileReader("itemCatalog.txt");
			BufferedReader bufferReader = new BufferedReader(fileReader);
			
			String line = null;
			
			while((line = bufferReader.readLine()) != null) {
				String[] tokens = line.split(";");
				
				boolean taxable;
				if(Integer.parseInt(tokens[4].trim()) == -1) {
					taxable = false;
				}
				else {
					taxable = true;
				}
				
				listItem.add(new Item(tokens[0], Integer.parseInt(tokens[1].trim()), tokens[2].trim(), tokens[3].trim(), taxable));
			}	
		}catch(FileNotFoundException e) {
			throw new RepositoryException("File tidak ditemukan");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RepositoryException("File Corrupted");
		}

		return listItem;
		
		
	}
	
	@Override
	public Item findByCode(String code) throws RepositoryException {
		// TODO Auto-generated method stub
		
		List<Item> listItem = findAll();
		
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
