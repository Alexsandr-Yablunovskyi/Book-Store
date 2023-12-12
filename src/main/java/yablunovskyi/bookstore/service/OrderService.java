package yablunovskyi.bookstore.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import yablunovskyi.bookstore.dto.order.CreateOrderRequestDto;
import yablunovskyi.bookstore.dto.order.OrderItemDto;
import yablunovskyi.bookstore.dto.order.OrderResponseDto;
import yablunovskyi.bookstore.dto.order.UpdateStatusRequestDto;

public interface OrderService {
    OrderResponseDto addOrder(CreateOrderRequestDto requestDto);
    
    List<OrderResponseDto> getOrders(Pageable pageable);
    
    OrderResponseDto updateOrder(Long id, UpdateStatusRequestDto requestDto);
    
    List<OrderItemDto> getOrderItemsByOrder(Long id);
    
    OrderItemDto getOrderItemById(Long orderId, Long id);
}
