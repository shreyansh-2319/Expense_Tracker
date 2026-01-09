package miniAssessment.AuthService.repo;

import miniAssessment.AuthService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface AuthRepo extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);

}
