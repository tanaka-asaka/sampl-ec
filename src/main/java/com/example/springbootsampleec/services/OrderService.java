package com.example.springbootsampleec.services;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Order;
import java.util.List;


public interface OrderService {
	void register(User user, Item item, int amount, int price);
}
