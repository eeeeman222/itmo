package org.ifmo.laba4.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.ifmo.laba4.domain.User;
import org.ifmo.laba4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.ifmo.laba4.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:3000", maxAge = 3600)
public class AuthController {

    JwtUtil jwtUtil = new JwtUtil();
    @Autowired
    private UserService userService;

    @PostMapping("api/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/auth/status")
    public Map<String, Boolean> checkAuthStatus(Authentication authentication) {
        System.out.println(authentication);
        Map<String, Boolean> response = new HashMap<>();
        response.put("authenticated", authentication != null && authentication.isAuthenticated());
        return response;
    }

    @PostMapping("/api/auth/reg")
    public User reg(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            var a = userService.loadUserByUsername(user.getUsername());
            if(userService.log(user, a.getPassword())){
                String token = jwtUtil.generateToken(user.getUsername());
                System.out.println(token);
                return ResponseEntity.ok(Map.of("token", token));
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
    }
}