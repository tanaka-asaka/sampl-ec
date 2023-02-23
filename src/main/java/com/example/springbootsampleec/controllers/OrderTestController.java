package com.example.springbootsampleec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springbootsampleec.entities.Order;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.repositories.OrderRepository;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;

@RequestMapping("/order")
@Controller
public class OrderTestController {
	private final OrderRepository orderRepository;
	private final CartService cartService;
	private final UserService userService;
	private final ItemService itemService;

	public OrderTestController(OrderRepository orderRepository, CartService cartService, UserService userService, ItemService itemService) {
		this.orderRepository = orderRepository;
		this.cartService = cartService;
		this.userService = userService;
		this.itemService = itemService;
	}
	
	@GetMapping("/")
	public String orderSaveTest() {
		Order orders = new Order();
		// ユーザー型をインスタンス化
		User user= new User();
		// ユーザー型にidをセット
		user.setId(1L);
		// ユーザー型で返す 中にsetIdを書くと返り値がlong型になるのでエラー
		orders.setUser(user);
		//orders.setItem(2);
		orders.setAmount(10);
		orders.setPrice(1000);
		orderRepository.saveAndFlush(orders);
		
		
		return "redirect:/order/";
	}
}
