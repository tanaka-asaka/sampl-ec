package com.example.springbootsampleec.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootsampleec.entities.History;
import com.example.springbootsampleec.entities.Cart;
import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;

@Repository
public interface HistoryRepository  extends JpaRepository<History, Long>{
	Boolean existsByUserAndItem(User user, Item item);
	Optional<History> findTopByUserAndItem(User user, Item item);

}
