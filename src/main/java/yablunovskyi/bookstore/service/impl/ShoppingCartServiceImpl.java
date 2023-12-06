package yablunovskyi.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yablunovskyi.bookstore.dto.shoppingcart.CartResponseDto;
import yablunovskyi.bookstore.dto.shoppingcart.CreateCartItemRequestDto;
import yablunovskyi.bookstore.dto.shoppingcart.UpdateCartItemRequestDto;
import yablunovskyi.bookstore.exception.EntityNotFoundException;
import yablunovskyi.bookstore.mapper.CartItemMapper;
import yablunovskyi.bookstore.mapper.ShoppingCartMapper;
import yablunovskyi.bookstore.model.CartItem;
import yablunovskyi.bookstore.model.ShoppingCart;
import yablunovskyi.bookstore.model.User;
import yablunovskyi.bookstore.repository.BookRepository;
import yablunovskyi.bookstore.repository.CartItemRepository;
import yablunovskyi.bookstore.repository.ShoppingCartRepository;
import yablunovskyi.bookstore.repository.UserRepository;
import yablunovskyi.bookstore.service.ShoppingCartService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    
    @Override
    @Transactional
    public CartResponseDto addCartItem(
            Authentication authentication, CreateCartItemRequestDto requestDto) {
        checkIfBookExists(requestDto.bookId());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(authentication.getName())
                .orElseGet(
                        () -> createUserShoppingCart((User) authentication.getPrincipal()
                ));
        cartItemRepository.findByShoppingCartAndBookId(shoppingCart, requestDto.bookId())
                .ifPresentOrElse(
                        cartItem -> cartItem.setQuantity(
                                cartItem.getQuantity() + requestDto.quantity()),
                        () -> createCartItem(requestDto, shoppingCart)
                );
        return shoppingCartMapper.toDto(shoppingCart);
    }
    
    private void checkIfBookExists(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new EntityNotFoundException(
                    "Can't find a book with id: %d".formatted(bookId)
            );
        }
    }
    
    private ShoppingCart createUserShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userRepository.getReferenceById(user.getId()));
        return shoppingCartRepository.save(shoppingCart);
    }
    
    private void createCartItem(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toCartItem(requestDto);
        shoppingCart.addCartItem(cartItem);
        cartItem.setBook(bookRepository.getReferenceById(requestDto.bookId()));
        cartItemRepository.save(cartItem);
    }
    
    @Override
    public CartResponseDto getUserCart(Authentication authentication) {
        ShoppingCart shoppingCart = getUserShoppingCart(authentication.getName());
        return shoppingCartMapper.toDto(shoppingCart);
    }
    
    private ShoppingCart getUserShoppingCart(String email) {
        return shoppingCartRepository.findByUserEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find a cart for a user with email: %s".formatted(email)
                        )
                );
    }
    
    @Override
    @Transactional
    public CartResponseDto updateCartItem(
            Authentication authentication, Long id, UpdateCartItemRequestDto requestDto) {
        ShoppingCart shoppingCart = getUserShoppingCart(authentication.getName());
        CartItem cartItem = findCartItemByIdAndUser(id, authentication.getName());
        cartItem.setQuantity(requestDto.quantity());
        return shoppingCartMapper.toDto(shoppingCart);
    }
    
    private CartItem findCartItemByIdAndUser(Long id, String email) {
        return cartItemRepository.findByIdAndShoppingCartUserEmail(id, email)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find a cart item with id: %d in user's cart with email: %s"
                                        .formatted(id, email)
                        )
                );
    }
    
    @Override
    @Transactional
    public void deleteCartItem(Authentication authentication, Long id) {
        ShoppingCart shoppingCart = getUserShoppingCart(authentication.getName());
        CartItem cartItem = findCartItemByIdAndUser(id, authentication.getName());
        shoppingCart.removeCartItem(cartItem);
        //return shoppingCartMapper.toDto(shoppingCart);
    }
}
