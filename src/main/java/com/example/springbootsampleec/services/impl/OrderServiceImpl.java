package com.example.springbootsampleec.services.impl;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Order;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.OrderRepository;
import com.example.springbootsampleec.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	@Override
	public void register(User user, Item item, int amount, int price) {

		Order order = new Order(null, user, item, amount, price, null);
		orderRepository.saveAndFlush(order);
	}
}
