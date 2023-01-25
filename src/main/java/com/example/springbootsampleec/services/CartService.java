package com.example.springbootsampleec.services;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

import java.util.List;

import com.example.springbootsampleec.entities.Cart;

public interface CartService {
	List<Cart> findAll();
	
	void register(User user, Item item, int amount);
	void addCart(User user, long item_id);
	boolean purchase(User user);
}
