import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ExpenseService } from '../../Services/expense.service';

@Component({
  selector: 'app-expense-form-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './expense-form-component.html',
  styleUrl: './expense-form-component.scss',
})
export class ExpenseFormComponent {
  date: string = '';
  category: string = '';
  description: string = '';
  amount: number = 0;

  constructor(private expenseService: ExpenseService, private router: Router) {}

  addExpense(form: any) {
    if (form.valid) {
      const payload = {
        date: this.date,
        category: this.category,
        description: this.description,
        amount: this.amount
      };

      this.expenseService.addExpense(payload).subscribe({
        next: (response) => {
          alert('Expense added successfully!');
          this.router.navigate(['/expenses']);
        },
        error: (err) => {
          alert('Failed to add expense');
          console.error('Error adding expense:', err);
        }
      });
    }
  }
}
