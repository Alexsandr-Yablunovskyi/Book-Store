package yablunovskyi.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import yablunovskyi.bookstore.dto.order.CreateOrderRequestDto;
import yablunovskyi.bookstore.dto.order.OrderItemDto;
import yablunovskyi.bookstore.dto.order.OrderResponseDto;
import yablunovskyi.bookstore.dto.order.UpdateStatusRequestDto;
import yablunovskyi.bookstore.service.OrderService;

@Tag(name = "Order management", description = "Endpoints to managing orders")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add an order",
            description = "Create an order from the logged-up user cart items")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        return orderService.addOrder(requestDto);
    }
    
    @Operation(summary = "Get orders history",
            description = "Get a list with all information about logged-up user previous orders")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<OrderResponseDto> getOrdersHistory(
            @ParameterObject @PageableDefault(size = 3) Pageable pageable
    ) {
        return orderService.getOrders(pageable);
    }
    
    @Operation(summary = "Update user status",
            description = "Update information about order status by orders' id")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderStatus(
            @PathVariable @Positive Long id,
            @RequestBody @Valid UpdateStatusRequestDto requestDto) {
        return orderService.updateOrder(id, requestDto);
    }
    
    @Operation(summary = "Get a list of order items",
            description = "Get a list with information about logged-up user specific order items")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getOrderItems(@PathVariable @Positive Long orderId) {
        return orderService.getOrderItemsByOrder(orderId);
    }
    
    @Operation(summary = "Get an order item by id",
            description = "Get an information about logged-up user order specific item by id")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/item/{id}")
    public OrderItemDto getOrderItem(
            @PathVariable @Positive Long orderId,
            @PathVariable @Positive Long id) {
        return orderService.getOrderItemById(orderId, id);
    }
}
