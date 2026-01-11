import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterModule, Router } from '@angular/router';
import { UserRegistering } from './Services/user-registering.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontend');

  constructor(private userService: UserRegistering, private router: Router) {}

  isLoggedIn(): boolean {
    return this.userService.isLoggedIn();
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/']);
  }
}