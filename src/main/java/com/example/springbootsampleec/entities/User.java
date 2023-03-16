package com.example.springbootsampleec.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Order> orders;

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

			// 確認用コンソール出力
			System.out.println("getTotalAmount total :" + total);
		}
		return total;
	}

	// 注文履歴用、合計金額の取得
	public long getOrderTotalPrice() {
		long total = 0;

		// 確認用コンソール出力
		System.out.println("注文履歴用、合計金額の取得");
		System.out.println("getOrderTotalPrice :" + total);

		for (Order order : orders) {
			total += order.getAmount() * order.getPrice();

			// 確認用コンソール出力
			System.out.println("単価 * 数量:" + order.getPrice() + " * " + order.getAmount() + " = "
					+ order.getAmount() * order.getPrice());
			System.out.println("getOrderTotalPrice :" + total);
		}
		return total;
	}
}