import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ExpenseService } from '../../Services/expense.service';

@Component({
  selector: 'app-expense-list-component',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './expense-list-component.html',
  styleUrl: './expense-list-component.scss',
})
export class ExpenseListComponent implements OnInit {
  expenses: any[] = [];
  startDate: string = '';
  endDate: string = '';
  showTotal: boolean = false;
  totalAmount: number = 0;
  expenseCount: number = 0;

  constructor(private expenseService: ExpenseService, private router: Router) {}

  ngOnInit() {
    this.loadExpenses();
  }

  loadExpenses() {
    this.expenseService.getAllExpenses().subscribe({
      next: (data) => {
        this.expenses = data;
      },
      error: (err) => {
        console.error('Error loading expenses:', err);
        alert('Failed to load expenses');
      }
    });
  }

  filterByDateRange() {
    if (!this.startDate || !this.endDate) {
      alert('Please select both start and end dates');
      return;
    }

    this.expenseService.getTotalByDateRange(this.startDate, this.endDate).subscribe({
      next: (data) => {
        this.totalAmount = data.totalAmount;
        this.expenseCount = data.expenseCount;
        this.showTotal = true;
      },
      error: (err) => {
        console.error('Error getting total:', err);
        alert('Failed to get total');
      }
    });
  }

  editExpense(id: string) {
    this.router.navigate(['/expenses/update', id]);
  }

  deleteExpense(id: string) {
    if (confirm('Are you sure you want to delete this expense?')) {
      this.expenseService.deleteExpense(id).subscribe({
        next: () => {
          alert('Expense deleted successfully');
          this.loadExpenses();
        },
        error: (err) => {
          console.error('Error deleting expense:', err);
          alert('Failed to delete expense');
        }
      });
    }
  }
}
