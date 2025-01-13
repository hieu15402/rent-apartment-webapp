package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c JOIN Users u ON c.users.id = u.id WHERE u.id = ?1")
    List<Cart> findCartByUserId(Long id);
}
