package com.example.springbootsampleec.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.Order;
import com.example.springbootsampleec.entities.User;

/**
 * 注文履歴保存用 @Repository
 * 
 * @author Tanaka asaka
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Boolean existsByUserAndItem(User user, Item item);
	Optional<Order> findTopByUserAndItem(User user, Item item);

}
