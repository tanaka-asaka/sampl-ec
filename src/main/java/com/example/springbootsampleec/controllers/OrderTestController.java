package com.example.springbootsampleec.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Order;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.CartRepository;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.repositories.OrderRepository;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;

@RequestMapping("/orders")
@Controller
public class OrderTestController {
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	private final CartService cartService;
	private final UserService userService;
	private final ItemService itemService;

	public OrderTestController(OrderRepository orderRepository,
			CartRepository cartRepository, ItemRepository itemRepository,
			CartService cartService, UserService userService, ItemService itemService) {
		this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
		this.cartService = cartService;
		this.userService = userService;
		this.itemService = itemService;
	}
	
	@GetMapping("/")
	public String orderSaveTest(User user, Model model) {
		
		// ユーザー情報
		System.out.println("user:"+user);
		
		/*
		// 注文内容を保存する
		Order orders = new Order();
		// ユーザー型をインスタンス化
		// User user= new User();
		// ユーザー型にidをセット
		// int 型の範囲を超える整数リテラルを記述する場合は末尾に「L」または「l」を記述することで long 型の値として扱われます。
		// long 型の変数に int 型の範囲を超える数値を代入する場合は末尾に「L」を記述してください。
		// user.setId(1L);
		// ユーザー型で返す 中にsetIdを書くと返り値がlong型になるのでエラー
		orders.setUser(user);

		// アイテム型をインスタンス化
		Item item= new Item();
		// アイテム型にidをセット
		item.setId(14L);
		orders.setItem(item);
		orders.setAmount(1);
		orders.setPrice(1000);
		orderRepository.saveAndFlush(orders);
		System.out.println("orders:"+orders);
		*/


		//User refreshedUser = userService.findById(user.getId()).orElseThrow();
		//System.out.println("refreshedUser:" + refreshedUser );
		//model.addAttribute("user", refreshedUser);
		model.addAttribute("title", "注文画面");
		model.addAttribute("main", "orders/index::main");
		return "layout/logged_in";
	}
}
