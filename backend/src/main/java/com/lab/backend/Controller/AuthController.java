package com.lab.backend.Controller;

import com.lab.backend.Configuration.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        // En este ejemplo simple, no hay DB, solo usuario hardcode
        String username = user.get("username");
        String password = user.get("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}
