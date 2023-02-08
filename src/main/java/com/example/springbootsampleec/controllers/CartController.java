package com.example.springbootsampleec.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.services.CartService;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;

@RequestMapping("/carts")
@Controller
public class CartController {
	private final CartService cartService;
	private final UserService userService;
	private final ItemService itemService;
	
	public CartController(
			CartService cartService,
			UserService userService,
			ItemService itemService
		) {
		this.cartService = cartService;
		this.userService = userService;
		this.itemService = itemService;
	}
	
	@GetMapping("/")    
    public String index(
		@AuthenticationPrincipal(expression = "user") User user,
		Model model) {
		User refreshedUser = userService.findById(user.getId()).orElseThrow();
		int totalAmount = refreshedUser.getTotalAmount();
        model.addAttribute("title", "カート内の商品");
        model.addAttribute("main", "carts/index::main");
        model.addAttribute("user", refreshedUser);
        model.addAttribute("totalAmount", totalAmount);
        return "layout/logged_in";    
    }
	
	@PostMapping("/add_cart/{id}")    
    public String addCart(
    	@PathVariable("id")  Long id,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        cartService.addCart(
            user,
            id
        );
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "カートに追加しました");
        return "redirect:/items/";  
    }
	
	/**
	 * 詳細画面からもカートに入れる機能を追加
	 * */
	@PostMapping("/add_cart/detail/{id}")    
    public String addCartDetail(
    	@PathVariable("id")  Long id,
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        cartService.addCart(
            user,
            id
        );
		Item item = itemService.findById(id).orElseThrow();
		model.addAttribute("item", item);
		model.addAttribute("user", user);
		model.addAttribute("title", "商品の詳細");
		model.addAttribute("main", "items/detail::main");
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "カートに追加しました");
        return "redirect:items/detail/{id}";  
    }

	@PostMapping("/purchase")    
    public String purchase(
        RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
        ) {
        if(cartService.purchase(user)) {
        	redirectAttributes.addFlashAttribute(
                "successMessage",
                "商品を購入しました");
        	return "redirect:/items/";  
        } else {
        	redirectAttributes.addFlashAttribute(
                "errorMessage",
                "商品を購入できませんでした。");
        	return "redirect:/carts/"; 
        }
        
        
    }
}
