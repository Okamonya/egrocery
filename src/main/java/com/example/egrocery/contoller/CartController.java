package com.example.egrocery.contoller;

import com.example.egrocery.ApiResponse;
import com.example.egrocery.dto.cart.AddToCartDto;
import com.example.egrocery.dto.cart.CartDto;
import com.example.egrocery.exception.AuthenticationFailException;
import com.example.egrocery.exception.ProductDoesNotExistException;
import com.example.egrocery.model.Product;
import com.example.egrocery.model.User;
import com.example.egrocery.service.CartService;
import com.example.egrocery.service.ProductService;
import com.example.egrocery.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/add/{token}")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam(required=false, name = "token") String token) throws ProductDoesNotExistException {
        tokenService.authenticate(token);

        User user = tokenService.getUser(token);


        Product product = productService.findById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);

        return new ResponseEntity<>(new ApiResponse(true, "added to cart"),HttpStatus.CREATED);
    }

    @GetMapping("/items/{token}")
    public ResponseEntity<CartDto> getCartItems(@RequestParam(required = false, name = "token") String token) throws AuthenticationFailException {
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    @PutMapping("/update/{cartItemId}/token")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam(required=false,name = "token") String token) throws AuthenticationFailException, ProductDoesNotExistException, ProductDoesNotExistException {
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Product product = productService.findById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user,product);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}
