package miniAssessment.ExpenseService.repo;

import miniAssessment.ExpenseService.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepo extends JpaRepository<Expenses, UUID> {

    List<Expenses> findAllByUserId(UUID userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e WHERE e.userId = :userId")
    Integer getTotalbyUserId(@Param("userId") UUID userId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate")
    Integer getTotalByUserIdAndDateRange(@Param("userId") UUID userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(e) FROM Expenses e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate")
    Long countByUserIdAndDateRange(@Param("userId") UUID userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void deleteByUserId(UUID userId);
}
