package yablunovskyi.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import yablunovskyi.bookstore.dto.shoppingcart.CartResponseDto;
import yablunovskyi.bookstore.dto.shoppingcart.CreateCartItemRequestDto;
import yablunovskyi.bookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import yablunovskyi.bookstore.service.ShoppingCartService;

@Tag(name = "Shopping cart management", description = "Endpoints to managing shopping cart")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a book to user cart",
            description = "Add a book and its quantity to the logged-up user cart")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public CartResponseDto addCartItem(Authentication authentication,
                                       @RequestBody @Valid CreateCartItemRequestDto requestDto) {
        return shoppingCartService.addCartItem(authentication, requestDto);
    }
    
    @Operation(summary = "Get user cart",
            description = "Get all information about items from the logged-up user cart")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public CartResponseDto getUserCart(Authentication authentication) {
        return shoppingCartService.getUserCart(authentication);
    }
    
    @Operation(summary = "Update cart item",
            description = "Update information (book quantity) of the specific cart item")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cart-items/{cartItemId}")
    public CartResponseDto updateCartItem(
            Authentication authentication,
            @PathVariable @Positive Long cartItemId,
            @RequestBody @Valid UpdateCartItemRequestDto requestDto) {
        return shoppingCartService.updateCartItem(authentication, cartItemId, requestDto);
    }
    
    @Operation(summary = "Delete cart item",
            description = "Delete cart item from the logged-up user shopping cart")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    public CartResponseDto deleteCartItem(
            Authentication authentication,
            @PathVariable @Positive Long cartItemId) {
        return shoppingCartService.deleteCartItem(authentication, cartItemId);
    }
}
