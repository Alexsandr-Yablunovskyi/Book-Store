package yablunovskyi.bookstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.shoppingcart.CartItemDto;
import yablunovskyi.bookstore.dto.shoppingcart.CreateCartItemRequestDto;
import yablunovskyi.bookstore.model.CartItem;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);
    
    CartItem toCartItem(CreateCartItemRequestDto requestDto);
}
