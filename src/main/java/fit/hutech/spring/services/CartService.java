package fit.hutech.spring.services;

import fit.hutech.spring.daos.Cart;
import fit.hutech.spring.daos.Item;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService {
    private static final String CART_SESSION_KEY = "cart";

    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart) session.getAttribute(CART_SESSION_KEY)).orElseGet(() -> {
            Cart cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
            return cart;
        });
    }

    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream().mapToInt(Item::getQuantity).sum();
    }

    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
