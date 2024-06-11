package fit.hutech.spring.controllers;

import fit.hutech.spring.services.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String showCart(HttpSession session, @NotNull Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "book/cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session, @PathVariable Long id) {
        var cart = cartService.getCart(session);
        cart.removeItems(id);
        cartService.updateCart(session, cart);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session, @PathVariable Long id, @PathVariable int quantity) {
        var cart = cartService.getCart(session);
        cart.updateItems(id, quantity);
        cartService.updateCart(session, cart);
        return "redirect:/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cartService.removeCart(session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        return "book/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String name,
                                  @RequestParam String phone,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        // Validate name and phone number
        if (name == null || name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
            redirectAttributes.addFlashAttribute("error", "Invalid name");
            return "redirect:/cart/checkout";
        }
        if (phone == null || phone.isEmpty() || !phone.matches("\\d+")) {
            redirectAttributes.addFlashAttribute("error", "Invalid phone number");
            return "redirect:/cart/checkout";
        }

        // Process the checkout
        redirectAttributes.addFlashAttribute("cart", cartService.getCart(session));
        cartService.removeCart(session);
        redirectAttributes.addFlashAttribute("message", "Checkout successful");
        return "redirect:/cart/success";
    }


    @GetMapping("/success")
    public String success(HttpSession session, Model model) {
        model.addAttribute("cart", cartService.getCart(session));
        return "book/success";
    }

}
