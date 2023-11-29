package yablunovskyi.bookstore.service.impl;

import org.springframework.security.core.Authentication;
import yablunovskyi.bookstore.dto.shoppingcart.CartResponseDto;
import yablunovskyi.bookstore.dto.shoppingcart.CreateCartItemRequestDto;
import yablunovskyi.bookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import yablunovskyi.bookstore.service.ShoppingCartService;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Override
    public CartResponseDto addCartItem(
            Authentication authentication, CreateCartItemRequestDto requestDto) {
        return null;
    }
    
    @Override
    public CartResponseDto updateCartItem(
            Authentication authentication, Long id, UpdateCartItemRequestDto requestDto) {
        return null;
    }
    
    @Override
    public CartResponseDto deleteCartItem(Authentication authentication, Long id) {
        return null;
    }
}
