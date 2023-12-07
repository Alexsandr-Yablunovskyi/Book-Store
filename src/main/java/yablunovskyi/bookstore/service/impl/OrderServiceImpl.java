package yablunovskyi.bookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yablunovskyi.bookstore.dto.order.CreateOrderRequestDto;
import yablunovskyi.bookstore.dto.order.OrderItemDto;
import yablunovskyi.bookstore.dto.order.OrderResponseDto;
import yablunovskyi.bookstore.dto.order.UpdateStatusRequestDto;
import yablunovskyi.bookstore.exception.EntityNotFoundException;
import yablunovskyi.bookstore.mapper.OrderItemMapper;
import yablunovskyi.bookstore.mapper.OrderMapper;
import yablunovskyi.bookstore.model.CartItem;
import yablunovskyi.bookstore.model.Order;
import yablunovskyi.bookstore.model.OrderItem;
import yablunovskyi.bookstore.model.ShoppingCart;
import yablunovskyi.bookstore.model.User;
import yablunovskyi.bookstore.repository.OrderItemRepository;
import yablunovskyi.bookstore.repository.OrderRepository;
import yablunovskyi.bookstore.repository.ShoppingCartRepository;
import yablunovskyi.bookstore.repository.UserRepository;
import yablunovskyi.bookstore.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    
    @Override
    @Transactional
    public OrderResponseDto addOrder(CreateOrderRequestDto requestDto) {
        User user = findUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(user.getEmail())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find shopping cart by users email: %s"
                                        .formatted(user.getEmail())
                        )
                );
        Order order = createNewOrder(user, requestDto);
        order.setOrderItems(createOrderItems(order, shoppingCart.getCartItems()));
        shoppingCartRepository.delete(shoppingCart);
        return orderMapper.toDto(orderRepository.save(order));
    }
    
    @Override
    public List<OrderResponseDto> getOrders(Pageable pageable) {
        return orderMapper.toDtoList(orderRepository.findByUserId(findUser().getId(), pageable));
    }
    
    @Override
    @Transactional
    public OrderResponseDto updateOrder(Long id, UpdateStatusRequestDto requestDto) {
        Order order = orderRepository.getReferenceById(id);
        order.setStatus(Order.Status.valueOf(requestDto.status()));
        return orderMapper.toDto(order);
    }
    
    @Override
    public List<OrderItemDto> getOrderItemsByOrder(Long id) {
        return orderItemRepository.findAllByOrderId(findOrder(id).getId())
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }
    
    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long id) {
        return orderItemMapper.toDto(orderItemRepository
                .findByIdAndOrderId(id, findOrder(orderId).getId())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                        "Can't find order item with id: %d for order with id: %d"
                                .formatted(id, orderId))
                )
        );
    }
    
    private User findUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()
                -> new EntityNotFoundException("Can't find user by email %s".formatted(email)));
    }
    
    private Order createNewOrder(User user, CreateOrderRequestDto requestDto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus(Order.Status.NEW);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setTotal(BigDecimal.ZERO);
        return orderRepository.save(order);
    }
    
    private Set<OrderItem> createOrderItems(Order order, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);
            orderItem.setOrder(order);
            orderItems.add(orderItemRepository.save(orderItem));
            order.setTotal(order.getTotal()
                    .add(orderItem.getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()))));
        }
        return orderItems;
    }
    
    private Order findOrder(Long id) {
        return orderRepository.findByUserIdAndId(findUser().getId(), id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order for current user with id: %d".formatted(id))
        );
    }
}
