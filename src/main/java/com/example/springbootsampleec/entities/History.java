package com.example.springbootsampleec.entities;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
@Entity
public class History {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id

	@ManyToOne(fetch = FetchType.LAZY)
	private User user; // 購入したユーザーのid

	@ManyToOne(fetch = FetchType.EAGER)
	private Item item; // 購入した商品のid

	// 購入数
	@Column(name = "amount", nullable = false)
    private int amount;

	// 購入時の金額
	@Column(name = "price", nullable = false)
    private int price; // 金額

	// 購入日時
	@Column(name = "purchaseAt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime purchaseAt;
}
