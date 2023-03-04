package com.example.springbootsampleec.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Cart> carts;
	
	// 注文履歴用設定
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Order> orders;

	@Column(name = "name", length = 60, nullable = false)
	private String name; // ユーザー名

	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email; // メールアドレス

	@Column(name = "password", length = 100, nullable = false)
	private String password; // パスワード

	@Column(name = "roles", length = 120)
	private String roles; // ロール（役割）

	@Column(name = "enable_flag", nullable = false)
	private Boolean enable; // 有効フラグ

	public int getTotalAmount() {
		int total = 0;
		for (Cart cart : carts) {
			total += cart.getAmount() * cart.getItem().getPrice();
			
			//確認用コンソール出力
			System.out.println("getTotalAmount cart.getAmount() :" + cart.getAmount());
			System.out.println("getTotalAmount cart.getItem().getPrice() :" + cart.getItem().getPrice());
			System.out.println("getTotalAmount total :" + total);
		}
		return total;
	}
}