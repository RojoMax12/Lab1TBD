package com.lab.backend.Controller;

import com.lab.backend.Configuration.JwtUtil;
import com.lab.backend.Entities.DriverEntity;
import com.lab.backend.Services.DriverServices;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String email = user.get("email");
        String password = user.get("password");

        // Imprime los datos de entrada para depuración
        System.out.println("Login attempt - Username: " + username + ", Email: " + email + ", Password: " + password);

        // Verifica si los campos email o password están vacíos
        if (email == null || password == null) {
            System.out.println("Missing credentials - Email or password not provided");
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan credenciales"));
        }

        // Intentar obtener el conductor desde la base de datos por su correo electrónico
        DriverEntity driver = driverServices.getDriverByEmail(email);

        // Verificar si el conductor existe
        if (driver == null) {
            System.out.println("Invalid credentials - No driver found for email: " + email);
            return ResponseEntity.status(403).body(Map.of("error", "Credenciales inválidas"));
        }

        // Verificar si la contraseña es correcta
        if (passwordEncoder.matches(password, driver.getPassword())) {
            String token = jwtUtil.generateToken(driver.getEmail(), "driver");
            System.out.println("Login successful for driver with email: " + email);
            return ResponseEntity.ok(Map.of("token", token));  // Retornar el token en la respuesta
        }

        // Caso para el admin (Hardcoded para ejemplo, no recomendado en producción)
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username, "admin");
            System.out.println("Login successful for admin with username: " + username);
            return ResponseEntity.ok(Map.of("token", token));  // Retornar el token de admin
        }

        // Si las credenciales no son correctas
        System.out.println("Invalid credentials - Incorrect password or username");
        return ResponseEntity.status(403).body(Map.of("error", "Credenciales inválidas"));
    }


    // Registro rápido de driver: crea driver y devuelve JWT
    @PostMapping("/register-driver")
    public Map<String, Object> registerDriver(@RequestBody DriverEntity driverEntity) {
        if (driverEntity.getEmail() == null || driverEntity.getPassword() == null) {
            throw new RuntimeException("Faltan datos obligatorios");
        }
        DriverEntity created = driverServices.CreateDriver(driverEntity);
        String token = jwtUtil.generateToken(created.getEmail(), "driver");
        return Map.of("id", created.getId(), "token", token);
    }
}
