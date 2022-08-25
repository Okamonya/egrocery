package com.example.egrocery.service;

import com.example.egrocery.dto.cart.AddToCartDto;
import com.example.egrocery.dto.cart.CartDto;
import com.example.egrocery.dto.cart.CartItemDto;
import com.example.egrocery.model.Cart;
import com.example.egrocery.model.Product;
import com.example.egrocery.model.User;
import com.example.egrocery.repository.CartRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepo cartRepo;

    public CartService(){}

    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    public void addToCart(AddToCartDto addToCartDto,Product product, User user) {
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    private CartItemDto getDtoFromCart(Cart cart) {

        return new CartItemDto(cart);
    }

    public void updateCartItem(AddToCartDto cartDto, User user,Product product){
        Cart cart = cartRepo.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepo.save(cart);
    }
}
