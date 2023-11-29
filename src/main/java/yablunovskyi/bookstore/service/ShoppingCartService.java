package yablunovskyi.bookstore.service;

import org.springframework.security.core.Authentication;
import yablunovskyi.bookstore.dto.shoppingcart.CartResponseDto;
import yablunovskyi.bookstore.dto.shoppingcart.CreateCartItemRequestDto;
import yablunovskyi.bookstore.dto.shoppingcart.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    CartResponseDto addCartItem(Authentication authentication, CreateCartItemRequestDto requestDto);
    
    CartResponseDto updateCartItem(
            Authentication authentication, Long id, UpdateCartItemRequestDto requestDto);
    
    CartResponseDto deleteCartItem(Authentication authentication, Long id);
}
