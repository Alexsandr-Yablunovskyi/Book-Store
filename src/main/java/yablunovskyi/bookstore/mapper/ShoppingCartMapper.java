package yablunovskyi.bookstore.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.shoppingcart.CartResponseDto;
import yablunovskyi.bookstore.model.ShoppingCart;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartResponseDto toDto(ShoppingCart shoppingCart);
}
