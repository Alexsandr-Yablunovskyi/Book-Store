package yablunovskyi.bookstore.dto.shoppingcart;

import java.util.List;

public record CartResponseDto(
        Long id,
        Long userId,
        List<CartItemDto> cartItems
) {
}
