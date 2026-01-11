package miniAssessment.AuthService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.sql.Timestamp;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 to 50 characters")
    private String name;
    @NotBlank(message = "Password is required")
    @Size(min = 6,message = "Password must be atleast 6 characters")
    private String password;

    private Timestamp timestamp;

    @PrePersist
    public void prePersist() {
        timestamp = new Timestamp(System.currentTimeMillis());
    }
}
