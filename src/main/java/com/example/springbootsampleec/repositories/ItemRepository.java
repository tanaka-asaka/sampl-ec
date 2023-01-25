package com.example.springbootsampleec.repositories;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.example.springbootsampleec.entities.Item;

import java.util.List;
import java.util.Optional;
 
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	Optional<Item> findById(int id);
}