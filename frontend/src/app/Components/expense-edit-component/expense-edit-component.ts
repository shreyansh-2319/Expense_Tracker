import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ExpenseService } from '../../Services/expense.service';

@Component({
  selector: 'app-expense-edit-component',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './expense-edit-component.html',
  styleUrl: './expense-edit-component.scss',
})
export class ExpenseEditComponent implements OnInit {
  expenseId: string = '';
  date: string = '';
  category: string = '';
  description: string = '';
  amount: number = 0;

  constructor(
    private expenseService: ExpenseService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.expenseId = this.route.snapshot.params['id'];
    this.loadExpense();
  }

  loadExpense() {
    this.expenseService.getExpenseById(this.expenseId).subscribe({
      next: (expense) => {
        this.date = expense.date;
        this.category = expense.category;
        this.description = expense.description;
        this.amount = expense.amount;
      },
      error: (err) => {
        console.error('Error loading expense:', err);
        alert('Failed to load expense');
        this.router.navigate(['/expenses']);
      }
    });
  }

  updateExpense(form: any) {
    if (form.valid) {
      const payload = {
        date: this.date,
        category: this.category,
        description: this.description,
        amount: this.amount
      };

      this.expenseService.updateExpense(this.expenseId, payload).subscribe({
        next: (response) => {
          alert('Expense updated successfully!');
          this.router.navigate(['/expenses']);
        },
        error: (err) => {
          alert('Failed to update expense');
          console.error('Error updating expense:', err);
        }
      });
    }
  }
}
