package com.example.springbootsampleec.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.springbootsampleec.entities.Cart;
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

	public OrderTestController(OrderRepository orderRepository, CartRepository cartRepository,
			ItemRepository itemRepository, CartService cartService, UserService userService, ItemService itemService) {
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
		this.cartService = cartService;
		this.userService = userService;
		this.itemService = itemService;
	}

	/**
	 * 注文履歴、@AuthenticationPrincipal版
	 * 
	 * @AuthenticationPrincipal UserエンティティのordersのFetchType.EAGERにして、型をSetにするとエラーにならない
	 * @see https://qiita.com/kagamihoge/items/5250ec3c674debbfa5de
	 */
	@GetMapping("/")
	public String index(@AuthenticationPrincipal(expression = "user") User user, Model model) {

		// ユーザー情報
		// オーダー履歴の情報を取得
		User refreshedUser = userService.findById(user.getId()).orElseThrow();
		long totalPrice = refreshedUser.getOrderTotalPrice();

		// テストコンソール出力
		System.out.println("@AuthenticationPrincipal user.getId() : " + user.getId());
		System.out.println("refreshedUser.getOrderTotalPrice() : " + totalPrice);

		model.addAttribute("title", "注文履歴");
		model.addAttribute("main", "orders/index::main");
		model.addAttribute("user", refreshedUser);
		model.addAttribute("totalAmount", totalPrice);
		return "layout/logged_in";
	}

	/**
	 * @PathVariable だと、Idを直接入れて、ログインしていなくても閲覧できたので危険
	 */

	/*
	 * 保存テストのための記述、あまりセキュリティ的に良くない？
	 * 
	 * 
	 * @GetMapping("/save/") public String
	 * orderSaveTest(@AuthenticationPrincipal(expression = "user") User user, //
	 * RedirectAttributes redirectAttributes, Model model) {
	 * 
	 * // ユーザー情報 User refreshedUser =
	 * userService.findById(user.getId()).orElseThrow(); // アイテム型にidをセット Item item =
	 * itemRepository.findById(21L).orElseThrow();
	 * 
	 * // テスト出力 System.out.println("refreshedUser : " + refreshedUser.getId());
	 * System.out.println("item : " + item);
	 * 
	 * Order order = new Order(); order.setUser(refreshedUser); order.setItem(item);
	 * order.setAmount(10); order.setPrice(10000);
	 * 
	 * // テスト出力 System.out.println("order : " + order);
	 * 
	 * orderRepository.saveAndFlush(order);
	 * 
	 * model.addAttribute("title", "注文画面"); model.addAttribute("user",
	 * refreshedUser); model.addAttribute("main", "orders/save::main"); return
	 * "layout/logged_in"; }
	 */

}
