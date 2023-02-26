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

/**
 * 注文履歴保存用 Entity
 * @author Tanaka asaka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/* order だとSQLの予約語だからエラー
  @Table(name = "`order`")
*/
@Table(name = "orders")
@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id

	//5-3 1:nのリレーションとは ～ 5-4 @OneToMany の利用
	@ManyToOne(fetch = FetchType.LAZY)
	private User user; // 購入したユーザーのidを保存？

	@ManyToOne(fetch = FetchType.EAGER)
	private Item item; // 購入した商品のidを保存？

	// 購入時の単価
	@Column(name = "price", nullable = false)
	private int price;

	// 購入数
	@Column(name = "amount", nullable = false)
	private int amount;

	// 購入日時
	@Column(name = "orderAt", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime orderAt;

}
