package miniAssessment.AuthService.controller;

import jakarta.validation.Valid;
import miniAssessment.AuthService.model.Users;
import miniAssessment.AuthService.service.AuthoriseService;
import miniAssessment.AuthService.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {
    @Autowired
    private AuthoriseService service;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAll(){
    return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Users user) {
        Users savedUser = service.registerUser(user);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User Registered: " + savedUser.getName());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with this email already registered");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Users user){
        Users dbUser=service.findByEmail(user.getEmail());
        if(dbUser!=null && service.loginUser(user.getEmail(),user.getPassword()))
        {
            String token=jwtService.generateToken(dbUser.getEmail(), dbUser.getId());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid Credentials");
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMe(Authentication authentication){
        Users user=(Users) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "id",user.getId(),
                "email",user.getEmail(),
                "name",user.getName()
        ));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(Authentication authentication){
        Users user=(Users) authentication.getPrincipal();
        service.deleteUserById(user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
