package yablunovskyi.bookstore.mapper;

import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import yablunovskyi.bookstore.dto.order.OrderItemDto;
import yablunovskyi.bookstore.dto.order.OrderResponseDto;
import yablunovskyi.bookstore.model.Order;
import yablunovskyi.bookstore.model.OrderItem;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);
    
    List<OrderResponseDto> toDtoList(List<Order> orders);
    
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
