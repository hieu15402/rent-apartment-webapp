package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.CartDTO;
import com.example.vinhomeproject.dto.CartDTO_2;
import com.example.vinhomeproject.mapper.CartMapper;
import com.example.vinhomeproject.models.Cart;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.repositories.CartRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;
    @Autowired
    private CartMapper mapper;
    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<ResponseObject> getAll() {
        List<Cart> carts = repository.findAll();
        if (!carts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all card successfully",
                    carts
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Card null",
                null
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id) {
        Optional<Cart> cart = repository.findById(id);
        if (cart.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get card by id successfully",
                    cart
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Get card by id failed",
                null
        ));
    }

    public ResponseEntity<ResponseObject> update(Long id, CartDTO cartDTO) {
        Optional<Cart> cart = repository.findById(id);
        if (cart.isPresent()) {
            mapper.updateCart(cartDTO, cart.get());
            repository.save(cart.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update card successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found card by id",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> delete(Long id) {
        Optional<Cart> cart = repository.findById(id);
        if (cart.isPresent()) {
            cart.get().setStatus(!cart.get().isStatus());
            repository.save(cart.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete card successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found card by id",
                null
        ));
    }

    public ResponseEntity<ResponseObject> create(CartDTO cartDTO) {
        Cart cart = mapper.createCart(cartDTO);
        cart.setStatus(true);
        repository.save(cart);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Create card successfully",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> getByUserId(Long id) {
        List<Cart> carts = repository.findCartByUserId(id);
        List<CartDTO_2> list = new ArrayList<>();
        if(!carts.isEmpty()){
            for(int i = 0; i < carts.size();i++){
                list.add(mapper.cartDtoFromCart(carts.get(i)));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get list card by user id successfully",
                    list
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found card by user id",
                null
        ));
    }
}
