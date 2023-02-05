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
import com.example.springbootsampleec.services.CartService;

@Service
public class CartServiceImpl implements CartService{
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	
	@Autowired
    private Environment environment;
 
    public CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
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
    	if( cartRepository.existsByUserAndItem(user, item) ) {
    		Cart cart = cartRepository.findTopByUserAndItem(user, item).orElseThrow();
    		System.out.println(cart.getAmount() + 1);
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
    	for(Cart cart : user.getCarts()) {
    		if(cart.getAmount() > cart.getItem().getStock()) {
    			return false;
    		}
    			
    	}
    	for(Cart cart : user.getCarts()) {
    		Item item = cart.getItem();
    		item.setStock(item.getStock() - cart.getAmount());
    		itemRepository.saveAndFlush(item);
    	}
    	for(Cart cart : user.getCarts()) {
    		cartRepository.delete(cart);
    	}
    	return true;
    }
}