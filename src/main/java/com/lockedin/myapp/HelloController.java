package com.lockedin.myapp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
public class HelloController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductRepo productRepo;
    private boolean authUser = false; // Tracks user authentication
    private final CartRepository cartRepository;
    private Cart cart = new Cart(); // Simulating a session cart for now

    public HelloController(UserRepository userRepository,
                           EmailService emailService,
                           ProductRepository productRepository,
                           ProductRepo productRepo,
                           CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productRepo = productRepo;
        this.cartRepository = cartRepository;
    }

    @GetMapping("/")
    public String defaultLink() {
        return "custom-login";
    }

    @GetMapping("/logout1")
    public String logoutLink() {
        authUser = false; // Reset authentication
        return "redirect:/custom-login";
    }
        

    @GetMapping("/custom-login")
    public String main() {
        return "custom-login";
    }

    @PostMapping("/custom-login")
public String processLogin(@RequestParam String email,
                           @RequestParam String password,
                           Model model,
                           HttpSession session) {
    Optional<User> userOptional = userRepository.findByEmail(email.trim().toLowerCase());

    if (userOptional.isPresent()) {
        User user = userOptional.get();
        if (user.getPassword().equals(password)) {
            authUser = true; // Set authUser to true upon successful login
            session.setAttribute("loggedInUserId", user.getId()); // Store user ID in session
            return "redirect:/products";
        } else {
            model.addAttribute("errorMessage", "Incorrect password.");
        }
    } else {
        model.addAttribute("errorMessage", "User not found.");
    }

    return "custom-login";
}


    private boolean isAuthenticated(Model model) {
        if (!authUser) {
            model.addAttribute("errorMessage", "You must be logged in to access this page.");
            return false;
        }
        return true;
    }

    @GetMapping("/products")
    public String listingPage(@RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "minPrice", required = false) Double minPrice,
                              @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                              Model model) {
        if (!isAuthenticated(model)) return "custom-login";

        List<Product> products = null;
        try {
            if (search != null && !search.isEmpty()) {
                products = productRepository.findByName(search);
            } else if (minPrice != null && maxPrice != null) {
                products = productRepository.findByPriceRange(minPrice, maxPrice);
            } else {
                products = productRepository.findAll();
            }
            // Log the number of products retrieved
            System.out.println("Number of products retrieved: " + products.size());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error fetching products.");
        }

        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/sell")
    public String sell(Model model) {
        if (!isAuthenticated(model)) return "custom-login";
        return "sell";
    }

    @PostMapping("/sell")
    public String processSell(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") double price,
                              @RequestParam("images") MultipartFile[] images,
                              Model model) {
        if (!isAuthenticated(model)) return "custom-login";

        try {
            String uploadDir = "C:\\Users\\subha\\OneDrive\\Desktop\\website\\uploads";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);

            for (MultipartFile image : images) {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                File savedFile = new File(uploadPath, fileName);
                image.transferTo(savedFile);
                product.getImagePaths().add(fileName);
            }

            productRepository.save(product);
            productRepo.save(product);
            model.addAttribute("message", "Product listed successfully!");
            return "sell";
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading images.");
            return "sell";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        // Check if the email is already registered
        if (userRepository.findByEmail(email.trim().toLowerCase()).isPresent()) {
            model.addAttribute("error", "Email is already registered.");
            return "signup";
        }

        // Create and save the new user
        User newUser = new User();
        newUser.setEmail(email.trim().toLowerCase());
        newUser.setPassword(password); // Ideally, passwords should be hashed before saving
        userRepository.save(newUser);

        // Authenticate the user
        authUser = true;

        // Redirect to products page after successful signup and authentication
        return "redirect:/products";
    }

    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public Resource getImage(@PathVariable String filename) {
        // Use a relative path or a path based on the system property
        String uploadDir = Paths.get("uploads").toAbsolutePath().toString(); // Platform-independent way to get the "uploads" folder
        return new FileSystemResource(Paths.get(uploadDir, filename).toFile()); // Combine the upload directory and filename
    }

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password"; // Loads templates/forgot-password.html
    }


    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("totalPrice", cart.getItems().stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            Model model) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.addItem(cartItem);
        }
        return "redirect:/cart";
    }

    @Autowired
    private StripeService stripeService;
    
    @PostMapping("/cart/buy")
    public ResponseEntity<Void> buyNow(Model model) {
        try {
            double totalPrice = cart.getItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
    
            // Create the checkout session and get the URL
            String checkoutUrl = stripeService.createCheckoutSession(totalPrice);
    
            // Return a proper redirect response
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(checkoutUrl)).build();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create payment session.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/profile")
public String profile(Model model, HttpSession session) {
    if (!authUser) {
        model.addAttribute("errorMessage", "You must be logged in to view your profile.");
        return "custom-login";
    }

    Long loggedInUserId = (Long) session.getAttribute("loggedInUserId"); // Get user ID from session
    if (loggedInUserId != null) {
        Optional<User> userOptional = userRepository.findById(loggedInUserId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("createdAt", user.getCreatedAt());
        } else {
            model.addAttribute("errorMessage", "User not found.");
            return "custom-login";
        }
    } else {
        model.addAttribute("errorMessage", "No user is logged in.");
        return "custom-login";
    }

    return "profile";
}


    @GetMapping("/cart/success")
public String paymentSuccess(Model model) {
    // Clear the cart upon successful payment
    cart.getItems().clear();
    cartRepository.save(cart); // Persist the updated cart if needed
    
    model.addAttribute("message", "Payment successful! Your cart has been cleared.");
    return "products";
}


@GetMapping("/cart/cancel")
public String paymentCancel(Model model) {
    model.addAttribute("message", "Payment canceled.");
    return "cart-cancel";
}

}
