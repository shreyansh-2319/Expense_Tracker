import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRegistering } from './user-registering.service';

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private apiUrl = 'http://localhost:9091/expenses';

  constructor(private http: HttpClient, private authService: UserRegistering) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAllExpenses(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`, {
      headers: this.getHeaders()
    });
  }

  getExpenseById(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  addExpense(expense: any): Observable<string> {
    return this.http.post(`${this.apiUrl}/add`, expense, {
      headers: this.getHeaders(),
      responseType: 'text'
    });
  }

  updateExpense(id: string, expense: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`, expense, {
      headers: this.getHeaders()
    });
  }

  deleteExpense(id: string): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }

  getTotalByDateRange(startDate: string, endDate: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/total/daterange?startDate=${startDate}&endDate=${endDate}`, {
      headers: this.getHeaders()
    });
  }
}
