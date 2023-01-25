package com.example.springbootsampleec.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Boolean existsByUserAndItem(User user, Item item);
	Optional<Cart> findTopByUserAndItem(User user, Item item);
}
