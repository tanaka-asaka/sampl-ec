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
    
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private List<Cart> carts;
 
    @Column(name = "name", length = 60, nullable = false)
    private String name; // ユーザー名
 
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // メールアドレス
 
    @Column(name = "password", length = 100, nullable = false)
    private String password;  // パスワード
 
    @Column(name = "roles", length = 120)
    private String roles; // ロール（役割）
 
    @Column(name = "enable_flag", nullable = false)
    private Boolean enable; // 有効フラグ
    
    public int getTotalAmount() {
    	int total = 0;
    	for(Cart cart : carts) {
    		total += cart.getAmount() * cart.getItem().getPrice();
    	}
    	return total;
    }
}