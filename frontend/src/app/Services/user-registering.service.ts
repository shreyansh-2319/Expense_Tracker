import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserRegistering {
  private tokenKey = 'authToken';
  // private apiUrl = 'http://localhost:9090';

  constructor(private http: HttpClient) {}
    
  authenticateUser(email: string, password: string): Observable<string> {
    return this.http.post('http://localhost:9090/login', { email, password }, { responseType: 'text' });
  }
  
  registerUser(payload: {
    email: string;
    password: string;
    name: string;
  }): Observable<string> {
    return this.http.post('http://localhost:9090/register', payload, { responseType: 'text' });
  }

  storeToken(token: string): void {
    sessionStorage.setItem(this.tokenKey, token);  
  }

  getToken(): string | null {
    return sessionStorage.getItem(this.tokenKey);
  }

  decodeToken(): any {
    const token = this.getToken();
    if (!token) return null;
    const payload = token.split('.')[1];
    if (!payload) return null;
    try {
      const decodePayload = atob(payload);
      return JSON.parse(decodePayload);
    } catch (err) {
      console.error('Error decoding token payload', err);
      return null;
    }
  }

  getCurrentUserId(): string | null {
    const decodedToken = this.decodeToken();
    if (!decodedToken) return null;
    return decodedToken.id || null;
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  logout(): void {
    sessionStorage.removeItem(this.tokenKey);
  }
}
