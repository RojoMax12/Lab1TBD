package com.lab.backend.Controller;

import com.lab.backend.Configuration.JwtUtil;
import com.lab.backend.Entities.DriverEntity;
import com.lab.backend.Services.DriverServices;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final DriverServices driverServices;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(JwtUtil jwtUtil, DriverServices driverServices) {
        this.jwtUtil = jwtUtil;
        this.driverServices = driverServices;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        // Mantener compatibilidad con el login hardcodeado para admin
        String username = user.get("username");
        String password = user.get("password");

        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    // Login para drivers (email + password) que devuelve JWT
    @PostMapping("/login-driver")
    public Map<String, String> loginDriver(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) throw new RuntimeException("Faltan credenciales");

        DriverEntity driver = driverServices.getDriverByEmail(email);
        if (driver == null) throw new RuntimeException("Credenciales inválidas");

        if (passwordEncoder.matches(password, driver.getPassword())) {
            String token = jwtUtil.generateToken(driver.getEmail());
            return Map.of("token", token);
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    // Registro rápido de driver: crea driver y devuelve JWT
    @PostMapping("/register-driver")
    public Map<String, Object> registerDriver(@RequestBody DriverEntity driverEntity) {
        if (driverEntity.getEmail() == null || driverEntity.getPassword() == null) {
            throw new RuntimeException("Faltan datos obligatorios");
        }
        DriverEntity created = driverServices.CreateDriver(driverEntity);
        String token = jwtUtil.generateToken(created.getEmail());
        return Map.of("id", created.getId(), "token", token);
    }
}
