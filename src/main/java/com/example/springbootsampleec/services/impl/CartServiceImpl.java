package com.example.springbootsampleec.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.CartRepository;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.repositories.OrderRepository;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.OrderService;

@Service
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;
	// orderService を利用できるように追記
	private final OrderService orderService;

	@Autowired
	private Environment environment;

	public CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository,
			OrderRepository orderRepository, OrderService orderService) {
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
		this.orderRepository = orderRepository;
		this.orderService = orderService;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Cart> findAll() {
		return cartRepository.findAll();
	}

	@Transactional
	@Override
	public void register(User user, Item item, int amount) {

		Cart cart = new Cart(null, user, item, amount, null, null);

		cartRepository.saveAndFlush(cart);
	}

	@Transactional
	@Override
	public void addCart(User user, long item_id) {
		Item item = itemRepository.findById(item_id).orElseThrow();
		if (cartRepository.existsByUserAndItem(user, item)) {
			Cart cart = cartRepository.findTopByUserAndItem(user, item).orElseThrow();
			cart.setAmount(cart.getAmount() + 1);
			cartRepository.saveAndFlush(cart);
		} else {
			Cart cart = new Cart(null, user, item, 1, null, null);
			cartRepository.saveAndFlush(cart);
		}
	}

	@Transactional
	@Override
	public boolean purchase(User user) {
		// テスト出力
		System.out.println("購入ボタンを押した時点");
		for (Cart cart : user.getCarts()) {
			// テスト出力
			System.out.println("1回めの　for (Cart cart : user.getCarts())");
			System.out.println("cart.getItem().getId ：" + cart.getItem().getId());
			System.out.println("cart.getItem().getStock() ：" + cart.getItem().getStock());
			System.out.println("cart.getAmount() ：" + cart.getAmount());
			if (cart.getAmount() > cart.getItem().getStock()) {
				return false;
			}

		}
		for (Cart cart : user.getCarts()) {
			Item item = cart.getItem();
			// テスト出力
			System.out.println("2回めの　for (Cart cart : user.getCarts())");
			System.out.println("item：" + item);
			item.setStock(item.getStock() - cart.getAmount());
			itemRepository.saveAndFlush(item);
			// テスト出力
			System.out.println("在庫更新　item：" + item);
			System.out.println("在庫更新　item.getStock()：" + item.getStock());
			System.out.println("itemRepository.saveAndFlush(item)");

			// orderService を利用して購入履歴を保存
			orderService.register(user, item, cart.getAmount(), item.getPrice());

			// カートの情報をオーダーにコピー
			// Order order = new Order();
			// order.setUser(user);
			// order.setItem(item);
			// order.setAmount(cart.getAmount());
			// order.setPrice(cart.getItem().getPrice());
			// System.out.println("order：" + order);
			// オーダーを保存
			// orderRepository.saveAndFlush(order);

		}
		for (Cart cart : user.getCarts()) {
			// テスト出力
			System.out.println("3回めの　for (Cart cart : user.getCarts())");
			System.out.println("cartRepository.delete(cart)");
			cartRepository.delete(cart);
		}
		return true;
	}
}
