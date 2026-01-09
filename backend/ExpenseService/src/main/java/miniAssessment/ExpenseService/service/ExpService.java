package miniAssessment.ExpenseService.service;

import miniAssessment.ExpenseService.model.Expenses;
import miniAssessment.ExpenseService.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ExpService {
    @Autowired
    private ExpenseRepo repo;

    public List<Expenses> getAllExpense(UUID userId) {
        return repo.findAllByUserId(userId);
    }

    public String addExpense(UUID userId, Expenses expense) {
        expense.setUserId(userId);
        expense.setCreatedAt(LocalDateTime.now());
        repo.save(expense);
        return "Expense added successfully";
    }

    public Expenses updateExpense(UUID id, Expenses expense, UUID userId) {
        return repo.findById(id)
                .filter(e->e.getUserId().equals(userId))
                .map(existing->{
                    existing.setDate(expense.getDate());
                    existing.setCategory(expense.getCategory());
                    existing.setDescription(expense.getDescription());
                    existing.setAmount(expense.getAmount());
                    return  repo.save(existing);
                }).orElseThrow(()->new RuntimeException("Expense not found or not owned by user"));
    }

    public String deleteExpense(UUID id, UUID userId) {
        Expenses expense=repo.findById(id).orElseThrow(()->new RuntimeException("Expense does not exist!"));
        if(!expense.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this expense");
        }
        repo.delete(expense);
        return "Expense deleted successfully!";
        }

    public int getTotal(UUID userId) {
        return repo.getTotalbyUserId(userId);
    }

    public Map<String, Object> getTotalByDateRange(UUID userId, Date startDate, Date endDate) {
        Integer total = repo.getTotalByUserIdAndDateRange(userId, startDate, endDate);
        Long count = repo.countByUserIdAndDateRange(userId, startDate, endDate);
        return Map.of(
            "totalAmount", total,
            "expenseCount", count,
            "startDate", startDate,
            "endDate", endDate
        );
    }
}
