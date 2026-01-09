package miniAssessment.ExpenseService.controller;

import jakarta.validation.Valid;
import miniAssessment.ExpenseService.model.Expenses;
import miniAssessment.ExpenseService.repo.ExpenseRepo;
import miniAssessment.ExpenseService.service.ExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/expenses")
@RestController
public class ExpenseController {
    @Autowired
    private ExpService service;
    @Autowired
    private ExpenseRepo repo;

    @GetMapping("/all")
    public ResponseEntity<List<Expenses>> getAllExpenses(Authentication authentication){
        UUID userId= UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(service.getAllExpense(userId));
    }
    @PostMapping("/add")
    public ResponseEntity<String> addExpense(@Valid @RequestBody Expenses expense, Authentication authentication){
        UUID userId=UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.addExpense(userId,expense));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Expenses> updateExpense(@Valid @RequestBody Expenses expense, @PathVariable UUID id, Authentication authentication ){
        UUID userId=UUID.fromString((String) authentication.getPrincipal());
        try{
            Expenses updated= service.updateExpense(id,expense,userId);
            return ResponseEntity.ok(updated);
        }catch (RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable UUID id, Authentication authentication){
        UUID userId=UUID.fromString((String) authentication.getPrincipal());
        try{
            service.deleteExpense(id,userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }}
    @GetMapping("/{id}")
    public ResponseEntity<Expenses> getExpenseById(@PathVariable UUID id, Authentication authentication) {
        UUID userId = UUID.fromString((String) authentication.getPrincipal());
        Expenses expense = repo.findById(id)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/total")
    public ResponseEntity<Map<String,Object>> getTotal(Authentication authentication){
        UUID userId=UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(Map.of("Total : ",service.getTotal(userId)));
    }

    @GetMapping("/total/daterange")
    public ResponseEntity<Map<String, Object>> getTotalByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            Authentication authentication) {
        UUID userId = UUID.fromString((String) authentication.getPrincipal());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            return ResponseEntity.ok(service.getTotalByDateRange(userId, start, end));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid date format. Use yyyy-MM-dd"));
        }
    }
    @DeleteMapping("/internal/user/{userId}")
    public ResponseEntity<Void> deleteUserExpenses(@PathVariable UUID userId){
        repo.deleteByUserId(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
