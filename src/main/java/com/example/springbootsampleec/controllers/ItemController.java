package com.example.springbootsampleec.controllers;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import javax.validation.Valid;

import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.forms.ItemCreateForm;
import com.example.springbootsampleec.forms.ItemEditForm;
import com.example.springbootsampleec.services.ItemService;
import com.example.springbootsampleec.services.UserService;
 
@RequestMapping("/items")
@Controller
public class ItemController { 
    private final ItemService itemService;
    private final UserService userService;
    
    public ItemController(
        ItemService itemService,
        UserService userService
    ) {
        this.itemService = itemService;
        this.userService = userService;
    }
    
    @GetMapping("/")    
    public String index(
        @AuthenticationPrincipal(expression = "user") User user,
        Model model
    ) {
    	User refreshedUser = userService.findById(user.getId()).orElseThrow();
        List<Item> items = itemService.findByName("panda");
        model.addAttribute("user", refreshedUser);
        model.addAttribute("items", items);
        model.addAttribute("title", "商品一覧");
        model.addAttribute("main", "items/index::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/create")    
    public String create(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemCreateForm") ItemCreateForm itemCreateForm,
        Model model
    ) {
        model.addAttribute("title", "商品の新規作成");
        model.addAttribute("user", user);
        model.addAttribute("main", "items/create::main");
        return "layout/logged_in";    
    }
    
    @PostMapping("/create")    
    public String createProcess(
        @AuthenticationPrincipal(expression = "user") User user,
        @Valid ItemCreateForm itemCreateForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model
        ) {
        if(bindingResult.hasErrors()){
            return create(user, itemCreateForm, model);
        }
        itemService.register(
            itemCreateForm.getName(),
            itemCreateForm.getPrice(),
            itemCreateForm.getStock(),
            itemCreateForm.getDescription(),
            itemCreateForm.getImage()
        );
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品を追加しました");
        return "redirect:/admin";
    }
 
    @GetMapping("/detail/{id}")    
    public String detail(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Long id,
        Model model
    ) {
        Item item = itemService.findById(id).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "商品の詳細");
        model.addAttribute("main", "items/detail::main");
        return "layout/logged_in";    
    }
 
    @GetMapping("/edit/{id}")    
    public String edit(
        @AuthenticationPrincipal(expression = "user") User user,
        @ModelAttribute("itemEditForm") ItemEditForm itemEditForm,
        @PathVariable("id")  Integer id,
        Model model) {
        Item item = itemService.findById(id).orElseThrow();
        itemEditForm.setName(item.getName());
        itemEditForm.setPrice(item.getPrice());
        itemEditForm.setStock(item.getStock());
        itemEditForm.setDescription(item.getDescription());
        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("title", "商品情報の編集");
        model.addAttribute("main", "items/edit::main");
        return "layout/logged_in";    
    }
    
    @PostMapping("/update/{id}")    
    public String update(
        @AuthenticationPrincipal(expression = "user") User user,
        @PathVariable("id")  Integer id,
        @Valid ItemEditForm itemEditForm,
        RedirectAttributes redirectAttributes,
        BindingResult bindingResult,
        Model model) {
        if(bindingResult.hasErrors()){
            return edit(user, itemEditForm, id, model);
        }
        itemService.updateItem(
            id,
            itemEditForm.getName(),
            itemEditForm.getPrice(),
            itemEditForm.getStock(),
            itemEditForm.getDescription()
        );  
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品情報の更新が完了しました");
        return "redirect:/admin";
    }
    
    @PostMapping("/delete/{id}")    
    public String delete(
        @PathVariable("id")  Integer id,
        RedirectAttributes redirectAttributes,
        Model model) {
        itemService.delete(id);
        redirectAttributes.addFlashAttribute(
            "successMessage",
            "商品の削除が完了しました");
        return "redirect:/admin";  
    }
}