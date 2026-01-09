    package miniAssessment.AuthService.service;

    import miniAssessment.AuthService.model.Users;
    import miniAssessment.AuthService.repo.AuthRepo;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.util.List;
    import java.util.UUID;

    @Service
    public class AuthoriseService {
        @Autowired
        private AuthRepo repo;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private RestTemplate restTemplate;

        public List<Users> getAll() {
            return repo.findAll();
        }

        public Users registerUser(Users user) {
            Users existUser = repo.findByEmail(user.getEmail());
            if (existUser == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setEmail(user.getEmail().toLowerCase());
                return repo.save(user);
            } else {
                return null;
            }
        }

        public boolean loginUser(String email, String password){
            Users existUser=repo.findByEmail(email.toLowerCase());
            if(existUser!=null)
            {
                return passwordEncoder.matches(password, existUser.getPassword());
            }
            return false;
        }

        public Users findByEmail(String email){
            return repo.findByEmail(email.toLowerCase());
        }

        public void deleteUserById(UUID userId){
            repo.deleteById(userId);
            try{
                restTemplate.delete("http://localhost:9091/expenses/internal/user/"+userId);
            }
            catch (Exception e){
                System.out.println("Error deleting expenses: "+e.getMessage());
            }
        }
    }
